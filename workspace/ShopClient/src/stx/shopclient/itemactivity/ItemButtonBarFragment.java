package stx.shopclient.itemactivity;

import java.text.DecimalFormat;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.repository.Repository;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
		settings = Repository.getIntent().getCatalogManager()
				.getSettings();

		RelativeLayout rlButtonPanel = (RelativeLayout) view
				.findViewById(R.id.rlButtonPanel);
		
		rlButtonPanel.setBackgroundColor(settings.getItemPanelColor());
		//btnOrder.setBackgroundColor(settings.getBackground());
		btnOrder.setTextColor(settings.getForegroundColor());
		btnOrder.setBackground(new ButtonDrawble());
		//button = new Button(newContext);
		
		LayerDrawable stars = (LayerDrawable) rtbRating.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(settings.getRatingColor(), PorterDuff.Mode.SRC_ATOP);
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

	@Override
	public void onClick(View view)
	{
		((ItemActivity) getActivity()).onBarButtonClick(view);
	}
	
	class ButtonDrawble extends Drawable
	{

		@Override
		public void draw(Canvas canvas)
		{
			canvas.drawColor(settings.getBackground());
			Paint p = new Paint();
			p.setColor(getResources().getColor(android.R.color.black));
			canvas.drawRect(1, 1, 2, canvas.getHeight(), p);
			canvas.drawRect(1, canvas.getHeight()-2, canvas.getWidth(), canvas.getHeight(), p);
		}

		@Override
		public int getOpacity()
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAlpha(int alpha)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setColorFilter(ColorFilter cf)
		{
			
		}
		
	}
}
