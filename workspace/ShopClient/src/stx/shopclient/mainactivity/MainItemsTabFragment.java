package stx.shopclient.mainactivity;

import com.astuetz.PagerSlidingTabStrip;

import stx.shopclient.R;
import stx.shopclient.R.id;
import stx.shopclient.R.layout;
import stx.shopclient.repository.Repository;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainItemsTabFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.main_activity_items_tabs_fragment, container, false);

		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		
		tabStrip.setTabNames(new String[]
		{ "Популярное", "Избранное", "Последнее" });
		
		tabStrip.setIndicatorColor(Repository.getIntent().getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setUnderlineColor(Repository.getIntent().getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setBackgroundColor(Repository.getIntent().getCatalogManager()
				.getSettings().getItemPanelColor());

		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout);
		tabStrip.setFrameLayout(frameLayout);

		return view;
	}
}
