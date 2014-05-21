package stx.shopclient.itemactivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import stx.shopclient.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
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
		btnOrder = (Button)view.findViewById(R.id.btnOrder);

		btnShare.setOnClickListener(this);
		btnOverview.setOnClickListener(this);
		btnOrder.setOnClickListener(this);

		return view;
	}

	public void setPrice(float value)
	{
		DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
		lblPrice.setText(format.format(value) + " руб.");
	}

	public void setRating(float value)
	{
		rtbRating.setRating(value);
	}

	public void setOverviewCount(int value)
	{
		lblOverview.setText(String.format("%d", value));
	}

	public void setRepostCount(int value)
	{
		lblRepost.setText(String.format("%d", value));
	}

	@Override
	public void onClick(View view)
	{
		((ItemActivity)getActivity()).onBarButtonClick(view);
	}
}
