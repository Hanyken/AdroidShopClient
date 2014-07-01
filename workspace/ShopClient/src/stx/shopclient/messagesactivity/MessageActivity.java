package stx.shopclient.messagesactivity;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Message;
import stx.shopclient.entity.Token;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;

public class MessageActivity extends BaseActivity
{
	public static String MESSAGE_ID_EXTRA_KEY = "messageId";

	long _messageId;

	Message _message;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		_messageId = getIntent().getLongExtra(MESSAGE_ID_EXTRA_KEY, 0);
		_message = MessagesListActivity.SelectedMessage;

		getActionBar().setTitle(_message.getTitle());

		View view = getLayoutInflater().inflate(R.layout.message_activity,
				parent, false);

		WebView webView = (WebView) view.findViewById(R.id.webView);
		webView.loadData(_message.getText(), "text/html; charset=utf-8", "UTF-8");

		new ReadMessageTask().execute();

		return view;
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
}
