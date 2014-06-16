package stx.shopclient.searchresultsactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.webservice.WebClient;

public class SearchResultsActivity extends BaseActivity implements
		OnItemClickListener
{
	public static final String EXTRA_KEY_QUICKSEARCH = "quick_earch";
	public static final String EXTRA_KEY_QUICKSEARCH_QUERY = "quickquery_query";

	boolean _isQuickSearch = false;
	String _quickSearchQuery;

	PullToRefreshListView _listView;
	ListAdapter _adapter;

	List<CatalogItem> _items = new ArrayList<CatalogItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		_isQuickSearch = getIntent().getBooleanExtra(EXTRA_KEY_QUICKSEARCH,
				false);
		_quickSearchQuery = getIntent().getStringExtra(
				EXTRA_KEY_QUICKSEARCH_QUERY);

		getActionBar().setTitle("Результаты поиска");
	}

	@Override
	protected View createMainView(ViewGroup parent)
	{
		// generateData();

		View view = getLayoutInflater().inflate(
				R.layout.search_results_activity, parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		if (_isQuickSearch)
			_listView.setMode(Mode.DISABLED);
		else
			_listView.setMode(Mode.PULL_FROM_END);
		_listView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						new LoadTask(false).execute();
					}
				});
		_adapter = new ListAdapter();
		_listView.setOnItemClickListener(this);

		_listView.setAdapter(_adapter);

		new LoadTask(true).execute();

		return view;
	}

	void generateData()
	{
		Random random = new Random();

		for (int i = 0; i < 15; i++)
		{
			CatalogItem item = new CatalogItem();
			item.setName("Товар " + Integer.toString(i));
			/* item.setPrice(random.nextInt(5000)); */
			item.setRating(random.nextInt(5));
			_items.add(item);
		}
	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		boolean isFirstRun;
		Throwable exception;
		Collection<CatalogItem> items;
		ProgressDialog progressDialog;

		public LoadTask(boolean isFirstRun)
		{
			this.isFirstRun = isFirstRun;
		}

		@Override
		protected void onPreExecute()
		{
			if (isFirstRun)
				progressDialog = ProgressDialog.show(
						SearchResultsActivity.this, "Загрузка",
						"Выполняется поиск элементов");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				items = client.quickSearchItems(Token.getCurrent(),
						Repository.CatalogId, _quickSearchQuery, 1, 10);
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
			if (isFirstRun)
				progressDialog.dismiss();
			else
				_listView.onRefreshComplete();

			if (exception != null)
			{
				Toast.makeText(SearchResultsActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			}
			else
			{
				if (items != null && items.size() > 0)
				{
					_items.addAll(items);
					_adapter.notifyDataSetChanged();
				}
			}
		}
	}

	class ListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _items.size();
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

		void initItemView(View view, CatalogItem item)
		{
			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

			BaseActivity
					.setRatingBarColor(ratingBar,
							Repository.get(SearchResultsActivity.this)
									.getCatalogManager().getSettings()
									.getRatingColor());

			nameTextView.setText(item.getName());
			descriptionTextView.setText(Double.toString(item.getPrice())
					+ " рублей");

			ratingBar.setRating((float) item.getRating());
		}

		@Override
		public View getView(int index, View arg1, ViewGroup viewGroup)
		{
			View view = null;

			CatalogItem item = _items.get(index);

			view = getLayoutInflater().inflate(
					R.layout.catalog_browser_activity_item, viewGroup, false);

			view.setTag(item);

			initItemView(view, item);

			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3)
	{
		CatalogItem item = (CatalogItem) view.getTag();

		Intent intent = new Intent(this, ItemActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, item.getId());

		startActivity(intent);
	}
}
