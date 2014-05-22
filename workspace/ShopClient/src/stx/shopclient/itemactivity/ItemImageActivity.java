package stx.shopclient.itemactivity;


import stx.shopclient.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ItemImageActivity extends Activity implements OnClickListener
{
	private LinearLayout llPanel;
	private Button btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_image_activity);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.product_viewpager);
		
		llPanel = (LinearLayout)findViewById(R.id.llPanel);
		btnBack = (Button)findViewById(R.id.btnBack);
		

		ImagePageAdapter adapter = new ImagePageAdapter(this, getIntent().getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0));

		viewPager.setAdapter(adapter);
		
		viewPager.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		finish();
	}
	
	
	public void onImageClick(View v)
	{
		if (llPanel.getVisibility() == View.GONE)
			ShowPanel();
		else
			HidePanel();
	}
	
	private void ShowPanel()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llPanel, "y", -150, 0);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				llPanel.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{}
		});
		objectAnimator.setDuration(300);
		objectAnimator.start();
	}
	private void HidePanel()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llPanel, "y", 0, -150);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{				
				
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				llPanel.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{}
		});
		objectAnimator.setDuration(300);
		objectAnimator.start();
	}
}
