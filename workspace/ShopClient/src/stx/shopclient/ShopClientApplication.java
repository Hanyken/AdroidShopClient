package stx.shopclient;

import java.util.GregorianCalendar;

import stx.shopclient.entity.AppSettings;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.UpdateResultEntity;
import stx.shopclient.repository.Repository;
import stx.shopclient.settings.ServerSettings;
import stx.shopclient.webservice.WebClient;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ShopClientApplication extends Application
{
	public static final String BROADCAST_ACTION_MESSAGE_COUNT = "stx.shopclient.messagecount";
	public static final String BROADCAST_ACTION_NEW_MESSAGES = "stx.shopclient.newmessages";
	public static final String BROADCAST_ACTION_GEOLOCATION = "stx.shopclient.geolocation";

	Thread _pullingThread;
	boolean _threadRunning = true;
	GregorianCalendar _lastCheckCatalogModif = new GregorianCalendar();
	boolean _settingsLoaded = false;

	MessageCountBroadcastReceiver _msgCountReceiver = new MessageCountBroadcastReceiver();
	
	ShopClientLocationListener _locationListener = new ShopClientLocationListener();

	@Override
	public void onCreate()
	{
		super.onCreate();

		Repository.loadSelectedCatalogId(this);

		_pullingThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				pullingThreadFunc();
			}
		});
		_pullingThread.start();

		IntentFilter messageCountFilter = new IntentFilter(
				BROADCAST_ACTION_MESSAGE_COUNT);
		registerReceiver(_msgCountReceiver, messageCountFilter);	
		
		LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000,
				1000, _locationListener);
	}

	@Override
	public void onTerminate()
	{
		super.onTerminate();

		_threadRunning = false;
	}

	void pullingThreadFunc()
	{
		while (_threadRunning)
		{
			try
			{
				if (Token.getCurrent() != null)
				{
					WebClient client = new WebClient(this);

					updateCatalog(client);
				}

				Thread.sleep(10000);
			}
			catch (Throwable ex)
			{
				Log.e("pullingThreadFunc", ex.getLocalizedMessage(), ex);

				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	void updateCatalog(WebClient client)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(GregorianCalendar.SECOND, -60);

		if (_lastCheckCatalogModif.before(cal))
		{
			Catalog catalog = Repository.get(null).getCatalogManager()
					.getCatalog();

			if (catalog != null)
			{
				UpdateResultEntity res = client.updateCatalogNeeded(
						Token.getCurrent(), catalog.getId(),
						catalog.getLastModification());

				if (res.updateNeeded())
				{
					Repository.get(null).loadCatalogFromWeb(
							getApplicationContext());
					_lastCheckCatalogModif = new GregorianCalendar();
				}
			}
		}
	}

	public class MessageCountBroadcastReceiver extends BroadcastReceiver
	{
		public static final String COUNT_EXTRA_KEY = "count";

		@Override
		public void onReceive(Context context, Intent intent)
		{
			long count = intent.getLongExtra(COUNT_EXTRA_KEY, 0);
			Repository.get(null).getMessagesManager()
					.setUnreadMessageCount(count);
		}
	}
	
	class ShopClientLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location location)
		{
			final Location loc = location;

			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						WebClient client = new WebClient(ShopClientApplication.this);
						updateLocation(loc, client);
					}
					catch (Throwable ex)
					{
						Log.e("StxApplication", ex.getLocalizedMessage());
					}
				}
			}).start();
		}
		
		void updateLocation(Location location, WebClient client)
		{
			client.updateGeoPosition(Token.getCurrent(),
					Double.toString(location.getLatitude()),
					Double.toString(location.getLongitude()),
					Float.toString(location.getAccuracy()));
		}

		@Override
		public void onProviderDisabled(String arg0)
		{

		}

		@Override
		public void onProviderEnabled(String arg0)
		{

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2)
		{

		}
	}
}
