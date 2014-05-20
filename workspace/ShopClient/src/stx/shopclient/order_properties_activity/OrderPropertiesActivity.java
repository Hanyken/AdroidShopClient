package stx.shopclient.order_properties_activity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.ui.common.properties.PropertiesList;

public class OrderPropertiesActivity extends BaseActivity {
	
	public static final String TITLE_EXTRA_KEY = "title";
	
	PropertiesList _list;
	List<PropertyDescriptor> _properties = new ArrayList<PropertyDescriptor>();
	
	@Override
	protected View createMainView(ViewGroup parent) {
		generateData();
		
		getActionBar().setTitle(getIntent().getStringExtra(TITLE_EXTRA_KEY));
		
		View view = getLayoutInflater().inflate(R.layout.order_properties_activity, parent, false);
		
		_list = (PropertiesList)view.findViewById(R.id.propertiesList);
		
		_list.setAllowClear(false);
		_list.setProperties(_properties);
		
		return view;
	}
	
	void generateData(){
		NumberPropertyDescriptor prop = new NumberPropertyDescriptor();
		prop.setName("qwe");
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
