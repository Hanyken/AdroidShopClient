package stx.shopclient.order_properties_activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.repository.OrdersManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.properties.PropertiesList;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;

public class OrderPropertiesActivity extends BaseActivity
{

	public static final String TITLE_EXTRA_KEY = "title";
	public static final String ORDER_ID_EXTRA_KEY = "orderId";

	long _orderId;
	Order _order;
	PropertiesList _list;
	List<PropertyDescriptor> _properties = new ArrayList<PropertyDescriptor>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		_orderId = getIntent().getLongExtra(ORDER_ID_EXTRA_KEY, 0);
		_order = Repository.get(null).getOrderManager().getOrderById(_orderId);

		Collection<PropertyDescriptor> properties = OrdersManager
				.getOrderPropertiesAsDescriptiors(_order.getItem()
						.getOrderProperties(), _order.getProperties());
		_properties.addAll(properties);

		getActionBar().setTitle(getIntent().getStringExtra(TITLE_EXTRA_KEY));

		View view = getLayoutInflater().inflate(
				R.layout.order_properties_activity, parent, false);

		_list = (PropertiesList) view.findViewById(R.id.propertiesList);

		_list.setAllowClear(false);
		_list.setProperties(_properties);

		Button applyButton = (Button) view.findViewById(R.id.btnEditorder);
		CatalogSettings settings = Repository.get(null).getCatalogManager()
				.getSettings();
		applyButton.setTextColor(settings.getForegroundColor());
		applyButton.setBackground(BaseActivity.getButtonDrawable(settings));
		applyButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				applyButtonClick();
			}
		});

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

	void applyButtonClick()
	{
		new EditOrderTask().execute();
	}

	class EditOrderTask extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog dialog;
		Throwable exception;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(OrderPropertiesActivity.this,
					"Загрузка", "Изменение параметров заказа");
			ProgressDlgUtil.setCancellable(dialog, OrderPropertiesActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				Collection<OrderProperty> orderProps = OrdersManager
						.getOrderPropertiesFromDescriptors(_properties);

				WebClient client = createWebClient();
				client.editOrder(Token.getCurrent(), _orderId, orderProps);
				
				_order.setProperties(orderProps);
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
				Toast.makeText(OrderPropertiesActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Toast.makeText(OrderPropertiesActivity.this, "Заказ изменен",
						Toast.LENGTH_LONG).show();
				setResult(1);
				finish();
			}
		}
	}
}
