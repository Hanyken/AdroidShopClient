package stx.shopclient.orderactivity;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.KeyValue;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.ui.common.properties.PropertiesList;
import stx.shopclient.webservice.WebClient;

public class OrderActivity extends BaseActivity implements OnClickListener
{
	private long ItemId;
	private TextView lblItemName;
	private ImageView imgIco;
	private PropertiesList plProperies;
	private LinearLayout llCountable;
	private Button btnOrder;
	List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.order_activity,
				parent, false);

		Intent intent = getIntent();
		ItemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);

		CatalogSettings settings = Repository.get(this).getCatalogManager()
				.getSettings();
		CatalogItem item = Repository.get(this).getItemsManager()
				.getItem(ItemId);

		lblItemName = (TextView) view.findViewById(R.id.lblItemName);
		plProperies = (PropertiesList) view.findViewById(R.id.plProperties);
		llCountable = (LinearLayout) view.findViewById(R.id.llCountable);
		btnOrder = (Button) view.findViewById(R.id.btnOrder);
		imgIco = (ImageView) view.findViewById(R.id.imgIco);

		llCountable.setVisibility(View.GONE);

		lblItemName.setText(item.getName());

		btnOrder.setOnClickListener(this);
		btnOrder.setBackground(getBueButtonDrawable(settings));
		btnOrder.setTextColor(settings.getForegroundColor());

		plProperies.setAllowClear(false);

		imgIco.setImageBitmap(Repository.get(this).getImagesManager()
				.getImage(item.getIco()));

		properties.addAll(item.getOrderProperties());
		plProperies.setProperties(properties);

		return view;
	}

	@Override
	public void onClick(View v)
	{
		for (PropertyDescriptor el : properties)
		{
			if (!el.isValueDefined())
			{
				Toast.makeText(this, "Нужно заполнить параметры заказа",
						Toast.LENGTH_LONG).show();
				return;
			}
		}

		new AddOrderTask().execute();
	}

	private Drawable getBueButtonDrawable(CatalogSettings settings)
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[]
		{ android.R.attr.state_pressed }, press);
		drawable.addState(new int[]
		{ -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
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
				ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();
				double count = 0;
				for (PropertyDescriptor el : properties)
				{
					OrderProperty item = new OrderProperty();
					item.setName(el.getName());
					item.setValue(el.getStringValue());
					items.add(item);

					if (item.getName()
							.equals(OrderProperty.COUNT_PROPERTY_NAME))
						count = Double.parseDouble(item.getValue());
				}

				WebClient client = createWebClient();
				client.addOrder(Token.getCurrent(), ItemId, count, items);
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
				Toast.makeText(OrderActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				Toast.makeText(OrderActivity.this, "Товар добавлен в карзину",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}
}
