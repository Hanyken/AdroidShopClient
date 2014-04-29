package stx.shopclient;

import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.mainmenu.MainMenuListAdapter;
import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class BaseActivity extends FragmentActivity {
	DrawerLayout _drawerLayout;
	LinearLayout _mainViewContainer;
	MainMenuListAdapter _mainMenuListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.base_activity);

		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView _menuList = (ListView) findViewById(R.id.mainMenuList);

		_mainMenuListAdapter = new MainMenuListAdapter(this);
		_menuList.setAdapter(_mainMenuListAdapter);
		_menuList.setOnItemClickListener(new MainMenuOnClickListener());

		_mainViewContainer = (LinearLayout) findViewById(R.id.mainViewContainer);

		View mainView = createMainView(_mainViewContainer);

		if (mainView != null) {
			_mainViewContainer.addView(mainView);
		}
	}

	protected void onMainMenuItemClick(MainMenuItem item) {
		_drawerLayout.closeDrawer(Gravity.LEFT);
	}

	protected View createMainView(ViewGroup parent) {
		return null;
	}

	protected void onHomeButtonClick() {
		if (_drawerLayout.isDrawerOpen(Gravity.LEFT))
			_drawerLayout.closeDrawer(Gravity.LEFT);
		else
			_drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onHomeButtonClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class MainMenuOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int index,
				long id) {

			MainMenuItem item = (MainMenuItem) adapter.getAdapter().getItem(
					index);

			onMainMenuItemClick(item);
		}
	}
}
