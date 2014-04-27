package stx.shopclient;

import stx.shopclient.mainmenu.MainMenuItem;
import android.content.Intent;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	public View createMainView() {
		View view = getLayoutInflater().inflate(R.layout.screen_main, null);
		
		return view;
	}
	
	@Override
	protected void onMainMenuItemClick(MainMenuItem item) {
		super.onMainMenuItemClick(item);
		
		Intent intent = new Intent(this, this.getClass());
		startActivity(intent);
	}
}
