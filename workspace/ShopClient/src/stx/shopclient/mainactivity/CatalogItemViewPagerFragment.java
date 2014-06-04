package stx.shopclient.mainactivity;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.R.id;
import stx.shopclient.R.layout;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.GridPagerAdapter;
import stx.shopclient.utils.ImageDownloadTask;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CatalogItemViewPagerFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(
				R.layout.main_activity_catalogitems_viewpager_fragment,
				container, false);

		ViewPager viewPager = (ViewPager) view
				.findViewById(R.id.product_viewpager);
		CirclePageIndicator pageIndicator = (CirclePageIndicator) view
				.findViewById(R.id.page_indicator);

		List<CatalogItem> list = new ArrayList<CatalogItem>();
		list.addAll(Repository.getIntent().getItemsManager().getFavorits());

		TestGridAdapter adapter = new TestGridAdapter(this.getActivity(), list);

		viewPager.setAdapter(adapter);
		pageIndicator.setViewPager(viewPager);

		return view;
	}

	private class TestGridAdapter extends GridPagerAdapter<CatalogItem>
	{
		Context _context;

		public TestGridAdapter(Context context, List<CatalogItem> list)
		{
			super(list, context);

			_context = context;
		}

		@Override
		protected void onItemClick(CatalogItem item)
		{
			Intent intent = new Intent(_context, ItemActivity.class);
			intent.putExtra("ItemTitle", item.getName());
			intent.putExtra("ItemID", item.getId());

			startActivity(intent);
		}

		@Override
		protected View createItemView(CatalogItem item)
		{

			LayoutInflater inflater = (LayoutInflater) _context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater
					.inflate(
							R.layout.main_activity_catalogitems_viewpager_fragment_item,
							null);

			// TODO: после этого все уезжает
			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
			// imgView.setImageBitmap(Repository.getIntent().getImagesManager().getImage(item.getIco()));
			ImageDownloadTask.startNew(imgView,
					"file://"
							+ Repository.getIntent().getImagesManager()
									.getImagePath(item.getIco()));

			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(item.getName());

			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			ratingBar.setRating((float) item.getRating());

			BaseActivity.setRatingBarColor(ratingBar, Repository.getIntent().getCatalogManager().getSettings()
							.getRatingColor());
			
			return view;
		}

	}
}
