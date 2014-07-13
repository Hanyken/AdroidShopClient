package stx.shopclient;

import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.messagesactivity.MessagesListActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.webservice.WebClient;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ShopClientService extends Service
{
	final int MESSAGE_NOTIF_ID = 1;
	
	Thread _pullingThread;
	boolean _threadRunning = true;
	long _lastMessageCount = 0;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		_pullingThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				pullingThreadFunc();
			}
		});
		_pullingThread.start();

		return super.onStartCommand(intent, flags, startId);
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
			try
			{
				if (Token.getCurrent() != null)
				{
					WebClient client = new WebClient(this);

					checkMessages(client);
				}

				Thread.sleep(30000);
			}
			catch (Throwable ex)
			{
				Log.e("pullingThreadFunc", ex.getLocalizedMessage(), ex);

				try
				{
					Thread.sleep(30000);
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
		long count = client.getNewMessagesCount(Token.getCurrent());

		if (count > _lastMessageCount)
		{
			NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
					this);
			notifBuilder.setContentTitle(
					String.format("Непрочитанные сообщения (%d)", count)).setSmallIcon(
					R.drawable.ic_launcher);

			Intent intent = new Intent(this, MessagesListActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addParentStack(MessagesListActivity.class);
			
			stackBuilder.addNextIntent(intent);
			PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
					PendingIntent.FLAG_UPDATE_CURRENT);

			notifBuilder.setContentIntent(pendingIntent);
						
			NotificationManager notifManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			
			notifManager.notify(MESSAGE_NOTIF_ID, notifBuilder.build());
		}

		_lastMessageCount = count;
	}

}
