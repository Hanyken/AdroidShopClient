package stx.shopclient.mainscreen;

import stx.shopclient.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainRootNodesBrowser extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.screen_main_rootnodes_browser, null);
		
		TabHost tabHost = (TabHost)view.findViewById(R.id.tabhost);

		tabHost.setup();
		
		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");

		spec.setContent(R.id.tab1);
		spec.setIndicator("Список");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Плитки");
		tabHost.addTab(spec);		

		tabHost.setCurrentTab(0);
		
		return view;
	}
}
