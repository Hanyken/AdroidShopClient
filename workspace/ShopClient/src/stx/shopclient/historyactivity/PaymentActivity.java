package stx.shopclient.historyactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.Payment;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.parsers.BaseParser;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.webservice.WebClient;

public class PaymentActivity extends BaseActivity
{
	public static final String PAYMENT_ID_NAME = "PaymentId";
	public static final String PAYMENT_NUMBER_NAME = "PaymentNumber";
	public static final String PAYMENT_CREATE_DATE_NAME = "PaymentCreateDate";
	public static final String PAYMENT_SUM_NAME = "PaymentSum";
	public static final String PAYMENT_STATE_NAME = "PaymentState";
	public static final String PAYMENT_PAY_DATE_NAME = "PaymentPayDate";
	public static final String PAYMENT_ORDER_COUNT_NAME = "PaymentOrderCount";

	private Payment _Item;
	private List<Order> _orderItems = new ArrayList<Order>();
	private OrderListAdapter _adapter;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Информация о заказе");

		View mainView = getLayoutInflater().inflate(R.layout.payment_activity,
				parent, false);

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(settings.getBackground()));

		Intent intent = getIntent();
		try
		{
			_Item = new Payment();
			_Item.setId(intent.getLongExtra(PAYMENT_ID_NAME, 0));
			_Item.setNumber(intent.getLongExtra(PAYMENT_NUMBER_NAME, 0));
			_Item.setSum(intent.getDoubleExtra(PAYMENT_SUM_NAME, 0));
			_Item.setCreateDate(BaseParser.dateParser.parse(intent
					.getStringExtra(PAYMENT_CREATE_DATE_NAME)));
			_Item.setState(intent.getIntExtra(PAYMENT_STATE_NAME, 0));
			if (intent.hasExtra(PAYMENT_PAY_DATE_NAME))
				_Item.setPayDate(BaseParser.dateParser.parse(intent
						.getStringExtra(PAYMENT_PAY_DATE_NAME)));
			_Item.setOrderCount(intent.getIntExtra(PAYMENT_ORDER_COUNT_NAME, 0));
		}
		catch (Exception ex)
		{
		}
		TextView lblNumber = (TextView) mainView.findViewById(R.id.lblNumber);
		PullToRefreshListView lstOrders = (PullToRefreshListView) mainView
				.findViewById(R.id.lstOrders);
		lstOrders.setMode(Mode.DISABLED);

		_adapter = new OrderListAdapter();

		lblNumber.setText("Номер заказа: " + Long.toString(_Item.getNumber())
				+ "\nКол-во элементов:"
				+ Integer.toString(_Item.getOrderCount()) + "\nДата заказа: "
				+ BaseParser.dateParser.format(_Item.getCreateDate())
				+ "\nСумма заказа: " + Double.toString(_Item.getSum())
				+ "\nСтатус заказа: " + Integer.toString(_Item.getState()));
		lstOrders.setAdapter(_adapter);
		lstOrders.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3)
			{
				Order item = (Order) view.getTag();
				Intent intent = new Intent(PaymentActivity.this,
						ItemActivity.class);
				intent.putExtra("ItemID", item.getItemId());

				startActivity(intent);
			}

		});

		new LoadTask().execute();

		return mainView;
	}

	class OrderListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _orderItems.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			return 0;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup container)
		{

			Order order = _orderItems.get(index);

			final View view = getLayoutInflater().inflate(
					R.layout.payment_activity_item, container, false);

			view.setTag(order);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(order.getItem().getName());

			TextView descrTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			descrTextView
					.setText(CartActivity.getOrderDescription(order
							.getProperties(), order.getItem()
							.getOrderProperties()));

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

			if (order.getItem().getIco() != null)
				ImageDownloadTask.startNew(imgView, PaymentActivity.this, order
						.getItem().getIco());

			return view;
		}

	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(PaymentActivity.this, "Загрузка",
					"Получение элементов истории заказов");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				Collection<Order> payments = client.getPaymentOrders(
						Token.getCurrent(), _Item.getId());
				_orderItems.clear();
				_orderItems.addAll(payments);
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
				Toast.makeText(PaymentActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
				_adapter.notifyDataSetChanged();
		}
	}
}
