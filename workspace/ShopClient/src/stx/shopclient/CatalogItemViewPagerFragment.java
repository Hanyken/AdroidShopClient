package stx.shopclient;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.ui.common.GridPagerAdapter;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CatalogItemViewPagerFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_catalogitems_viewpager, null);
		
		ViewPager viewPager = (ViewPager)view.findViewById(R.id.product_viewpager);
		CirclePageIndicator pageIndicator = (CirclePageIndicator)view.findViewById(R.id.page_indicator);
		
		List<String> list = new ArrayList<String>();
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		list.add("test5");
		list.add("test6");
		list.add("test1");
		list.add("test1");
		list.add("test2");
		list.add("test3");
		list.add("test4");
		list.add("test5");
		list.add("test6");
		list.add("test1");
		
		
		TestGridAdapter adapter = new TestGridAdapter(this.getActivity(), list);
		
		viewPager.setAdapter(adapter);
		pageIndicator.setViewPager(viewPager);
		
		return view;
	}
	
	private class TestGridAdapter extends GridPagerAdapter<String> {
		Context _context;
		
        public TestGridAdapter(Context context, List<String> list) {
            super(list, context);
            
            _context = context;
        }

		@Override
		protected View createItemView(String item) {
			
			LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.view_catalog_item_image_text, null);
			
			TextView textView = (TextView)view.findViewById(R.id.textView);
			textView.setText(item);
			return view;
		}

        
    }
}
