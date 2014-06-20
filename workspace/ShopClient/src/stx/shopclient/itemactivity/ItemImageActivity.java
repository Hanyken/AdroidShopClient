package stx.shopclient.itemactivity;


import stx.shopclient.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

public class ItemImageActivity extends Activity
{
	public static final String SELECTED_ITEM_POSITION = "SelectedItemPosition";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_image_activity);
		
		Intent intent = getIntent();
		long itemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("");		

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.product_viewpager);		

		ImagePageAdapter adapter = new ImagePageAdapter(this, itemId);

		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(intent.getIntExtra(SELECTED_ITEM_POSITION, 0));
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
