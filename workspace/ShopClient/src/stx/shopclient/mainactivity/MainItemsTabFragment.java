package stx.shopclient.mainactivity;

import com.astuetz.PagerSlidingTabStrip;

import stx.shopclient.R;
import stx.shopclient.repository.Repository;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MainItemsTabFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.main_activity_items_tabs_fragment, container, false);
		
		view.setBackgroundColor(Color.WHITE);

		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);
		
		tabStrip.setTabNames(new String[]
		{ "Популярное", "Избранное", "Последнее" });
		
		tabStrip.setIndicatorColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setUnderlineColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setBackgroundColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getItemPanelColor());

		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout);
		tabStrip.setFrameLayout(frameLayout);

		return view;
	}
}
