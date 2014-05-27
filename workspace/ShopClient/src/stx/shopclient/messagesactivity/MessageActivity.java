package stx.shopclient.messagesactivity;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Message;
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
		_message = Repository.getIntent().getMessagesManager().getMessage(_messageId);
		
		getActionBar().setTitle(_message.getTitle());
		
		View view = getLayoutInflater().inflate(R.layout.message_activity, parent, false);
		
		WebView webView = (WebView)view.findViewById(R.id.webView);
		webView.loadData(_message.getText(), "text/html", "utf-8");
		
		return view;
	}
}
