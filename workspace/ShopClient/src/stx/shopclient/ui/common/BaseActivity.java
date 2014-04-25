package stx.shopclient.ui.common;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	DrawerLayout _drawerLayout;
	FrameLayout _mainViewContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.base_activity);

		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView _menuList = (ListView) findViewById(R.id.leftMenuList);

		LeftMenuAdapter leftMenuAdapter = new LeftMenuAdapter(this);
		_menuList.setAdapter(leftMenuAdapter);

		_mainViewContainer = (FrameLayout) findViewById(R.id.mainViewContainer);

		View mainView = createMainView();

		if (mainView != null) {
			_mainViewContainer.addView(mainView);
		}
	}

	public View createMainView() {
		return null;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (_drawerLayout.isDrawerOpen(Gravity.LEFT))
				_drawerLayout.closeDrawer(Gravity.LEFT);
			else
				_drawerLayout.openDrawer(Gravity.LEFT);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class LeftMenuAdapter extends BaseAdapter {

		Context _context;
		List<String> _menuItems = new ArrayList<String>();

		public LeftMenuAdapter(Context context) {
			_context = context;

			_menuItems.add("Главная");
			_menuItems.add("Корзина");
			_menuItems.add("Настройки");
			_menuItems.add("О программе");
		}

		@Override
		public int getCount() {
			return _menuItems.size();
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

		@Override
		public View getView(int index, View arg1, ViewGroup arg2) {
			TextView textView = new TextView(_context);
			textView.setTextSize(20);
			textView.setTextColor(Color.WHITE);
			textView.setPadding(20, 20, 20, 20);
			textView.setText(_menuItems.get(index));
			return textView;
		}

	}
}
