package stx.shopclient.catalogbrowseractivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;
import stx.shopclient.searchactivity.SearchActivity;
import stx.shopclient.ui.common.LoadMoreListAdapter;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;

public class CatalogBrowserActivity extends BaseActivity implements
		OnItemClickListener
{

	public static final String NODE_ID_EXTRA_KEY = "nodeId";
	public static final String NODE_NAME_EXTRA_KEY = "nodeName";

	// TextView _nodeNameTextView;
	long _rootNodeId;
	String _rootNodeName;
	PullToRefreshListView _listView;
	ListAdapter _adapter;
	ProgressDialog _progressDialog;

	List<CatalogNode> _nodes = new ArrayList<CatalogNode>();
	List<CatalogItem> _items = new ArrayList<CatalogItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Intent intent = getIntent();

		_rootNodeId = intent.getLongExtra(NODE_ID_EXTRA_KEY, 0);
		_rootNodeName = intent.getStringExtra(NODE_NAME_EXTRA_KEY);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(
				R.layout.catalog_browser_activity, parent, false);

		getActionBar().setTitle(_rootNodeName);

		// _nodeNameTextView = (TextView)
		// view.findViewById(R.id.nodeNameTextView);
		// _nodeNameTextView.setText(_rootNodeName);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.PULL_FROM_END);

		_listView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						new LoadItemsTask(false).execute();
					}
				});

		_adapter = new ListAdapter(_rootNodeId);
		_listView.setOnItemClickListener(this);

		_listView.setAdapter(_adapter);

		_nodes.addAll(Repository.get(CatalogBrowserActivity.this)
				.getCatalogManager().getNodes(_rootNodeId));

		LoadItemsTask task = new LoadItemsTask(true);
		task.execute();

		return view;
	}

	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return true;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	protected long getSearchActivityNodeId()
	{
		return _rootNodeId;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.catalog_browser_activity_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.search)
		{
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra(SearchActivity.TITLE_EXTRA_KEY,
					getSearchActivityTitle());
			intent.putExtra(SearchActivity.NODE_ID_EXTRA_KEY,
					getSearchActivityNodeId());
			startActivity(intent);
			return true;
		}
		else
			return super.onOptionsItemSelected(item);
	}

	class LoadItemsTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		boolean isFirstLoad;
		Collection<CatalogItem> items;

		public LoadItemsTask(boolean isFirstLoad)
		{
			this.isFirstLoad = isFirstLoad;
		}

		@Override
		protected void onPreExecute()
		{
			if (isFirstLoad)
			{
				_progressDialog = ProgressDialog.show(
						CatalogBrowserActivity.this, "��������",
						"�������� ��������� ���������");
				ProgressDlgUtil.setCancellable(_progressDialog, CatalogBrowserActivity.this);
			}
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = new WebClient(CatalogBrowserActivity.this);

				items = client.getNodeItems(Token.getCurrent(), _rootNodeId,
						_items.size() + 1, 50, 0);

				if (items != null && items.size() > 0)
					_items.addAll(items);
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
			if (isDestroyed())
				return;

			if (isFirstLoad)
				_progressDialog.dismiss();
			else
				_listView.onRefreshComplete();

			if (exception != null)
			{
				Toast.makeText(CatalogBrowserActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			}
			else
				_adapter.notifyDataSetChanged();
		}
	}

	class ListAdapter extends BaseAdapter
	{

		public ListAdapter(long nodeId)
		{
			// generateData();
		}

		void generateData()
		{
			Random random = new Random();

			for (int i = 0; i < 4; i++)
			{
				CatalogNode node = new CatalogNode();
				node.setName("��������� " + Integer.toString(i));
				node.setMinPrice(random.nextInt(5000));
				node.setCount(random.nextInt(100));
				_nodes.add(node);
			}

			for (int i = 0; i < 10; i++)
			{
				CatalogItem item = new CatalogItem();
				item.setName("����� " + Integer.toString(i));
				// item.setPrice(random.nextInt(5000));
				item.setRating(random.nextInt(5));
				_items.add(item);
			}
		}

		@Override
		public int getCount()
		{
			return _nodes.size() + _items.size();
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
							Repository.get(CatalogBrowserActivity.this)
									.getCatalogManager().getSettings()
									.getRatingColor());

			nameTextView.setText(item.getName());
			descriptionTextView.setText(Double.toString(item.getPrice())
					+ " ������");

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
			if (item.getIco() != null)
				ImageDownloadTask.startNew(imgView,
						CatalogBrowserActivity.this, item.getIco());

			ratingBar.setRating((float) item.getRating());
		}

		void initNodeView(View view, CatalogNode node)
		{
			TextView nameTextView = (TextView) view
					.findViewById(R.id.nodeNameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);

			nameTextView.setText(node.getName());

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
			imgView.setBackground(getNodeIconBackground());
			if (node.getIcon() != null)
				ImageDownloadTask.startNew(imgView,
						CatalogBrowserActivity.this, node.getIcon());

			descriptionTextView.setText(getNodeDescription(node));
		}

		String getNodeDescription(CatalogNode node)
		{
			String description = String.format("%d ������� �� %d ������",
					node.getCount(), (int) node.getMinPrice());
			return description;
		}

		public Drawable getNodeIconBackground()
		{
			float radius = 20.0f;
			float[] rads = new float[]
			{ radius, radius, radius, radius, radius, radius, radius, radius };
			RoundRectShape shape = new RoundRectShape(rads, null, null);
			ShapeDrawable drawable = new ShapeDrawable(shape);
			drawable.getPaint().setColor(
					Repository.get(null).getCatalogManager().getSettings()
							.getBackground());

			return drawable;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup viewGroup)
		{
			View view = null;

			if (index < _nodes.size())
			{
				CatalogNode node = _nodes.get(index);

				view = getLayoutInflater().inflate(
						R.layout.catalog_browser_activity_node, viewGroup,
						false);
				view.setTag(node);

				initNodeView(view, node);
			}
			else
			{
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
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3)
	{
		if (view.getTag() instanceof CatalogNode)
		{
			CatalogNode node = (CatalogNode) view.getTag();

			Intent intent = new Intent(this, CatalogBrowserActivity.class);
			intent.putExtra(CatalogBrowserActivity.NODE_ID_EXTRA_KEY,
					node.getId());
			intent.putExtra(CatalogBrowserActivity.NODE_NAME_EXTRA_KEY,
					node.getName());

			startActivity(intent);
		}
		else
		{
			CatalogItem item = (CatalogItem) view.getTag();

			Intent intent = new Intent(this, ItemActivity.class);
			intent.putExtra("ItemTitle", item.getName());
			intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, item.getId());
			startActivity(intent);
		}
	}
}
