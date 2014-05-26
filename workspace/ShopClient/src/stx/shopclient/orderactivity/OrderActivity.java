package stx.shopclient.orderactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.properties.PropertiesList;

public class OrderActivity extends BaseActivity implements OnClickListener
{
	private long ItemId;
	private TextView lblItemName;
	private PropertiesList plProperies;
	private LinearLayout llCountable;
	private Button btnOrder;
	List<PropertyDescriptor> _properties = new ArrayList<PropertyDescriptor>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		generateData();

		View view = getLayoutInflater().inflate(R.layout.order_activity,
				parent, false);

		Intent intent = getIntent();

		lblItemName = (TextView) view.findViewById(R.id.lblItemName);
		plProperies = (PropertiesList) view.findViewById(R.id.plProperties);
		llCountable = (LinearLayout) view.findViewById(R.id.llCountable);
		btnOrder = (Button)view.findViewById(R.id.btnOrder);
		
		llCountable.setVisibility(View.GONE);

		ItemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		lblItemName.setText(intent.getStringExtra("ItemTitle"));

		btnOrder.setOnClickListener(this);
		
		plProperies.setAllowClear(false);
		plProperies.setProperties(_properties);

		return view;
	}

	private void generateData()
	{
		_properties.addAll(Repository.getIntent().getPropertiesManager().getOrderProperties(ItemId));
	}

	@Override
	public void onClick(View v)
	{
		ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();
		for(PropertyDescriptor el : _properties)
		{
			OrderProperty item = new OrderProperty();
			item.setName(el.getName());
			item.setValue(el.getStringValue());
			items.add(item);
		}
		
		Repository.getIntent().getOrderManager().addOrder(ItemId, items);
		Toast.makeText(this, "Товар добавлен в карзину",Toast.LENGTH_SHORT).show();
		finish();
	}
}
