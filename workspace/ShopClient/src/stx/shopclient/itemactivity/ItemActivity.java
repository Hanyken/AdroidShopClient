package stx.shopclient.itemactivity;

import java.util.Collection;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.overviewactivity.OverviewActivity;
import stx.shopclient.repository.Repository;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemActivity extends BaseActivity
{
	private CatalogItem _Item;

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";
	public static final String ITEM_BUY_EXTRA_KEY = "CanBuyItem";

	LinearLayout myGallery;
	
	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.item_activity, parent,
				false);
		
		Intent intent = getIntent();
		
		CatalogSettings settings = Repository.getIntent().getCatalogManager().getSettings();
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(settings.getBackground()));
		
		Long itemId = intent.getLongExtra(ITEM_ID_EXTRA_KEY, 0);
		
		_Item = Repository.getIntent().getItemsManager().getItem(itemId);

		ItemImageFragment imageFragment = (ItemImageFragment) getFragmentManager()
				.findFragmentById(R.id.frgImages);
		ItemButtonBarFragment buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		TextView txtProperty = (TextView) view.findViewById(R.id.txtProperty);

		imageFragment.setImages(_Item.getId());

		buttonBar.setPrice(_Item.getPrice());
		buttonBar.setRating(_Item.getRating());
		buttonBar.setOverviewCount(_Item.getOverviewsCount());
		buttonBar.setCanBuy(intent.getBooleanExtra(ITEM_BUY_EXTRA_KEY, true));
		
		Collection<CatalogItem> analogs = Repository.getIntent().getItemsManager().getItems(_Item.getNodeId());
		long[] ids = new long[analogs.size() - 1];
		int i = 0;
		for(CatalogItem el : analogs)
		{
			if (el.getId() != itemId)
			{
				ids[i] = el.getId();
				i++;
			}
		}
		buttonBar.addAnalogs("Попробуйте так же", ids);

		setProperty(txtProperty);
		getActionBar().setTitle(_Item.getName());

		return view;
	}

	public void setProperty(TextView txtProperty)
	{
		String description = _Item.getDescription() + "\n\n\n";
		String text = _Item.getPropertyString();

		String firstTitle = "Общее\n\n\t";
		String secondTitle = "Характеристики\n\n";

		SpannableStringBuilder str = new SpannableStringBuilder(firstTitle
				+ description + secondTitle + text);
		str.setSpan(new StyleSpan(Typeface.BOLD), 0, firstTitle.length(),
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		str.setSpan(
				new StyleSpan(Typeface.BOLD),
				description.length() + firstTitle.length(),
				description.length() + firstTitle.length()
						+ secondTitle.length(),
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

		txtProperty.setText(str);
	}

	public void onBarButtonClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btnOverview:
			Intent intent = new Intent(this, OverviewActivity.class);
			intent.putExtra(ITEM_ID_EXTRA_KEY, _Item.getId());
			startActivity(intent);
			break;

		case R.id.btnShare:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.setType("text/plain");
			sendIntent.putExtra(Intent.EXTRA_TEXT, _Item.getName());
			sendIntent.setType("image/jpeg");
			sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+Repository.getIntent().getImagesManager().getImagePath(_Item.getIco())));
			startActivity(sendIntent);

			break;

		case R.id.btnOrder:
			Intent orderIntent = new Intent(this, OrderActivity.class);
			orderIntent.putExtra(ITEM_ID_EXTRA_KEY, _Item.getId());
			orderIntent.putExtra("ItemTitle", _Item.getName());
			startActivity(orderIntent);
			break;
		}
	}
}
