package stx.shopclient.messagesactivity;

import java.text.SimpleDateFormat;
import java.util.Collection;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.ShopClientService;
import stx.shopclient.entity.Message;
import stx.shopclient.entity.Token;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.utils.ProgressDialogAsyncTask;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class MessageActivity extends BaseActivity
{
	public static String MESSAGE_ID_EXTRA_KEY = "messageId";
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	long _messageId;

	Message _message;

	View _mainView;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.cancel(ShopClientService.MESSAGE_NOTIF_ID);

		_messageId = getIntent().getLongExtra(MESSAGE_ID_EXTRA_KEY, 0);

		_mainView = getLayoutInflater().inflate(R.layout.message_activity,
				parent, false);

		if (MessagesListActivity.SelectedMessage != null)
		{
			_message = MessagesListActivity.SelectedMessage;
			MessagesListActivity.SelectedMessage = null;
			loadMessageUI();
		}
		else
			new LoadTask().execute();

		return _mainView;
	}

	void loadMessageUI()
	{
		getActionBar().setTitle("Сообщения");

		WebView webView = (WebView) _mainView.findViewById(R.id.webView);
		TextView title = (TextView) _mainView.findViewById(R.id.titleTextView);
		TextView time = (TextView) _mainView.findViewById(R.id.timeTextView);
		TextView date = (TextView) _mainView.findViewById(R.id.dateTextView);
		
		title.setText(_message.getTitle());
		time.setText(timeFormat.format(_message.getCreateDate().getTime()));
		date.setText(dateFormat.format(_message.getCreateDate().getTime()));
		
		webView.loadData(_message.getText(), "text/html; charset=utf-8",
				"UTF-8");

		new ReadMessageTask().execute();
	}

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	class ReadMessageTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			createWebClient().getReciveMessages(Token.getCurrent(),
					_message.getId());
			return null;
		}
	}

	class LoadTask extends ProgressDialogAsyncTask<Collection<Message>>
	{
		public LoadTask()
		{
			super(MessageActivity.this, "Получение сообщения");
		}

		@Override
		protected Collection<Message> backgroundTask() throws Throwable
		{
			return createWebClient().getNewMessages(Token.getCurrent());
		}

		@Override
		protected void onPostExecuteNoError(Collection<Message> result)
		{
			if (result != null)
			{
				for (Message message : result)
				{
					if (message.getId() == _messageId)
					{
						_message = message;						
						MessagesListActivity.SelectedMessage = null;
						loadMessageUI();
						break;
					}
				}
			}
			else
				finish();
		}
	}
}
