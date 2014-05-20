package stx.shopclient.overviewactivity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Loader.OnLoadCanceledListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import stx.shopclient.R;
import stx.shopclient.ui.common.LoadMoreListAdapter;

public class OverviewAdapter extends BaseAdapter implements OnScrollListener
{
	private ArrayList<String> _Items;
	private LayoutInflater _Inflater;
	private ListView _ListView;
	private boolean loading;
	private ProgressBar _progressBar;

	public OverviewAdapter(Context context, ListView listView)
	{
		_progressBar = new ProgressBar(context);
		_Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_Items = new ArrayList<String>();
		_ListView = listView;
		onLoadMore();
	}

	@Override
	public int getCount()
	{
		return _Items.size();
	}

	@Override
	public Object getItem(int index)
	{
		return _Items.get(index);
	}

	@Override
	public long getItemId(int index)
	{
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup viewgroup)
	{
		if (view == null)
			view = _Inflater.inflate(R.layout.overview_activity_item,
					viewgroup, false);

		String item = (String) getItem(index);
		TextView txtOverview = (TextView) view.findViewById(R.id.txtOverview);

		txtOverview.setText(item);

		return view;
	}

	public boolean onLoadMore()
	{
		try
		{
		Thread.sleep(1000);
		}
		catch(Exception ex){}
		int size = _Items.size();
		for (int i = 0; i < 10; i++)
		{
			_Items.add("Коментарий № " + String.valueOf(size + i));
		}
		return true;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		if (!loading)
		{
			if ((firstVisibleItem + visibleItemCount) >= getCount())
			{
				loading = true;
				_ListView.addFooterView(_progressBar);
				new AsyncTask<Void, Void, Boolean>()
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						return onLoadMore();
					}
					@Override
					protected void onPostExecute(Boolean result)
					{
						_ListView.removeFooterView(_progressBar);
						notifyDataSetChanged();
						loading = false;
					}
				}.execute();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{}

}
