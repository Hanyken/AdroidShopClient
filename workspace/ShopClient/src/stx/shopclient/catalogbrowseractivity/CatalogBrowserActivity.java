package stx.shopclient.catalogbrowseractivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import stx.shopclient.utils.ImageDownloadTask;

public class CatalogBrowserActivity extends BaseActivity implements
		OnItemClickListener {

	public static final String NODE_ID_EXTRA_KEY = "nodeId";
	public static final String NODE_NAME_EXTRA_KEY = "nodeName";

	//TextView _nodeNameTextView;
	long _rootNodeId;
	String _rootNodeName;
	PullToRefreshListView _listView;
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
		
		_adapter = new ListAdapter(_rootNodeId);
		_listView.setOnItemClickListener(this);

		_listView.setAdapter(_adapter);

		return view;
	}
	
	class LoadMoreTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(1000);
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


	class ListAdapter extends BaseAdapter {

		List<CatalogNode> _nodes;
		List<CatalogItem> _items;

		public ListAdapter(long nodeId) {

			_nodes = new ArrayList<CatalogNode>();
			_items = new ArrayList<CatalogItem>();
			
			_nodes.addAll(Repository.getIntent(CatalogBrowserActivity.this).getCatalogManager().getNodes(nodeId));
			_items.addAll(Repository.getIntent(CatalogBrowserActivity.this).getItemsManager().getItems(nodeId));
			//generateData();
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
			
			BaseActivity.setRatingBarColor(ratingBar, Repository.getIntent(CatalogBrowserActivity.this).getCatalogManager().getSettings()
					.getRatingColor());

			nameTextView.setText(item.getName());
			descriptionTextView.setText(Double.toString(item.getPrice()) + " рублей");
			
			ImageView imgView = (ImageView)view.findViewById(R.id.imageView);			
			ImageDownloadTask.startNew(imgView, "file://" + Repository.getIntent(CatalogBrowserActivity.this).getImagesManager().getImagePath(item.getIco()));

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
			intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, item.getId());
			startActivity(intent);
		}
	}
}
