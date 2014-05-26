package stx.shopclient.itemactivity;

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
	private ViewPager viewPager;
	private CirclePageIndicator pageIndicator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(
				R.layout.item_activity_imageitems_viwepager_fragment,
				container, false);
		
		viewPager = (ViewPager) view.findViewById(R.id.product_viewpager);
		pageIndicator = (CirclePageIndicator) view
				.findViewById(R.id.page_indicator);
		
		return view;
	}

	public void setImages(long itemId)
	{
		ItemImagePageAdapter adapter = new ItemImagePageAdapter(this.getActivity(), itemId);

		viewPager.setAdapter(adapter);
		pageIndicator.setViewPager(viewPager);
	}
}
