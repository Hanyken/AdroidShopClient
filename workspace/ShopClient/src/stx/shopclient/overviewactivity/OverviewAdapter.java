package stx.shopclient.overviewactivity;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Overview;
import stx.shopclient.repository.OverviewsManager;
import stx.shopclient.repository.Repository;

public class OverviewAdapter extends BaseAdapter implements OnScrollListener
{
	private ArrayList<Overview> _Items;
	private CatalogItem _Item;
	private LayoutInflater _Inflater;
	private ListView _ListView;
	private boolean _Loading;
	private boolean _LoadNeeded;
	private ProgressBar _progressBar;
	private long _ItemId;

	public OverviewAdapter(Context context, ListView listView, long itemId)
	{
		_LoadNeeded = true;
		_progressBar = new ProgressBar(context);
		_Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_Items = new ArrayList<Overview>();
		_ListView = listView;
		_ItemId = itemId;
		_Item = Repository.getIntent().getItemsManager().getItem(itemId);
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

		Overview item = (Overview) getItem(index);
		TextView txtOverview = (TextView) view.findViewById(R.id.txtOverview);
		RatingBar rtgRating = (RatingBar)view.findViewById(R.id.rtgRating);

		txtOverview.setText(item.getDescription());
		
		rtgRating.setRating((float)item.getRating());

		return view;
	}

	public Collection<Overview> onLoadMore()
	{
		try
		{
		Thread.sleep(1000);
		}
		catch(Exception ex){}
		
		ArrayList<Overview> items = new ArrayList<Overview>();
		
		int size = _Items.size();
		OverviewsManager manager = Repository.getIntent().getOverviewsManager();
		int count = _Item.getOverviewsCount();
		
		if (size >= count)
		{
			_LoadNeeded = false;
		}
		else
		{
			items.addAll(manager.getOverviews(_ItemId));
		}
		
		return items;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		if (!_Loading && _LoadNeeded)
		{
			if ((firstVisibleItem + visibleItemCount) >= getCount())
			{
				_Loading = true;
				_ListView.addFooterView(_progressBar);
				new AsyncTask<Void, Void, Collection<Overview>>()
				{
					@Override
					protected Collection<Overview> doInBackground(Void... params)
					{
						return onLoadMore();
					}
					@Override
					protected void onPostExecute(Collection<Overview> result)
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
								_Loading = false;	
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
