package stx.shopclient.itemactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.viewpagerindicator.CirclePageIndicator;

import stx.shopclient.R;
import stx.shopclient.repository.Repository;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		String[] images = Repository.getImages(itemId);
		List<String> items = new ArrayList<String>();
		for(String el : images)
			items.add(el);

		ItemImagePageAdapter adapter = new ItemImagePageAdapter(this.getActivity(), itemId, items);

		viewPager.setAdapter(adapter);
		pageIndicator.setViewPager(viewPager);
	}
}
