package stx.shopclient.itemactivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.overviewactivity.OverviewActivity;
import stx.shopclient.parsers.PropertyParser;
import stx.shopclient.parsers.OverviewParser;
import stx.shopclient.repository.Repository;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
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

		
		//OverviewParser parser = new OverviewParser();
		//parser.getElements(parser.TEST);
		
		//ItemParser parser = new ItemParser();
		//parser.getElements(ItemParser.TEST);
		
		//PropertyParser parser = new PropertyParser();
		//parser.getElements(PropertyParser.TEST);
		
		PropertyParser parser = new PropertyParser();
		parser.getElements(parser.TEST);
		
		Intent intent = getIntent();

		Long itemId = 0l;
		// Получаю параметры
		String itemTitle = intent.getStringExtra("ItemTitle");
		String strId = intent.getStringExtra(ITEM_ID_EXTRA_KEY);
		if (strId != null)
		{
			itemId = Long.parseLong(strId);
		}
		else
		{
			itemId = intent.getLongExtra(ITEM_ID_EXTRA_KEY, 0);

		}
		_Item = Repository.getIntent().getItemsManager().getItem(itemId);

		ItemImageFragment imageFragment = (ItemImageFragment) getFragmentManager()
				.findFragmentById(R.id.frgImages);
		ItemButtonBarFragment buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		TextView txtProperty = (TextView) view.findViewById(R.id.txtProperty);

		imageFragment.setImages(_Item.getId());

		buttonBar.setPrice(Repository.getIntent().getPropertiesManager()
				.getItemPrice(itemId));
		buttonBar.setRating(_Item.getRating());
		buttonBar.setOverviewCount(_Item.getOverviewsCount());
		buttonBar.setCanBuy(intent.getBooleanExtra(ITEM_BUY_EXTRA_KEY, true));

		setProperty(txtProperty);
		getActionBar().setTitle(itemTitle);

		return view;
	}

	public void setProperty(TextView txtProperty)
	{
		String description = _Item.getDescription() + "\n\n\n";
		String text = "\t-Клевость - самый клевый\n\t-Привлекательность - привлекательнее товара не существует\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nНу и тут какие то еще вкусности про товар";

		SpannableStringBuilder str = new SpannableStringBuilder("Общее\n"
				+ description + "Характеристики\n" + text);
		str.setSpan(new StyleSpan(Typeface.BOLD), 0, 5,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		str.setSpan(new StyleSpan(Typeface.BOLD), description.length() + 5,
				description.length() + 20,
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
			// TODO сделать передачу картинки
			// sendIntent.setType("image/jpeg");
			// sendIntent.putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("file://"+file.getAbsolutePath()));
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
