package stx.shopclient.mainactivity;

import java.util.ArrayList;
import java.util.Collection;
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
	List<CatalogItem> _items = new ArrayList<CatalogItem>();
	ItemsGridAdapter _adapter;
	CirclePageIndicator _pageIndicator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(
				R.layout.main_activity_catalogitems_viewpager_fragment,
				container, false);

		ViewPager viewPager = (ViewPager) view
				.findViewById(R.id.product_viewpager);
		_pageIndicator = (CirclePageIndicator) view
				.findViewById(R.id.page_indicator);

		_adapter = new ItemsGridAdapter(this.getActivity(), _items);

		viewPager.setAdapter(_adapter);
		_pageIndicator.setViewPager(viewPager);

		return view;
	}

	public void setItems(Collection<CatalogItem> items)
	{
		_items.clear();
		_adapter.notifyDataSetChanged();
		_items.addAll(items);
		_adapter.notifyDataSetChanged();
		if (_adapter.getCount() > 1)
			_pageIndicator.setVisibility(View.VISIBLE);
		else
			_pageIndicator.setVisibility(View.INVISIBLE);
	}

	private class ItemsGridAdapter extends GridPagerAdapter<CatalogItem>
	{
		Context _context;

		public ItemsGridAdapter(Context context, List<CatalogItem> list)
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

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
			if (item.getIco() != null)
				ImageDownloadTask.startNew(imgView, getActivity(),
						item.getIco());

			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(item.getName());

			RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
			ratingBar.setRating((float) item.getRating());

			BaseActivity.setRatingBarColor(ratingBar,
					Repository.get(getActivity()).getCatalogManager()
							.getSettings().getRatingColor());

			return view;
		}

	}
}
