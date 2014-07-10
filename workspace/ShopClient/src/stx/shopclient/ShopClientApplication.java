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
import android.util.Log;

public class ShopClientApplication extends Application
{
	Thread _pullingThread;
	boolean _threadRunning = true;
	GregorianCalendar _lastCheckCatalogModif = new GregorianCalendar();
	boolean _settingsLoaded = false;

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

					updateMessageCount(client);
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

	void updateMessageCount(WebClient client)
	{
		long count = client.getNewMessagesCount(Token.getCurrent());
		Repository.get(null).getMessagesManager().setUnreadMessageCount(count);
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

}
