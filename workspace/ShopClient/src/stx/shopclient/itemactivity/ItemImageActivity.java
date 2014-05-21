package stx.shopclient.itemactivity;


import stx.shopclient.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ItemImageActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_image_activity);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.product_viewpager);
		
		
		

		ImagePageAdapter adapter = new ImagePageAdapter(this, null);

		viewPager.setAdapter(adapter);
	}
}
