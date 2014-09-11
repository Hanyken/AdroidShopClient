package stx.shopclient.historyactivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Payment;
import stx.shopclient.entity.Token;
import stx.shopclient.parsers.BaseParser;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;

public class PaymentListActivity extends BaseActivity implements
		OnItemClickListener
{
	PullToRefreshListView lstMain;
	List<Payment> _paymentItems = new ArrayList<Payment>();
	PaymentListAdapter _adapter;
	Long _CatalogId;
	SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View mainView = getLayoutInflater().inflate(
				R.layout.payment_list_activity, parent, false);

		// CatalogSettings settings = Repository.get(this).getCatalogManager()
		// .getSettings();

		_CatalogId = Repository.CatalogId;
		_adapter = new PaymentListAdapter();

		lstMain = (PullToRefreshListView) mainView.findViewById(R.id.lstMain);
		lstMain.setMode(Mode.DISABLED);
		lstMain.setOnItemClickListener(this);
		lstMain.setAdapter(_adapter);

		new LoadTask().execute();

		return mainView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3)
	{
		Payment item = (Payment) view.getTag();
		Intent intent = new Intent(this, PaymentActivity.class);
		intent.putExtra(PaymentActivity.PAYMENT_ID_NAME, item.getId());
		intent.putExtra(PaymentActivity.PAYMENT_NUMBER_NAME, item.getNumber());
		intent.putExtra(PaymentActivity.PAYMENT_CREATE_DATE_NAME,
				BaseParser.dateParser.format(item.getCreateDate()));
		intent.putExtra(PaymentActivity.PAYMENT_SUM_NAME, item.getSum());
		intent.putExtra(PaymentActivity.PAYMENT_STATE_NAME, item.getState());
		if (item.getPayDate() != null)
			intent.putExtra(PaymentActivity.PAYMENT_PAY_DATE_NAME,
					BaseParser.dateParser.format(item.getPayDate()));
		intent.putExtra(PaymentActivity.PAYMENT_ORDER_COUNT_NAME,
				item.getOrderCount());
		startActivity(intent);
	}

	class PaymentListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _paymentItems.size();
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

			Payment item = _paymentItems.get(index);

			final View view = PaymentListActivity.this.getLayoutInflater()
					.inflate(R.layout.payment_list_activity_item, container,
							false);

			TextView lblNumber = (TextView) view.findViewById(R.id.lblNumber);
			TextView countTextView = (TextView) view
					.findViewById(R.id.countTextView);
			TextView priceTextView = (TextView) view
					.findViewById(R.id.priceTextView);
			TextView stateTextView = (TextView) view
					.findViewById(R.id.stateTextView);
			TextView dateTextView = (TextView) view
					.findViewById(R.id.dateTextView);

			lblNumber.setText("ЗАКАЗ № " + Long.toString(item.getNumber()));
			countTextView.setText(Integer.toString(item.getOrderCount()));

			DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
			String price = format.format(item.getSum()) + " руб.";
			priceTextView.setText(price);

			String state = "";
			if (item.getState() == Payment.STATE_ACCEPTED)
				state = "Заказ принят";
			stateTextView.setText(state);
			
			dateTextView.setText(_dateFormat.format(item.getCreateDate()));

			view.setTag(item);

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
			dialog = ProgressDialog.show(PaymentListActivity.this, "Загрузка",
					"Получение элементов истории заказов");
			ProgressDlgUtil.setCancellable(dialog, PaymentListActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = PaymentListActivity.this.createWebClient();
				Collection<Payment> payments = client.getPayments(
						Token.getCurrent(), _CatalogId);
				_paymentItems.clear();
				_paymentItems.addAll(payments);
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
				Toast.makeText(PaymentListActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
				_adapter.notifyDataSetChanged();
		}
	}
}
