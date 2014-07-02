package stx.shopclient.itemactivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogItemGroup;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.repository.Repository;
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
	private RatingBar rtgRating;
	private TextView lblOverview;
	private TextView lblRepost;

	private Button btnFavorits;
	private Button btnOverview;
	private Button btnShare;
	private Button btnOrder;

	CatalogSettings settings;

	List<ItemAnalogsFragment> _analogFragments = new ArrayList<ItemAnalogsFragment>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.item_activity_buttonbar_fragment,
				container, false);

		lblPrice = (TextView) view.findViewById(R.id.lblPrice);
		rtgRating = (RatingBar) view.findViewById(R.id.rtgRating);
		lblOverview = (TextView) view.findViewById(R.id.lblOverview);
		lblRepost = (TextView) view.findViewById(R.id.lblShare);

		btnFavorits = (Button) view.findViewById(R.id.btnFavorits);
		btnOverview = (Button) view.findViewById(R.id.btnOverview);
		btnShare = (Button) view.findViewById(R.id.btnShare);
		btnOrder = (Button) view.findViewById(R.id.btnOrder);

		setThems(view);

		btnShare.setOnClickListener(this);
		btnOverview.setOnClickListener(this);
		btnOrder.setOnClickListener(this);
		btnFavorits.setOnClickListener(this);

		return view;
	}

	private void setThems(View view)
	{
		settings = Repository.get(getActivity()).getCatalogManager()
				.getSettings();

		RelativeLayout rlButtonPanel = (RelativeLayout) view
				.findViewById(R.id.rlButtonPanel);

		rlButtonPanel.setBackgroundColor(settings.getItemPanelColor());
		btnOrder.setTextColor(settings.getForegroundColor());
		btnOrder.setBackground(BaseActivity.getButtonDrawable(settings));

		btnShare.setBackground(getButtonDrawable("Share"));
		btnOverview.setBackground(getButtonDrawable("Comment"));
		btnFavorits.setBackground(getButtonDrawable("Favorits"));

		lblOverview.setTextColor(settings.getCountButtonLableColor());
		lblRepost.setTextColor(settings.getCountButtonLableColor());

		LayerDrawable stars = (LayerDrawable) rtgRating.getProgressDrawable();
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
		rtgRating.setRating((float) value);
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

	public void clearAnalogs()
	{
		for (ItemAnalogsFragment analogFragment : _analogFragments)
			getFragmentManager().beginTransaction().remove(analogFragment)
					.commit();
	}

	public void addAnalogs(CatalogItemGroup group, Collection<CatalogItem> items)
	{
		ItemAnalogsFragment analogFragment = new ItemAnalogsFragment();
		analogFragment.setGroup(group);
		analogFragment.setItems(items);

		getFragmentManager().beginTransaction()
				.add(R.id.llAnalogs, analogFragment).commit();

		_analogFragments.add(analogFragment);
	}

	@Override
	public void onClick(View view)
	{
		((ItemActivity) getActivity()).onBarButtonClick(view);
	}

	
	private Drawable getButtonDrawable(String name)
	{
		StateListDrawable drawable = new StateListDrawable();
		Bitmap bmp = settings.getImageFromPath(getResources(), name);
		Drawable normal = new ImageButtonDrawable(bmp);
		Bitmap bmpPress = settings.getImageFromPath(getResources(), name+"Press");
		Drawable press = new ImageButtonDrawable(bmpPress);

		drawable.addState(new int[]
		{ android.R.attr.state_pressed }, press);
		drawable.addState(new int[0], normal);
		return drawable;
	}
}
