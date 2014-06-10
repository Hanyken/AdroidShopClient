package stx.shopclient.overviewactivity;

import java.util.ArrayList;
import java.util.Collection;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Overview;
import stx.shopclient.repository.OverviewsManager;
import stx.shopclient.repository.Repository;

public class OverviewAdapter extends BaseAdapter
{
	private ArrayList<Overview> _Items;
	private CatalogItem _Item;
	private LayoutInflater _Inflater;
	private PullToRefreshListView _listView;
	private CatalogSettings settings;
	private Context _Context;
	
	public OverviewAdapter(Context context, PullToRefreshListView lstView, long itemId)
	{
		_Context = context;
		settings = Repository.getIntent(context).getCatalogManager().getSettings();
		_listView = lstView;
		_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_Items = new ArrayList<Overview>();
		_Item = Repository.getIntent(context).getItemsManager().getItem(itemId);
		
		_Items.addAll(onLoadMore());
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

		LayerDrawable stars = (LayerDrawable) rtgRating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(settings.getRatingColor(),
				PorterDuff.Mode.SRC_ATOP);
		
		txtOverview.setText(item.getDescription());
		
		rtgRating.setRating((float)item.getRating());

		return view;
	}

	private Collection<Overview> onLoadMore()
	{		
		ArrayList<Overview> items = new ArrayList<Overview>();
		
		int size = _Items.size();
		OverviewsManager manager = Repository.getIntent(_Context).getOverviewsManager();
		int count = _Item.getOverviewsCount();
		
		if (size < count)
		{
			items.addAll(manager.getOverviews(_Item.getId()));
		}
		
		return items;
	}

	public void RefreshData()
	{
		new AsyncTask<Void, Void, Collection<Overview>>()
		{
			@Override
			protected Collection<Overview> doInBackground(Void... params)
			{
				try
				{
				Thread.sleep(1000);
				}
				catch(Exception ex){}
				
				return onLoadMore();
			}
			@Override
			protected void onPostExecute(Collection<Overview> result)
			{
				_Items.addAll(result);
				_listView.onRefreshComplete();
				notifyDataSetChanged();
			}
		}.execute();
	}
	
}
