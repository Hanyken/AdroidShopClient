package stx.shopclient.orderactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.cartactivity.CrosssaleActivity;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.KeyValue;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.historyactivity.PaymentDescriptionFragment;
import stx.shopclient.historyactivity.PaymentListActivity;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.repository.OrdersManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.ui.common.properties.PropertiesList;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.webservice.WebClient;

public class OrderActivity extends BaseActivity implements OnClickListener
{
	private long ItemId;
	private TextView lblItemName;
	private ImageView imgIco;
	private PropertiesList plProperies;
	private LinearLayout llCountable;
	private Button btnOrder;
	private Button btnAddPayment;
	List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();
	CatalogItem _item;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.order_activity,
				parent, false);

		Intent intent = getIntent();
		ItemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();
		_item = Repository.get(this).getItemsManager().getItem(ItemId);

		lblItemName = (TextView) view.findViewById(R.id.lblItemName);
		plProperies = (PropertiesList) view.findViewById(R.id.plProperties);
		llCountable = (LinearLayout) view.findViewById(R.id.llCountable);
		btnOrder = (Button) view.findViewById(R.id.btnOrder);
		btnAddPayment = (Button) view.findViewById(R.id.btnAddPayment);
		imgIco = (ImageView) view.findViewById(R.id.imgIco);

		llCountable.setVisibility(View.GONE);

		lblItemName.setText(_item.getName());

		btnOrder.setOnClickListener(this);
		btnOrder.setBackground(getButtonDrawable(settings));
		btnOrder.setTextColor(settings.getForegroundColor());

		btnAddPayment.setOnClickListener(this);
		btnAddPayment.setBackground(getButtonDrawable(settings));
		btnAddPayment.setTextColor(settings.getForegroundColor());

		plProperies.setAllowClear(false);

		if (_item.getIco() != null)
			ImageDownloadTask.startNew(imgIco, this, _item.getIco());

		properties.addAll(_item.getOrderProperties());

		for (PropertyDescriptor prop : properties)
		{
			if ((prop instanceof NumberPropertyDescriptor)
					&& prop.getName().toLowerCase().equals("count"))
			{
				NumberPropertyDescriptor numProp = (NumberPropertyDescriptor) prop;
				numProp.setCurrentValueDefined(true);
				numProp.setCurrentMinValue(1);
			}
		}

		plProperies.setProperties(properties);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		GridLayout.LayoutParams params = (GridLayout.LayoutParams) btnOrder
				.getLayoutParams();
		params.width = (int) (displayMetrics.widthPixels / 2 - 10);
		btnOrder.setLayoutParams(params);

		params = (GridLayout.LayoutParams) btnAddPayment.getLayoutParams();
		params.width = (int) (displayMetrics.widthPixels / 2 - 10);
		btnAddPayment.setLayoutParams(params);

		return view;
	}

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	boolean validateProperties()
	{
		for (PropertyDescriptor el : properties)
		{
			if (!el.isValueDefined())
			{
				Toast.makeText(this, "Нужно заполнить параметры заказа",
						Toast.LENGTH_LONG).show();
				return false;
			}
		}

		return true;
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.btnOrder)
		{
			if (validateProperties())
				new AddOrderTask().execute();
		}
		else if (v.getId() == R.id.btnAddPayment)
		{
			if (validateProperties())
			{
				DialogFragment wd = PaymentDescriptionFragment.get(!Token
						.getCurrent().getAuthorize());
				wd.show(getFragmentManager(), "Comment");
			}
		}
	}
	
	public void SetComment(String comment)
	{
		PaymentTask task = new PaymentTask();
		task.comment = comment;
		task.execute();
	}

	class AddOrderTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog dialog;
		Throwable exception;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(OrderActivity.this, "Загрузка",
					"Добавление товара в корзину");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				Collection<OrderProperty> orderProps = OrdersManager
						.getOrderPropertiesFromDescriptors(properties);

				WebClient client = createWebClient();
				client.addOrder(Token.getCurrent(), ItemId, orderProps);
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
			if (isDestroyed())
				return;

			dialog.dismiss();

			if (exception != null)
				Toast.makeText(OrderActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Toast.makeText(OrderActivity.this, "Товар добавлен в корзину",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
	
	class PaymentTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;

		public String comment;

		Collection<CatalogItem> crosssaleItems;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(OrderActivity.this, "Загрузка",
					"Оформирование заказа");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				
				Collection<OrderProperty> orderProps = OrdersManager
						.getOrderPropertiesFromDescriptors(properties);
				
				client.addOrder(Token.getCurrent(), ItemId, orderProps);
				
				long paymentNumber = client.addPayment(Token.getCurrent(),
						Repository.get(null).CatalogId, comment);
				crosssaleItems = client.getCrosssale(Token.getCurrent(),
						paymentNumber);
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
			if (isDestroyed())
				return;

			dialog.dismiss();

			if (exception != null)
				Toast.makeText(OrderActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Repository.get(null).getOrderManager()
						.setOrderCatalogs(new ArrayList<Catalog>());
				Toast.makeText(OrderActivity.this, "Заказ оформлен",
						Toast.LENGTH_LONG).show();

				finish();

				if (crosssaleItems != null && crosssaleItems.size() > 0)
				{
					CrosssaleActivity.crosssaleItems = crosssaleItems;
					Intent intent = new Intent(OrderActivity.this,
							CrosssaleActivity.class);
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(OrderActivity.this,
							PaymentListActivity.class);
					startActivity(intent);
				}
			}
		}
	}
}
