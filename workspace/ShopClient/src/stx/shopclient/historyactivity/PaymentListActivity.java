package stx.shopclient.historyactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Payment;
import stx.shopclient.entity.Token;
import stx.shopclient.parsers.BaseParser;
import stx.shopclient.repository.Repository;
import stx.shopclient.webservice.WebClient;

public class PaymentListActivity extends BaseActivity implements OnItemClickListener
{
	ListView lstMain;
	List<Payment> _paymentItems = new ArrayList<Payment>();
	PaymentListAdapter _adapter;
	
	@Override
	protected View createMainView(ViewGroup parent)
	{
		View mainView = getLayoutInflater().inflate(R.layout.payment_list_activity, parent, false);
		
		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();

		getActionBar().setBackgroundDrawable(new ColorDrawable(settings.getBackground()));

		_adapter = new PaymentListAdapter();
		
		lstMain = (ListView)mainView.findViewById(R.id.lstMain);
		lstMain.setOnItemClickListener(this);
		lstMain.setAdapter(_adapter);

		new LoadTask().execute();
		
		return mainView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		Payment item = _paymentItems.get(arg2);
		Intent intent = new Intent(this, PaymentActivity.class);
		intent.putExtra(PaymentActivity.PAYMENT_ID_NAME, item.getId());
		intent.putExtra(PaymentActivity.PAYMENT_NUMBER_NAME, item.getNumber());
		intent.putExtra(PaymentActivity.PAYMENT_CREATE_DATE_NAME, BaseParser.dateParser.format(item.getCreateDate()));
		intent.putExtra(PaymentActivity.PAYMENT_SUM_NAME, item.getSum());
		intent.putExtra(PaymentActivity.PAYMENT_STATE_NAME, item.getState());
		if (item.getPayDate() != null)
			intent.putExtra(PaymentActivity.PAYMENT_PAY_DATE_NAME, BaseParser.dateParser.format(item.getPayDate()));
		intent.putExtra(PaymentActivity.PAYMENT_ORDER_COUNT_NAME, item.getOrderCount());
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

			final View view = getLayoutInflater().inflate(
					R.layout.payment_list_activity_item, container, false);

			TextView lblNumber = (TextView)view.findViewById(R.id.lblNumber);
			
			lblNumber.setText("Номер заказа: "+Long.toString(item.getNumber()) + "\n Кол-во элементов:" + Integer.toString(item.getOrderCount())+ "\n Дата заказа: "+ BaseParser.dateParser.format(item.getCreateDate()));
			
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
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				Collection<Payment> payments = client.getPayments(Token.getCurrent(),
						Repository.CatalogId);
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
