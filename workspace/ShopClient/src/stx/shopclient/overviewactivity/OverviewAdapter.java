package stx.shopclient.overviewactivity;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.ImageColumns;
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

	public Collection<String> onLoadMore()
	{
		try
		{
		Thread.sleep(1000);
		}
		catch(Exception ex){}
		int size = _Items.size();
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			items.add("Коментарий № " + String.valueOf(size + i));
		}
		return items;
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
				new AsyncTask<Void, Void, Collection<String>>()
				{
					@Override
					protected Collection<String> doInBackground(Void... params)
					{
						return onLoadMore();
					}
					@Override
					protected void onPostExecute(Collection<String> result)
					{
						_Items.addAll(result);
						_ListView.post(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
								_ListView.removeFooterView(_progressBar);
								}
								catch(Exception ex){}
								notifyDataSetChanged();
								loading = false;	
							}
						});
					}
				}.execute();
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{}

}
