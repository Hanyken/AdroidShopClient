package stx.shopclient.itemactivity;

import java.util.Collection;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.AnalogGroup;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.overviewactivity.OverviewActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.webservice.WebClient;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.entity.Token;

public class ItemActivity extends BaseActivity
{
	private CatalogItem _Item;
	private long _itemId;

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";
	public static final String ITEM_BUY_EXTRA_KEY = "CanBuyItem";

	private ItemButtonBarFragment buttonBar;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.item_activity, parent,
				false);

		Intent intent = getIntent();

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(settings.getBackground()));

		_itemId = intent.getLongExtra(ITEM_ID_EXTRA_KEY, 0);

		_Item = null;//Repository.get(this).getItemsManager().getItem(_itemId);

		if (_Item != null)
			loadUI(view);
		else
		{
			LoadTask task = new LoadTask();
			task.mainView = view;
			task.execute();
		}

		return view;
	}

	void loadUI(View view)
	{
		ItemImageFragment imageFragment = (ItemImageFragment) getFragmentManager()
				.findFragmentById(R.id.frgImages);
		buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		TextView txtProperty = (TextView) view.findViewById(R.id.txtProperty);

		imageFragment.setImages(_Item.getId());

		buttonBar.setPrice(_Item.getPrice());
		buttonBar.setRating(_Item.getRating());
		buttonBar.setOverviewCount(_Item.getOverviewsCount());
		buttonBar.setCanBuy(!Repository.get(this).getOrderManager()
				.existsItem(_Item.getId()));

		Collection<AnalogGroup> groups = Repository.get(this).getItemsManager()
				.getAnalogs(_itemId);
		for (AnalogGroup el : groups)
		{
			buttonBar.addAnalogs(el.getName(), el.getIds());
		}

		setProperty(txtProperty);
		getActionBar().setTitle(_Item.getName());
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		if (_Item != null)
			buttonBar.setCanBuy(!Repository.get(this).getOrderManager()
					.existsItem(_Item.getId()));
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
			sendIntent.putExtra(
					Intent.EXTRA_STREAM,
					Uri.parse("file://"
							+ Repository.get(this).getImagesManager()
									.getImagePath(_Item.getIco())));
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

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		public View mainView;
		ProgressDialog dialog;
		Throwable exception;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(ItemActivity.this, "Загрузка",
					"Получение элемента каталога");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = new WebClient(ItemActivity.this);
				Collection<CatalogItem> items = client.getNodeItem(
						Token.getCurrent(), _itemId);
				_Item = items.iterator().next();
			}
			catch (Throwable ex)
			{
				exception = ex;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			dialog.dismiss();
			
			if (exception != null)
			{
				Toast.makeText(ItemActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
				finish();
			}
			else
				loadUI(mainView);			
		}

	}

}
