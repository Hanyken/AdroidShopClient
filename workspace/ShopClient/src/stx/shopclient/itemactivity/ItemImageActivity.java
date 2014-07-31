package stx.shopclient.itemactivity;


import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.repository.Repository;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ItemImageActivity extends BaseActivity
{
	public static final String SELECTED_ITEM_POSITION = "SelectedItemPosition";
	
	
	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.item_image_activity, parent,
				false);
		
		Intent intent = getIntent();
		long itemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("");
		
		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();

		actionBar.setBackgroundDrawable(
				new ColorDrawable(settings.getBackground()));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		ViewPager viewPager = (ViewPager)view.findViewById(R.id.product_viewpager);		

		ImagePageAdapter adapter = new ImagePageAdapter(this, itemId);

		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(intent.getIntExtra(SELECTED_ITEM_POSITION, 0));
		
		return view;
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
