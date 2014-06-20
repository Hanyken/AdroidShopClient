package stx.shopclient.messagesactivity;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Message;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.LoadMoreListAdapter;

public class MessagesListActivity extends BaseActivity implements
		OnItemClickListener
{
	PullToRefreshListView _listView;

	List<Message> _messages = new ArrayList<Message>();
	MessageListAdapter _adapter = new MessageListAdapter();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		_messages.addAll(Repository.get(this).getMessagesManager()
				.getMessages());

		getActionBar().setTitle("Сообщения");

		View view = getLayoutInflater().inflate(R.layout.message_list_activity,
				parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setOnItemClickListener(this);
		_listView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						new RefreshTask().execute();
					}
				});
		_listView.setAdapter(_adapter);

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

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			_listView.onRefreshComplete();
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
			text.setPadding(10, 10, 10, 10);

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

		Intent intent = new Intent(this, MessageActivity.class);
		intent.putExtra(MessageActivity.MESSAGE_ID_EXTRA_KEY, message.getId());
		startActivity(intent);
	}
}
