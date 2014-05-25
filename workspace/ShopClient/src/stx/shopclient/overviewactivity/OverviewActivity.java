package stx.shopclient.overviewactivity;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.itemactivity.ItemActivity;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class OverviewActivity extends BaseActivity
{
	private final int MI_COMMENT = 1;

	private LinearLayout llOverview;
	private RatingBar rtgRaiting;
	private TextView txtOverview; 

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.overview_activity,
				parent, false);

		Intent intent = getIntent();
		long itemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		
		ListView lstMain = (ListView) view.findViewById(R.id.lstMain);

		llOverview = (LinearLayout) view.findViewById(R.id.llOverviw);
		txtOverview = (TextView)view.findViewById(R.id.txtOverview);
		rtgRaiting = (RatingBar)view.findViewById(R.id.rtgRating);

		OverviewAdapter adapter = new OverviewAdapter(this, lstMain, itemId);

		lstMain.setAdapter(adapter);
		lstMain.setOnScrollListener(adapter);

		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.clear();
		MenuItem commentItem = menu.add(0, MI_COMMENT, 0, "Коментарий");
		commentItem.setIcon(R.drawable.img_comment);
		commentItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case MI_COMMENT:
			if (llOverview.getVisibility() == View.GONE)
				ShowOverview();
			else
				HideOverview();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void ShowOverview()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llOverview, "y",
				-150, 0);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				llOverview.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, 0);
				txtOverview.requestFocus();
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	private void HideOverview()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llOverview, "y",
				0, -150);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtOverview.getWindowToken(), 0);
				
				llOverview.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{}

			@Override
			public void onAnimationEnd(Animator animation)
			{}

			@Override
			public void onAnimationCancel(Animator animation)
			{}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}
}
