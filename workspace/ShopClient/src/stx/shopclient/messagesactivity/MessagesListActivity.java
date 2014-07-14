package stx.shopclient.messagesactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.ShopClientService;
import stx.shopclient.entity.Message;
import stx.shopclient.entity.Token;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.LoadMoreListAdapter;
import stx.shopclient.webservice.WebClient;

public class MessagesListActivity extends BaseActivity implements
		OnItemClickListener
{
	PullToRefreshListView _listView;

	List<Message> _messages = new ArrayList<Message>();
	MessageListAdapter _adapter = new MessageListAdapter();
	
	public static Message SelectedMessage;

	@Override
	protected View createMainView(ViewGroup parent)
	{		
		NotificationManager notifManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.cancel(ShopClientService.MESSAGE_NOTIF_ID);
		
		getActionBar().setTitle("Сообщения");

		View view = getLayoutInflater().inflate(R.layout.message_list_activity,
				parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setOnItemClickListener(this);
		_listView.setMode(Mode.PULL_FROM_END);
		_listView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						RefreshTask task = new RefreshTask();
						task.isFirstLoad = false;
						task.execute();
					}
				});
		_listView.setAdapter(_adapter);
		
		RefreshTask task = new RefreshTask();
		task.isFirstLoad = true;
		task.execute();

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

	class RefreshTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog dialog;
		Throwable exception;
		public boolean isFirstLoad;
		Collection<Message> messages;

		@Override
		protected void onPreExecute()
		{
			if (isFirstLoad)
				dialog = ProgressDialog.show(MessagesListActivity.this,
						"Загрузка", "Получение списка сообщений");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				messages = client.getAllMessages(Token.getCurrent(),
						_messages.size() + 1, 30);
			}
			catch (Throwable ex)
			{
				exception = ex;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			if (isFirstLoad)
				dialog.dismiss();
			else
				_listView.onRefreshComplete();

			if (exception != null)
				Toast.makeText(MessagesListActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else if (messages != null && messages.size() > 0)
			{
				_messages.addAll(messages);
				_adapter.notifyDataSetChanged();
			}
		}
	}

	class MessageListAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
			return _messages.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup arg2)
		{
			Message message = _messages.get(index);

			TextView text = new TextView(MessagesListActivity.this);

			text.setText(message.getTitle());

			text.setTextSize(18);
			text.setPadding(20, 20, 20, 20);

			if (!message.isRead())
				text.setTypeface(text.getTypeface(), Typeface.BOLD);

			return text;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
	{
		Message message = _messages.get(index - 1);

		message.setRead(true);
		_adapter.notifyDataSetChanged();

		SelectedMessage = message;
		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra(MessageActivity.MESSAGE_ID_EXTRA_KEY, message.getId());
		startActivity(intent);
	}
}
