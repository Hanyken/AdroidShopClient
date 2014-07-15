package stx.shopclient;

import java.util.Collection;

import stx.shopclient.entity.Message;
import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.messagesactivity.MessageActivity;
import stx.shopclient.messagesactivity.MessagesListActivity;
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
import android.widget.Toast;

public class ShopClientService extends Service
{
	public static final int MESSAGE_NOTIF_ID = 1;

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

				Thread.sleep(10000);
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
		Log.w("Service", "Запрос на сервер");
		long count = client.getNewMessagesCount(Token.getCurrent());
		//Toast.makeText(this, "Кол-во "+ Long.toString(count), Toast.LENGTH_SHORT).show();
		
		Intent countIntent = new Intent(
				ShopClientApplication.BROADCAST_ACTION_MESSAGE_COUNT);
		countIntent.putExtra(
				ShopClientApplication.MessageCountBroadcastReceiver.COUNT_EXTRA_KEY,
				count);
		sendBroadcast(countIntent);

		if (count > _lastMessageCount)
		{
			Log.w("Service", "Кол-во "+Long.toString(count));
			NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(
					this);
			notifBuilder.setSmallIcon(R.drawable.ic_launcher);

			PendingIntent pendingIntent = null;

			if (count == 1)
			{
				Collection<Message> messages = client.getNewMessages(Token
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
				notifBuilder.setContentText(String.format("%d шт.", count));
				Intent mainIntent = new Intent(this, MainActivity.class);

				Intent intent = new Intent(this, MessagesListActivity.class);

				pendingIntent = PendingIntent.getActivities(this, 0,
						new Intent[]
						{ mainIntent, intent },
						PendingIntent.FLAG_UPDATE_CURRENT);
			}

			Log.w("Service", "До выврда сообщения");
			if (pendingIntent != null)
			{
				Log.w("Service", "Сообщение пошло");
				notifBuilder.setContentIntent(pendingIntent);

				NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				notifManager.notify(MESSAGE_NOTIF_ID, notifBuilder.build());
				Log.w("Service", "Сообщение послали");
			}
		}

		_lastMessageCount = count;
	}

}
