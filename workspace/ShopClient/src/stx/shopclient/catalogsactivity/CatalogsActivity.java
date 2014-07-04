package stx.shopclient.catalogsactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.ActionType;
import stx.shopclient.entity.Catalog;
import stx.shopclient.searchresultsactivity.SearchResultsActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CatalogsActivity extends BaseActivity implements
	SearchView.OnQueryTextListener, SearchView.OnCloseListener
{
	static final int MENU_FILTER_CATEGORY = 1;
	static final int MENU_FILTER_NAME = 2;

	PullToRefreshListView _listView;
	List<Catalog> _catalogs = new ArrayList<Catalog>();
	CatalogListAdapter _adapter = new CatalogListAdapter();
	Collection<ActionType> _actionTypes;
	List<ActionType> _selectedActionTypes = new ArrayList<ActionType>();
	SearchView _searchView;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Каталоги");		
		
		_searchView = new SearchView(this);
		
		View view = getLayoutInflater().inflate(R.layout.catalogs_activity,
				parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.PULL_FROM_END);
		_listView.setAdapter(_adapter);

		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.clear();
		
		MenuItem nameFilterItem = menu.add(0, MENU_FILTER_CATEGORY, 0, "Фильтр по названию");
		nameFilterItem.setIcon(android.R.drawable.ic_menu_search);
		nameFilterItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		nameFilterItem.setActionView(_searchView);
		
		MenuItem categoryFilterItem = menu.add(0, MENU_FILTER_CATEGORY, 0, "Фильтр по категориям");
		categoryFilterItem.setIcon(R.drawable.img_filter);
		categoryFilterItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case MENU_FILTER_CATEGORY:

			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onQueryTextChange(String arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String text)
	{
		if (StringUtils.isBlank(text))
			return false;

		Intent intent = new Intent(this, SearchResultsActivity.class);
		intent.putExtra(SearchResultsActivity.EXTRA_KEY_QUICKSEARCH, true);
		intent.putExtra(SearchResultsActivity.EXTRA_KEY_QUICKSEARCH_QUERY, text);
		startActivity(intent);

		return true;
	}
	
	@Override
	public boolean onClose()
	{
		// TODO Auto-generated method stub
		return false;
	}

	class CatalogListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _catalogs.size();
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

		@Override
		public View getView(int index, View arg1, ViewGroup arg2)
		{
			Catalog catalog = _catalogs.get(index);

			View view = getLayoutInflater().inflate(
					R.layout.catalogs_activity_item, arg2, false);

			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			TextView textView = (TextView) view.findViewById(R.id.nameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);

			if (StringUtils.isNoneBlank(catalog.getLogo()))
				setImage(imageView, catalog.getLogo());
			textView.setText(catalog.getName());
			descriptionTextView.setText(catalog.getDescription());

			return view;
		}
	}

}
