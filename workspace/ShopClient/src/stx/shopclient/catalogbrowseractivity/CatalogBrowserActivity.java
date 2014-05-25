package stx.shopclient.catalogbrowseractivity;

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
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.LoadMoreListAdapter;

public class CatalogBrowserActivity extends BaseActivity implements
		OnItemClickListener {

	public static final String NODE_ID_EXTRA_KEY = "nodeId";
	public static final String NODE_NAME_EXTRA_KEY = "nodeName";

	//TextView _nodeNameTextView;
	long _rootNodeId;
	String _rootNodeName;
	ListView _listView;
	ListAdapter _adapter;
	ProgressBar _progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();

		_rootNodeId = intent.getLongExtra(NODE_ID_EXTRA_KEY, 0);
		_rootNodeName = intent.getStringExtra(NODE_NAME_EXTRA_KEY);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		View view = getLayoutInflater().inflate(
				R.layout.catalog_browser_activity, parent, false);
		
		getActionBar().setTitle(_rootNodeName);

		_progressBar = new ProgressBar(CatalogBrowserActivity.this);

		//_nodeNameTextView = (TextView) view.findViewById(R.id.nodeNameTextView);
		//_nodeNameTextView.setText(_rootNodeName);

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

		List<CatalogNode> _nodes;
		List<CatalogItem> _items;

		public ListAdapter() {
			super(_listView);

			_nodes = new ArrayList<CatalogNode>();
			_items = new ArrayList<CatalogItem>();

			generateData();

			setLoading(false);
		}

		void generateData() {
			Random random = new Random();

			for (int i = 0; i < 4; i++) {
				CatalogNode node = new CatalogNode();
				node.setName("Категория " + Integer.toString(i));
				node.setMinPrice(random.nextInt(5000));
				node.setCount(random.nextInt(100));
				_nodes.add(node);
			}

			for (int i = 0; i < 10; i++) {
				CatalogItem item = new CatalogItem();
				item.setName("Товар " + Integer.toString(i));
				//item.setPrice(random.nextInt(5000));
				item.setRating(random.nextInt(5));
				_items.add(item);
			}
		}

		@Override
		public int getCount() {
			return _nodes.size() + _items.size();
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
			descriptionTextView.setText(Double.toString(Repository.getIntent().getPropertiesManager().getItemPrice(item.getId()))
					+ " рублей");

			ratingBar.setRating((float) item.getRating());
		}

		void initNodeView(View view, CatalogNode node) {
			TextView nameTextView = (TextView) view
					.findViewById(R.id.nodeNameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);

			nameTextView.setText(node.getName());

			descriptionTextView.setText(getNodeDescription(node));
		}

		String getNodeDescription(CatalogNode node) {
			String description = String.format("%d товаров от %d рублей",
					node.getCount(), (int) node.getMinPrice());
			return description;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup viewGroup) {
			View view = null;

			if (index < _nodes.size()) {
				CatalogNode node = _nodes.get(index);

				view = getLayoutInflater().inflate(
						R.layout.catalog_browser_activity_node, viewGroup,
						false);
				view.setTag(node);

				initNodeView(view, node);
			} else {
				index = index - _nodes.size();

				CatalogItem item = _items.get(index);

				view = getLayoutInflater().inflate(
						R.layout.catalog_browser_activity_item, viewGroup,
						false);

				view.setTag(item);

				initItemView(view, item);
			}

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
		if (view.getTag() instanceof CatalogNode) {
			CatalogNode node = (CatalogNode)view.getTag();
			
			Intent intent = new Intent(this, CatalogBrowserActivity.class);
			intent.putExtra(CatalogBrowserActivity.NODE_ID_EXTRA_KEY, node.getId());
			intent.putExtra(CatalogBrowserActivity.NODE_NAME_EXTRA_KEY, node.getName());
			
			startActivity(intent);
		} else {
			CatalogItem item = (CatalogItem)view.getTag();
			
			Intent intent = new Intent(this, ItemActivity.class);
			intent.putExtra("ItemTitle", item.getName());
			intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, "123");
			startActivity(intent);
		}
	}
}
