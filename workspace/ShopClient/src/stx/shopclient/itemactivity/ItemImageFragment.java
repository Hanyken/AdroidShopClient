package stx.shopclient.itemactivity;

import java.util.ArrayList;
import java.util.List;

import com.viewpagerindicator.CirclePageIndicator;

import stx.shopclient.R;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemImageFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.item_activity_imageitems_viwepager_fragment, container, false);
		
		ViewPager viewPager = (ViewPager)view.findViewById(R.id.product_viewpager);
		CirclePageIndicator pageIndicator = (CirclePageIndicator)view.findViewById(R.id.page_indicator);
		
		List<String> items = new ArrayList<String>();
		items.add("1");
		items.add("2");
		items.add("3");
		items.add("4");
		
		ItemImagePageAdapter adapter = new ItemImagePageAdapter(this.getActivity(), items);
		
		viewPager.setAdapter(adapter);
		pageIndicator.setViewPager(viewPager);
		
		return view;
	}
}
