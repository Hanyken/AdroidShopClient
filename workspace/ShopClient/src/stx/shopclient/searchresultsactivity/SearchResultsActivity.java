package stx.shopclient.searchresultsactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

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
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;

public class SearchResultsActivity extends BaseActivity implements
		OnItemClickListener {

	PullToRefreshListView _listView;
	ListAdapter _adapter;
	ProgressBar _progressBar;
	
	List<CatalogItem> _items = new ArrayList<CatalogItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setTitle("Результаты поиска");
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		generateData();
		
		View view = getLayoutInflater().inflate(
				R.layout.search_results_activity, parent, false);

		_progressBar = new ProgressBar(this);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.PULL_FROM_END);		
		_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(PullToRefreshBase<ListView> refreshView)
					{
						new LoadMoreTask().execute();
					}
				});
		_adapter = new ListAdapter();
		_listView.setOnItemClickListener(this);
		
		_listView.setAdapter(_adapter);

		return view;
	}

	void generateData() {
		Random random = new Random();

		for (int i = 0; i < 15; i++) {
			CatalogItem item = new CatalogItem();
			item.setName("Товар " + Integer.toString(i));
			/*item.setPrice(random.nextInt(5000));*/
			item.setRating(random.nextInt(5));
			_items.add(item);
		}
	}
	
	class LoadMoreTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Random random = new Random();
			int size = _items.size();
			for (int i = size; i < size + 10; i++) {
				CatalogItem item = new CatalogItem();
				item.setName("Товар " + Integer.toString(i));
				/*item.setPrice(random.nextInt(5000));*/
				item.setRating(random.nextInt(5));
				_items.add(item);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			_listView.onRefreshComplete();
			_adapter.notifyDataSetChanged();
		}
	}
	
	class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return _items.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		void initItemView(View view, CatalogItem item) {
			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			
			BaseActivity.setRatingBarColor(ratingBar, Repository.getIntent().getCatalogManager().getSettings()
					.getRatingColor());

			nameTextView.setText(item.getName());
			descriptionTextView.setText(Double.toString(item.getPrice()) + " рублей");

			ratingBar.setRating((float) item.getRating());
		}

		@Override
		public View getView(int index, View arg1, ViewGroup viewGroup) {
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
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		CatalogItem item = (CatalogItem)view.getTag();
		
		Intent intent = new Intent(this, ItemActivity.class);
		intent.putExtra("ItemTitle", item.getName());
		
		startActivity(intent);
	}
}
