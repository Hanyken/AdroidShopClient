package stx.shopclient.searchresultsactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.ui.common.LoadMoreListAdapter;

public class SearchResultsActivity extends BaseActivity implements
		OnItemClickListener {

	ListView _listView;
	ListAdapter _adapter;
	ProgressBar _progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setTitle("Результаты поиска");
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		View view = getLayoutInflater().inflate(
				R.layout.search_results_activity, parent, false);

		_progressBar = new ProgressBar(this);

		_listView = (ListView) view.findViewById(R.id.listView);
		_adapter = new ListAdapter();
		_listView.setOnScrollListener(_adapter);
		_listView.setOnItemClickListener(this);
		
		showLoadingProgress(true);
		_listView.setAdapter(_adapter);
		showLoadingProgress(false);

		return view;
	}

	void showLoadingProgress(boolean show) {
		if (show)
			_listView.addFooterView(_progressBar);
		else
			_listView.removeFooterView(_progressBar);
	}

	class ListAdapter extends LoadMoreListAdapter {

		List<CatalogItem> _items;

		public ListAdapter() {
			super(_listView);

			_items = new ArrayList<CatalogItem>();

			generateData();

			setLoading(false);
		}

		void generateData() {
			Random random = new Random();

			for (int i = 0; i < 15; i++) {
				CatalogItem item = new CatalogItem();
				item.setName("Товар " + Integer.toString(i));
				item.setPrice(random.nextInt(5000));
				item.setRating(random.nextInt(5));
				_items.add(item);
			}
		}

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

			nameTextView.setText(item.getName());
			descriptionTextView.setText(Integer.toString((int) item.getPrice())
					+ " рублей");

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

		@Override
		public boolean onLoadMore() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Random random = new Random();
			int size = _items.size();
			for (int i = size; i < size + 10; i++) {
				CatalogItem item = new CatalogItem();
				item.setName("Товар " + Integer.toString(i));
				item.setPrice(random.nextInt(5000));
				item.setRating(random.nextInt(5));
				_items.add(item);
			}

			return false;
		}

		@Override
		public void onBeforeLoadData() {
			showLoadingProgress(true);
		}

		@Override
		public void onAfterLoadData() {
			showLoadingProgress(false);
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
