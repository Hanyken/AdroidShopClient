package stx.shopclient.orderactivity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.ui.common.properties.PropertiesList;

public class OrderActivity extends BaseActivity
{
	private Double ItemId;
	private TextView lblItemName;
	private PropertiesList plProperies;
	private LinearLayout llCountable;
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

		llCountable.setVisibility(View.GONE);

		ItemId = intent.getDoubleExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		lblItemName.setText(intent.getStringExtra("ItemTitle"));

		plProperies.setAllowClear(false);
		plProperies.setProperties(_properties);

		return view;
	}

	private void generateData()
	{
		NumberPropertyDescriptor prop = new NumberPropertyDescriptor();
		prop.setName("Count");
		prop.setTitle("Количество");
		prop.setMinValue(1);
		prop.setMaxValue(99999);
		prop.setFloat(false);
		prop.setRange(false);
		prop.setCurrentMinValue(1);
		prop.setCurrentValueDefined(true);
		_properties.add(prop);

		DatePropertyDescriptor prop1 = new DatePropertyDescriptor();
		prop1.setName("asd");
		prop1.setTitle("Дата");
		prop1.setMinValue(new GregorianCalendar(1997, 1, 1));
		prop1.setMaxValue(new GregorianCalendar(2020, 1, 1));
		prop1.setCurrentMinValue(new GregorianCalendar(2015, 1, 1));
		prop1.setCurrentValueDefined(true);
		prop1.setRange(false);
		_properties.add(prop1);
	}
}
