package stx.shopclient.itemactivity;

import java.text.DecimalFormat;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.repository.Repository;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.styles.ImageButtonDrawable;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemButtonBarFragment extends Fragment implements OnClickListener
{
	private TextView lblPrice;
	private RatingBar rtbRating;
	private TextView lblOverview;
	private TextView lblRepost;

	private Button btnOverview;
	private Button btnShare;
	private Button btnOrder;

	CatalogSettings settings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.item_activity_buttonbar_fragment,
				container, false);

		lblPrice = (TextView) view.findViewById(R.id.lblPrice);
		rtbRating = (RatingBar) view.findViewById(R.id.rtbRating);
		lblOverview = (TextView) view.findViewById(R.id.lblOverview);
		lblRepost = (TextView) view.findViewById(R.id.lblShare);

		btnOverview = (Button) view.findViewById(R.id.btnOverview);
		btnShare = (Button) view.findViewById(R.id.btnShare);
		btnOrder = (Button) view.findViewById(R.id.btnOrder);
		
		setThems(view);

		btnShare.setOnClickListener(this);
		btnOverview.setOnClickListener(this);
		btnOrder.setOnClickListener(this);

		return view;
	}

	private void setThems(View view)
	{
		settings = Repository.getIntent().getCatalogManager().getSettings();

		RelativeLayout rlButtonPanel = (RelativeLayout) view
				.findViewById(R.id.rlButtonPanel);

		rlButtonPanel.setBackgroundColor(settings.getItemPanelColor());
		btnOrder.setTextColor(settings.getForegroundColor());
		btnOrder.setBackground(getBueButtonDrawable());

		btnShare.setBackground(getShareButtonDrawable());
		btnOverview.setBackground(getCommentButtonDrawable());
		
		lblOverview.setTextColor(settings.getCountButtonLableColor());
		lblRepost.setTextColor(settings.getCountButtonLableColor());
		
		LayerDrawable stars = (LayerDrawable) rtbRating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(settings.getRatingColor(),
				PorterDuff.Mode.SRC_ATOP);
	}

	public void setPrice(double value)
	{
		DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
		lblPrice.setText(format.format(value) + " руб.");
	}

	public void setRating(double value)
	{
		rtbRating.setRating((float) value);
	}

	public void setOverviewCount(int value)
	{
		lblOverview.setText(String.format("%d", value));
	}

	public void setRepostCount(int value)
	{
		lblRepost.setText(String.format("%d", value));
	}

	public void setCanBuy(boolean value)
	{
		btnOrder.setEnabled(value);
	}

	public void addAnalogs(String title, long[] ids)
	{
		Fragment analogFragment = ItemAnalogsFragment.getIntent(title, ids);
		getFragmentManager().beginTransaction().add(R.id.llAnalogs, analogFragment).commit();
	}
	
	@Override
	public void onClick(View view)
	{
		((ItemActivity) getActivity()).onBarButtonClick(view);
	}

	
	
	
	private Drawable getBueButtonDrawable()
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[] { android.R.attr.state_pressed }, press);
		drawable.addState(new int[] { -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
	}
	
	private Drawable getShareButtonDrawable()
	{
		StateListDrawable drawable = new StateListDrawable();
		Bitmap bmp = settings.getImageFromPath(getResources(), "Share"); //BitmapFactory.decodeResource(getResources(), R.drawable.img_share);
		Drawable normal = new ImageButtonDrawable(bmp);
		Bitmap bmpPress = settings.getImageFromPath(getResources(), "SharePress"); //BitmapFactory.decodeResource(getResources(), R.drawable.img_share_press);
		Drawable press = new ImageButtonDrawable(bmpPress);

		drawable.addState(new int[] { android.R.attr.state_pressed }, press);
		drawable.addState(new int[0], normal);
		return drawable;
	}
	
	private Drawable getCommentButtonDrawable()
	{
		StateListDrawable drawable = new StateListDrawable();
		Bitmap bmp = settings.getImageFromPath(getResources(), "Comment"); //BitmapFactory.decodeResource(getResources(), R.drawable.img_share);
		Drawable normal = new ImageButtonDrawable(bmp);
		Bitmap bmpPress = settings.getImageFromPath(getResources(), "CommentPress"); //BitmapFactory.decodeResource(getResources(), R.drawable.img_share_press);
		Drawable press = new ImageButtonDrawable(bmpPress);

		drawable.addState(new int[] { android.R.attr.state_pressed }, press);
		drawable.addState(new int[0], normal);
		return drawable;
	}
}
