package stx.shopclient.mainactivity;

import stx.shopclient.R;
import stx.shopclient.R.id;
import stx.shopclient.R.layout;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainItemsTabFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = getActivity().getLayoutInflater().inflate(R.layout.main_activity_items_tabs_fragment, container, false);
		
		TabHost tabHost = (TabHost)view.findViewById(R.id.tabhost);

		tabHost.setup();
		
		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");

		spec.setContent(R.id.popularLayout);
		spec.setIndicator("Популярное");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tag2");
		spec.setContent(R.id.favoritesLayout);
		spec.setIndicator("Избранное");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tag3");
		spec.setContent(R.id.recentLayout);
		spec.setIndicator("Последнее");
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		
		final TabWidget tabWidget = tabHost.getTabWidget();
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
		    final ViewGroup tab = (ViewGroup) tabWidget.getChildAt(i);
		    final TextView tabTextView = (TextView) tab.getChildAt(1); // Magic number
		    tabTextView.setTextSize(10);
		}
		
		return view;
	}
}
