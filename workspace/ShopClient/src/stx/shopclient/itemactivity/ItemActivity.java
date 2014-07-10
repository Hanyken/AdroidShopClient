package stx.shopclient.itemactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogItemGroup;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.mainmenu.MainMenuItem;
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
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.entity.Token;

public class ItemActivity extends BaseActivity
{
	private CatalogItem _Item;
	private long _itemId;
	private View _mainView;

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";
	public static final String ITEM_BUY_EXTRA_KEY = "CanBuyItem";

	private ItemButtonBarFragment buttonBar;

	private Map<CatalogItemGroup, Collection<CatalogItem>> _groupItems = new HashMap<CatalogItemGroup, Collection<CatalogItem>>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		_mainView = getLayoutInflater().inflate(R.layout.item_activity, parent,
				false);

		Intent intent = getIntent();

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(settings.getBackground()));
		//_mainView.setBackgroundColor(settings.getBackground());

		_itemId = intent.getLongExtra(ITEM_ID_EXTRA_KEY, 0);

		_Item = null;// Repository.get(this).getItemsManager().getItem(_itemId);

		if (_Item != null)
			loadUI(_mainView);
		else
		{
			LoadTask task = new LoadTask();
			task.mainView = _mainView;
			task.execute();
		}

		return _mainView;
	}

	@Override
	protected long getSearchActivityNodeId()
	{
		return _Item.getNodeId();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == OverviewActivity.OVERVIEW_REQUEST && resultCode == 1)
		{
			LoadTask task = new LoadTask();
			task.mainView = _mainView;
			task.execute();
		}
	}

	void loadUI(View view)
	{
		ItemImageFragment imageFragment = (ItemImageFragment) getFragmentManager()
				.findFragmentById(R.id.frgImages);
		buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		TextView txtProperty = (TextView) view.findViewById(R.id.txtProperty);
		WebView webProperty = (WebView)view.findViewById(R.id.webProperty);

		imageFragment.setImages(_Item.getId());

		buttonBar.setPrice(_Item.getPrice());
		buttonBar.setRating(_Item.getRating());
		buttonBar.setOverviewCount(_Item.getOverviewsCount());
		buttonBar.setCanBuy(!_Item.getUnsaleable());

		buttonBar.clearAnalogs();

		for (CatalogItemGroup group : _groupItems.keySet())
		{
			buttonBar.addAnalogs(group, _groupItems.get(group));
		}
		
		if (_Item.webVewShow())
		{
			txtProperty.setVisibility(View.GONE);
			webProperty.loadData(_Item.webVewText(), "text/html; charset=utf-8", "UTF-8");
		}
		else
		{
			webProperty.setVisibility(View.GONE);
			setProperty(txtProperty);
		}
		getActionBar().setTitle(_Item.getName());
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

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	public void onBarButtonClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btnOverview:
			Intent intent = new Intent(this, OverviewActivity.class);
			intent.putExtra(ITEM_ID_EXTRA_KEY, _Item.getId());
			startActivityForResult(intent, OverviewActivity.OVERVIEW_REQUEST);
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
		case R.id.btnFavorits:
			new FavoritsTask().execute();
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

				client.addLast(Token.getCurrent(), _itemId);
				
				_groupItems.clear();

				if (_Item != null)
				{
					for (CatalogItemGroup group : _Item.getGroups())
					{
						Collection<CatalogItem> groupItems = client
								.getGroupItems(Token.getCurrent(), _itemId,
										group.getId());
						if (groupItems != null && groupItems.size() > 0)
						{
							_groupItems.put(group, groupItems);
						}
					}
				}
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

	
	class FavoritsTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;

		public String comment;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(ItemActivity.this, "Обработка",
					"Добавить в избранное");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				client.addFavorite(Token.getCurrent(), _itemId);
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
				Toast.makeText(ItemActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Repository.get(null).getOrderManager()
						.setOrderCatalogs(new ArrayList<Catalog>());
				Toast.makeText(ItemActivity.this, "Элемент добавлен в список",
						Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
