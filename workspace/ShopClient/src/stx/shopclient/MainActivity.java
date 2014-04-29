package stx.shopclient;

import stx.shopclient.mainmenu.MainMenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends BaseActivity {

	@Override
	public View createMainView(ViewGroup parent) {
		View view = getLayoutInflater().inflate(R.layout.screen_main, parent, false);
		
		return view;
	}
	
	@Override
	protected void onMainMenuItemClick(MainMenuItem item) {
		super.onMainMenuItemClick(item);
		
		Intent intent = new Intent(this, this.getClass());
		startActivity(intent);
	}
}
