package stx.shopclient.mainactivity;

import org.apache.commons.lang3.StringUtils;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.R.layout;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.Repository;
import stx.shopclient.searchresultsactivity.SearchResultsActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends BaseActivity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener
{

	@Override
	public View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.main_activity, parent,
				false);

		getActionBar()
				.setTitle(
						Repository.get(this).getCatalogManager().getCatalog()
								.getName());

		ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);

		scrollView.fullScroll(ScrollView.FOCUS_UP);

//		parent.getRootView().setBackgroundResource(
//				R.drawable.catalog_background_test);

		return view;
	}

	@Override
	protected void onHomeButtonClick()
	{
		showOrHideMenu();
	}

	@Override
	protected void onMainMenuItemClick(MainMenuItem item)
	{
		if (item.getId() != MainMenuItem.HOME_MENU_ITEM_ID)
			super.onMainMenuItemClick(item);
	}

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.discount_list_activity_menu, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);

		return true;
	}

	@Override
	public boolean onClose()
	{
		// TODO Auto-generated method stub
		return false;
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
}
