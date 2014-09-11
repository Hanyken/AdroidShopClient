package stx.shopclient.cartactivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.historyactivity.PaymentDescriptionFragment;
import stx.shopclient.historyactivity.PaymentListActivity;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.order_properties_activity.OrderPropertiesActivity;
import stx.shopclient.repository.OrdersManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends BaseActivity implements OnItemClickListener
{
	public static final String CARD_ID_NAME = "CardId";
	public static final int EDIT_ORDER_REQUEST = 1;
	private static final int MENU_PAYMENT = 1;
	ListView _list;
	CartListAdapter _adapter;
	Long _CatalogId;
	Button _buttonOrder;
	TextView _summaryTextView;
	RelativeLayout _summaryLayout;

	List<Order> _cartItems = new ArrayList<Order>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Корзина");

		View view = getLayoutInflater().inflate(R.layout.cart_activity, parent,
				false);

		_buttonOrder = (Button) view.findViewById(R.id.buttonOrder);
		_summaryTextView = (TextView) view.findViewById(R.id.summaryTextView);
		_summaryLayout = (RelativeLayout) view.findViewById(R.id.summaryLayout);

		_buttonOrder.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showPaymentDialog();
			}
		});
		setButtonStyle(_buttonOrder);		

		_CatalogId = getIntent().getLongExtra(CARD_ID_NAME, 0);
		_list = (ListView) view.findViewById(R.id.listView);
		_list.setOnItemClickListener(this);

		_adapter = new CartListAdapter();

		_list.setAdapter(_adapter);

		registerForContextMenu(_list);

		setActivityBackgroundFromSettings();

		new LoadTask().execute();

		return view;
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		if (_adapter != null)
			_adapter.notifyDataSetChanged();
	}

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo)
	{

		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.cart_activity_item_menu, menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		menu.clear();
		MenuItem paymentItem = menu.add(0, MENU_PAYMENT, 0, "Оформить заказ");
		paymentItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return super.onPrepareOptionsMenu(menu);
	}

	void showPaymentDialog()
	{
		if (_cartItems.size() > 0)
		{
			DialogFragment wd = PaymentDescriptionFragment.get(!Token
					.getCurrent().getAuthorize());
			wd.show(getFragmentManager(), "Comment");
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case MENU_PAYMENT:
			showPaymentDialog();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void SetComment(String comment)
	{
		PaymentTask task = new PaymentTask();
		task.comment = comment;
		task.execute();
	}

	class CartListAdapter extends BaseAdapter
	{
		@Override
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();

			double totalPrice = 0;

			for (Order order : _cartItems)
			{
				for (OrderProperty prop : order.getProperties())
				{
					if (prop.getName()
							.equals(OrderProperty.COUNT_PROPERTY_NAME))
					{
						int count = (int) Double.parseDouble(prop.getValue());

						totalPrice += order.getItem().getPrice() * count;

						break;
					}
				}

			}

			if (_cartItems.size() > 0)
			{
				_summaryLayout.setVisibility(View.VISIBLE);
				DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
				_summaryTextView.setText("Итого: " + format.format(totalPrice)
						+ " руб.");
			}
			else
			{
				_summaryLayout.setVisibility(View.GONE);
			}
		}

		@Override
		public int getCount()
		{
			return _cartItems.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup container)
		{

			final Order order = _cartItems.get(index);

			final View view = getLayoutInflater().inflate(
					R.layout.cart_activity_item, container, false);

			view.setTag(order);

			ImageButton plusButton = (ImageButton) view
					.findViewById(R.id.plusButton);
			ImageButton minusButton = (ImageButton) view
					.findViewById(R.id.minusButton);
			LinearLayout llCount = (LinearLayout) view
					.findViewById(R.id.llCount);
			TextView countTextView = (TextView) view
					.findViewById(R.id.countTextView);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(order.getItem().getName());

			TextView descrTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);

			DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
			String description = "Цена: "
					+ format.format(order.getItem().getPrice()) + " руб.";
			// String propsDescription =
			// getOrderDescription(order.getProperties(),
			// order.getItem().getOrderProperties());
			// if(StringUtils.isNoneBlank(propsDescription))
			// description += ", " + propsDescription;

			descrTextView.setText(description);

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

			if (order.getItem().getIco() != null)
				ImageDownloadTask.startNew(imgView, CartActivity.this, order
						.getItem().getIco());

			llCount.setVisibility(View.GONE);
			for (OrderProperty prop : order.getProperties())
			{
				if (prop.getName().equals(OrderProperty.COUNT_PROPERTY_NAME))
				{
					llCount.setVisibility(View.VISIBLE);
					int count = (int) Double.parseDouble(prop.getValue());
					countTextView.setText(Integer.toString(count));
					break;
				}
			}

			Button menuButton = (Button) view.findViewById(R.id.menuButton);

			menuButton.setTag(order);
			menuButton.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					openContextMenu(view);
				}
			});

			plusButton.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					incrementCountClick(order);
				}
			});
			minusButton.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					decrementCountClick(order);
				}
			});

			return view;
		}

	}

	void incrementCountClick(Order order)
	{
		if (addCount(order, 1))
		{
			EditOrderTask task = new EditOrderTask();
			task.order = order;
			task.execute();
		}
	}

	void decrementCountClick(Order order)
	{
		if (addCount(order, -1))
		{
			EditOrderTask task = new EditOrderTask();
			task.order = order;
			task.execute();
		}
	}

	boolean addCount(Order order, int count)
	{
		for (OrderProperty prop : order.getProperties())
		{
			if (prop.getName().equals(OrderProperty.COUNT_PROPERTY_NAME))
			{
				int count_ = (int) Double.parseDouble(prop.getValue());

				if ((count_ + count) <= 0)
					return false;

				count_ += count;
				prop.setValue(Integer.toString(count_));
				return true;
			}
		}

		return false;
	}

	public static String getOrderDescription(
			Collection<OrderProperty> orderProperties,
			Collection<PropertyDescriptor> itemOrderProperties)
	{
		Collection<PropertyDescriptor> properties = OrdersManager
				.getOrderPropertiesAsDescriptiors(itemOrderProperties,
						orderProperties);

		String description = "";

		for (PropertyDescriptor prop : properties)
		{
			if (!description.equals(""))
				description += ", ";

			if (prop instanceof EnumPropertyDescriptor)
			{
				EnumPropertyDescriptor enumProp = (EnumPropertyDescriptor) prop;
				String value = "";

				for (EnumValue el : enumProp.getCurrentValues())
				{
					if (!value.equals(""))
						value += ",";
					value += el.getName();
				}

				String itemDescr = prop.getTitle() + ": " + value;
				description += itemDescr;
			}
			else
			{
				String itemDescr = prop.getTitle() + ": "
						+ prop.getStringValue();
				description += itemDescr;
			}
		}

		return description;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		// Order order = _cartItems.get(info.position + 1);
		Order order = (Order) info.targetView.getTag();

		if (item.getItemId() == R.id.edit)
		{
			Intent intent = new Intent(this, OrderPropertiesActivity.class);
			intent.putExtra(OrderPropertiesActivity.TITLE_EXTRA_KEY, order
					.getItem().getName());
			intent.putExtra(OrderPropertiesActivity.ORDER_ID_EXTRA_KEY,
					order.getId());
			startActivityForResult(intent, EDIT_ORDER_REQUEST);
		}
		else if (item.getItemId() == R.id.delete)
		{
			RemoveTask task = new RemoveTask();
			task.order = order;
			task.execute();
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3)
	{
		Order order = (Order) view.getTag();

		Intent intent = new Intent(this, ItemActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, order.getItem().getId());
		intent.putExtra(ItemActivity.ITEM_BUY_EXTRA_KEY, false);
		intent.putExtra("ItemTitle", order.getItem().getName());
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == EDIT_ORDER_REQUEST && resultCode == 1)
		{
			new LoadTask().execute();
		}
	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(CartActivity.this, "Загрузка",
					"Получение элементов корзины");
			ProgressDlgUtil.setCancellable(dialog, CartActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				Collection<Order> orders = client.getOrders(Token.getCurrent(),
						_CatalogId);
				_cartItems.clear();
				_cartItems.addAll(orders);
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
				Toast.makeText(CartActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
				_adapter.notifyDataSetChanged();
		}
	}

	class RemoveTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;
		public Order order;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(CartActivity.this, "Загрузка",
					"Удаление элемента корзины");
			ProgressDlgUtil.setCancellable(dialog, CartActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				client.deleteOrder(Token.getCurrent(), order.getId());
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
				Toast.makeText(CartActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Toast.makeText(
						CartActivity.this,
						String.format("Удалено (%s)", order.getItem().getName()),
						Toast.LENGTH_LONG).show();
				_cartItems.remove(order);
				_adapter.notifyDataSetChanged();
				CartActivity.this.invalidateOptionsMenu(); // Обновляю кнопку
															// "Офрмить"
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
			dialog = ProgressDialog.show(CartActivity.this, "Загрузка",
					"Оформирование заказа");
			ProgressDlgUtil.setCancellable(dialog, CartActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				long paymentNumber = client.addPayment(Token.getCurrent(),
						_CatalogId, comment);
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
				Toast.makeText(CartActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Repository.get(null).getOrderManager()
						.setOrderCatalogs(new ArrayList<Catalog>());
				Toast.makeText(CartActivity.this, "Заказ оформлен",
						Toast.LENGTH_LONG).show();

				finish();

				if (crosssaleItems != null && crosssaleItems.size() > 0)
				{
					CrosssaleActivity.crosssaleItems = crosssaleItems;
					Intent intent = new Intent(CartActivity.this,
							CrosssaleActivity.class);
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(CartActivity.this,
							PaymentListActivity.class);
					startActivity(intent);
				}
			}
		}
	}

	class EditOrderTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		public Order order;

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				client.editOrder(Token.getCurrent(), order.getId(),
						order.getProperties());
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

			if (exception != null)
				Toast.makeText(CartActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				_adapter.notifyDataSetChanged();
			}
		}
	}
}
