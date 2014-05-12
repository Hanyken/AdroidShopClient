package stx.shopclient.mainactivity;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.R.layout;
import stx.shopclient.mainmenu.MainMenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class MainActivity extends BaseActivity {

	@Override
	public View createMainView(ViewGroup parent) {
		View view = getLayoutInflater().inflate(R.layout.main_activity, parent,
				false);
		
		getActionBar().setTitle("Магазин 1");

		ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);

		scrollView.fullScroll(ScrollView.FOCUS_UP);

		return view;
	}

	@Override
	protected void onHomeButtonClick() {
		showOrHideMenu();
	}

	@Override
	protected void onMainMenuItemClick(MainMenuItem item) {
		if (item.getId() != MainMenuItem.HOME_MENU_ITEM_ID)
			super.onMainMenuItemClick(item);
	}
}
