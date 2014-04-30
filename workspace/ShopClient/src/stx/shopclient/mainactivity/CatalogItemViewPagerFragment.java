package stx.shopclient.mainactivity;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import stx.shopclient.R.id;
import stx.shopclient.R.layout;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.ui.common.GridPagerAdapter;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogItemViewPagerFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_activity_catalogitems_viewpager_fragment, container, false);
		
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
        protected void onItemClick(String item) {
        	 Intent intent = new Intent(_context, ItemActivity.class);    
        	 intent.putExtra("ItemTitle",item);
        	 intent.putExtra("ItemID","123");
        	 
        	 startActivity(intent);
        }

		@Override
		protected View createItemView(String item) {
			
			LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.main_activity_catalogitems_viewpager_fragment_item, null);
			
			TextView textView = (TextView)view.findViewById(R.id.textView);
			textView.setText(item);
			
			RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
			ratingBar.setRating(3.5f);
			
			return view;
		}

        
    }
}
