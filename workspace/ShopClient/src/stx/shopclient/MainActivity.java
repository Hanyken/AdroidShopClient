package stx.shopclient;

import stx.shopclient.ui.common.BaseActivity;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	public View createMainView() {
		View view = getLayoutInflater().inflate(R.layout.test_list_activity, null);
		return view;
	}
}
