package stx.shopclient;

import java.util.Collection;

import stx.shopclient.entity.Message;
import stx.shopclient.entity.MessageCountResult;
import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.messagesactivity.MessageActivity;
import stx.shopclient.messagesactivity.MessagesListActivity;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.webservice.WebClient;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ShopClientService extends Service
{
	public static final int MESSAGE_NOTIF_ID = 1;
	static final String MESSAGE_COUNT_PREF_NAME = "ServerSettings";

	Thread _pullingThread;
	boolean _threadRunning = true;	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);

		_pullingThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				pullingThreadFunc();
			}
		});
		_pullingThread.start();
			
		Log.d("StxService", "Service started");

		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		Log.d("StxService", "Service destroyed");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	void pullingThreadFunc()
	{
		while (_threadRunning)
		{
			Log.d("StxService", "pullingThreadFunc");

			try
			{
				WebClient client = new WebClient(this);

				if (Token.getCurrent() == null)
				{
					UserAccount.load(this);
					client.login(UserAccount.getLogin(),
							UserAccount.getPassword(), UserAccount.getWidth(),
							UserAccount.getHeight());
				}

				if (Token.getCurrent() != null)
				{
					checkMessages(client);
				}

				Thread.sleep(60000);
			}
			catch (Throwable ex)
			{
				//Log.e("StxService", ex.getLocalizedMessage(), ex);

				try
				{
					Thread.sleep(60000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	void checkMessages(WebClient client)
	{
		long count = client.getNewMessagesCount(Token.getCurrent()).getCount();
		MessageCountResult showCountResult = client.getShowMessagesCount(Token
				.getCurrent());

		Intent countIntent = new Intent(
				ShopClientApplication.BROADCAST_ACTION_MESSAGE_COUNT);
		countIntent
				.putExtra(
						ShopClientApplication.MessageCountBroadcastReceiver.COUNT_EXTRA_KEY,
						count);
		sendBroadcast(countIntent);

		LastCountInfo lastCount = loadLastMessageCount();

		if (showCountResult.getCount() > 0
				&& showCountResult.getLastId() != lastCount.lastId)
		{
			NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
					this);
			notifBuilder.setSmallIcon(R.drawable.ic_launcher);

			PendingIntent pendingIntent = null;

			Intent newMessagesIntent = new Intent(
					ShopClientApplication.BROADCAST_ACTION_NEW_MESSAGES);
			sendBroadcast(newMessagesIntent);

			if (showCountResult.getCount() == 1)
			{
				Collection<Message> messages = client.getShowMessages(Token
						.getCurrent());
				if (messages != null && messages.size() > 0)
				{
					Message message = messages.iterator().next();

					notifBuilder.setContentTitle("Новое сообщение");
					notifBuilder.setContentText(message.getTitle());

					Intent intent = new Intent(this, MessageActivity.class);
					intent.putExtra(MessageActivity.MESSAGE_ID_EXTRA_KEY,
							message.getId());

					Intent msgListIntent = new Intent(this,
							MessagesListActivity.class);
					Intent mainIntent = new Intent(this, MainActivity.class);

					pendingIntent = PendingIntent.getActivities(this, 0,
							new Intent[]
							{ mainIntent, msgListIntent, intent },
							PendingIntent.FLAG_UPDATE_CURRENT);
				}
			}
			else
			{
				notifBuilder.setContentTitle(String.format("Новые сообщения",
						count));
				notifBuilder.setContentText(String.format("%d шт.",
						showCountResult.getCount()));
				Intent mainIntent = new Intent(this, MainActivity.class);

				Intent intent = new Intent(this, MessagesListActivity.class);

				pendingIntent = PendingIntent.getActivities(this, 0,
						new Intent[]
						{ mainIntent, intent },
						PendingIntent.FLAG_UPDATE_CURRENT);
			}

			if (pendingIntent != null)
			{
				notifBuilder.setContentIntent(pendingIntent);

				Uri alarmSound = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				notifBuilder.setSound(alarmSound);
				long[] pattern =
				{ 500, 500, 500, 500, 500 };
				notifBuilder.setVibrate(pattern);

				NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				notifManager.notify(MESSAGE_NOTIF_ID, notifBuilder.build());
				Log.d("StxService", "notification was sent");
			}
			else
				Log.d("StxService", "pendingIntent is null");
		}

		saveLastMessageCount(showCountResult.getCount(),
				showCountResult.getLastId());
	}

	LastCountInfo loadLastMessageCount()
	{
		SharedPreferences pref = getSharedPreferences(MESSAGE_COUNT_PREF_NAME,
				Activity.MODE_PRIVATE);
		LastCountInfo info = new LastCountInfo();
		info.count = pref.getLong("count", 0);
		info.lastId = pref.getLong("id", 0);
		return info;
	}

	void saveLastMessageCount(long count, long lastId)
	{
		SharedPreferences pref = getSharedPreferences(MESSAGE_COUNT_PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putLong("count", count);
		editor.putLong("id", lastId);
		editor.commit();
	}

	class LastCountInfo
	{
		public long count;
		public long lastId;
	}
}
