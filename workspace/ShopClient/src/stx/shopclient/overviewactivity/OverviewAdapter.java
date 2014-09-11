package stx.shopclient.overviewactivity;

import java.util.ArrayList;
import java.util.Collection;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Overview;
import stx.shopclient.entity.Token;
import stx.shopclient.repository.OverviewsManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;

public class OverviewAdapter extends BaseAdapter
{
	private ArrayList<Overview> _Items;
	private CatalogItem _Item;
	private LayoutInflater _Inflater;
	private PullToRefreshListView _listView;
	private CatalogSettings settings;
	private Context _Context;
	ProgressDialog _progressDialog;
	private boolean _activityDestroyed = false;

	public OverviewAdapter(Context context, PullToRefreshListView lstView,
			long itemId)
	{
		_Context = context;
		settings = Repository.get(context).getCatalogManager().getSettings();
		_listView = lstView;
		_Inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_Items = new ArrayList<Overview>();
		_Item = Repository.get(context).getItemsManager().getItem(itemId);

		loadData(true);
	}

	public void setActivityDestroyed(boolean val)
	{
		_activityDestroyed = val;
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
		RatingBar rtgRating = (RatingBar) view.findViewById(R.id.rtgRating);

		LayerDrawable stars = (LayerDrawable) rtgRating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(settings.getRatingColor(),
				PorterDuff.Mode.SRC_ATOP);

		txtOverview.setText(item.getDescription());

		rtgRating.setRating((float) item.getRating());

		return view;
	}

	public void loadData(boolean isFirstLoad)
	{
		LoadTask task = new LoadTask(isFirstLoad);
		task.execute();
	}

	public void refreshData()
	{
		LoadTask task = new LoadTask(true);
		task.isRefresh = true;
		task.execute();
	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		boolean isFirstLoad;
		boolean isRefresh;
		Throwable exception;
		Collection<Overview> overviews;

		public LoadTask(boolean isFirstLoad)
		{
			this.isFirstLoad = isFirstLoad;
		}

		@Override
		protected void onPreExecute()
		{
			if (_activityDestroyed)
				return;

			if (isFirstLoad)
			{
				_progressDialog = ProgressDialog.show(_Context, "Загрузка",
						"Выполняется загрузка отзывов");
				ProgressDlgUtil.setCancellable(_progressDialog, (Activity)_Context);
			}
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				WebClient client = new WebClient(_Context);
				if (isRefresh)
					overviews = client.getOverviews(Token.getCurrent(),
							_Item.getId(), 1, _Items.size() + 1);
				else
					overviews = client.getOverviews(Token.getCurrent(),
							_Item.getId(), _Items.size() + 1, 50);
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
			if (_activityDestroyed)
				return;

			if (isFirstLoad)
				_progressDialog.dismiss();
			else
				_listView.onRefreshComplete();

			if (exception != null)
			{
				Toast.makeText(_Context, exception.getLocalizedMessage(),
						Toast.LENGTH_LONG).show();
			}
			else
			{
				if (overviews != null && overviews.size() > 0)
				{
					if (isRefresh)
					{
						_Items.clear();
						_Items.addAll(overviews);
					}
					else
						_Items.addAll(overviews);
					notifyDataSetChanged();
				}
			}
		}
	}

}
