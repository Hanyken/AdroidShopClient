package stx.shopclient;

import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.discountactivity.DiscountListActivity;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.mainmenu.MainMenuListAdapter;
import stx.shopclient.messagesactivity.MessagesListActivity;
import stx.shopclient.searchactivity.SearchActivity;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class BaseActivity extends FragmentActivity
{
	DrawerLayout _drawerLayout;
	LinearLayout _mainViewContainer;
	MainMenuListAdapter _mainMenuListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.base_activity);

		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener()
		{

			@Override
			public void onDrawerStateChanged(int arg0)
			{

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1)
			{

			}

			@Override
			public void onDrawerOpened(View arg0)
			{
				_mainMenuListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onDrawerClosed(View arg0)
			{

			}
		});

		ListView _menuList = (ListView) findViewById(R.id.mainMenuList);

		_mainMenuListAdapter = new MainMenuListAdapter(this);
		_menuList.setAdapter(_mainMenuListAdapter);
		_menuList.setOnItemClickListener(new MainMenuOnClickListener());

		_mainViewContainer = (LinearLayout) findViewById(R.id.mainViewContainer);

		View mainView = createMainView(_mainViewContainer);

		if (mainView != null)
		{
			_mainViewContainer.addView(mainView);
		}
	}

	protected void onMainMenuItemClick(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.HOME_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, MainActivity.class);
			NavUtils.navigateUpTo(this, intent);
		}
		else if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra(SearchActivity.TITLE_EXTRA_KEY,
					getSearchActivityTitle());
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.CART_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, CartActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.DISCOUNT_CARDS_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, DiscountListActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.MESSAGES_MENU_ITEM)
		{
			Intent intent = new Intent(this, MessagesListActivity.class);
			startActivity(intent);
		}
	}

	protected String getSearchActivityTitle()
	{
		return getActionBar().getTitle().toString();
	}

	protected View createMainView(ViewGroup parent)
	{
		return null;
	}

	protected void onHomeButtonClick()
	{
		onBackPressed();
	}

	protected void showOrHideMenu()
	{
		if (_drawerLayout.isDrawerOpen(Gravity.LEFT))
			_drawerLayout.closeDrawer(Gravity.LEFT);
		else
			_drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			onHomeButtonClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class MainMenuOnClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int index,
				long id)
		{

			MainMenuItem item = (MainMenuItem) adapter.getAdapter().getItem(
					index);

			_drawerLayout.closeDrawer(Gravity.LEFT);
			onMainMenuItemClick(item);
		}
	}
}
