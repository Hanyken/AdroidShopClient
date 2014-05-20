package stx.shopclient.searchactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.BooleanPropertyDescriptor;
import stx.shopclient.entity.searchproperties.DatePropertyDescriptor;
import stx.shopclient.entity.searchproperties.EnumPropertyDescriptor;
import stx.shopclient.entity.searchproperties.NumberPropertyDescriptor;
import stx.shopclient.entity.searchproperties.PropertyDescriptor;
import stx.shopclient.entity.searchproperties.StringPropertyDescriptor;
import stx.shopclient.searchresultsactivity.SearchResultsActivity;
import stx.shopclient.ui.common.PropertiesList;
import stx.shopclient.utils.DisplayUtil;

public class SearchActivity extends BaseActivity {

	public static final int LIST_ITEM_HEIGHT = 50;

	public static final String TITLE_EXTRA_KEY = "Title";
	
	PropertiesList _propsList;

	List<PropertyDescriptor> _props = new ArrayList<PropertyDescriptor>();

	SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	void generateData() {
		Random random = new Random();

		for (int i = 0; i < 30; i++) {

			int type = random.nextInt(5);

			if (type == 1) {
				BooleanPropertyDescriptor prop = new BooleanPropertyDescriptor();
				prop.setTitle("Boolean " + Integer.toString(i));
				prop.setName("bool");
				prop.setType(BooleanPropertyDescriptor.TYPE_STRING);
				_props.add(prop);
			} else if (type == 2) {
				NumberPropertyDescriptor prop = new NumberPropertyDescriptor();
				prop.setTitle("Numeric " + Integer.toString(i));
				prop.setName("number");
				prop.setType(NumberPropertyDescriptor.TYPE_STRING);
				prop.setMinValue(random.nextInt(10));
				prop.setMaxValue(random.nextInt(50) + 10);
				_props.add(prop);
			} else if (type == 3) {
				DatePropertyDescriptor prop = new DatePropertyDescriptor();
				prop.setTitle("Date" + Integer.toString(i));
				prop.setName("date");
				prop.setType(DatePropertyDescriptor.TYPE_STRING);
				prop.setMinValue(new GregorianCalendar(1997, 1, 1));
				prop.setMaxValue(new GregorianCalendar(2020, 1, 1));
				_props.add(prop);
			} else if (type == 4) {
				StringPropertyDescriptor prop = new StringPropertyDescriptor();
				prop.setTitle("String" + Integer.toString(i));
				prop.setName("string");
				prop.setType(StringPropertyDescriptor.TYPE_STRING);
				_props.add(prop);
			} else {
				EnumPropertyDescriptor prop = new EnumPropertyDescriptor();
				prop.setName("OS");
				if (i == 0)
					prop.setTitle("Платформа");
				else
					prop.setTitle("Enumeration " + Integer.toString(i));

				if (i == 0) {
					prop.getValues()
							.add(new EnumPropertyDescriptor.EnumValue("1",
									"Android"));
					prop.getValues().add(
							new EnumPropertyDescriptor.EnumValue("2", "iOS"));
					prop.getValues().add(
							new EnumPropertyDescriptor.EnumValue("3",
									"Windows Phone"));
				} else {
					int size = random.nextInt(20) + 2;
					for (int j = 0; j < size; j++) {
						prop.getValues().add(
								new EnumPropertyDescriptor.EnumValue(Integer
										.toString(j), "Значение"
										+ Integer.toString(j)));
					}
				}
				prop.setType(EnumPropertyDescriptor.TYPE_STRING);

				_props.add(prop);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Intent intent = getIntent();

		String title = intent.getStringExtra(TITLE_EXTRA_KEY);
		getActionBar().setTitle(title);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		
		generateData();
		
		View view = getLayoutInflater().inflate(R.layout.search_activity,
				parent, false);

		_propsList = (PropertiesList)view.findViewById(R.id.propertiesList);
		_propsList.setProperties(_props);
		
		Button buttonClear = (Button) view.findViewById(R.id.buttonClear);		

		buttonClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clearSearchParams();
			}
		});

		Button buttonSearch = (Button) view.findViewById(R.id.buttonSearch);

		buttonSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onButtonSearchClicked();
			}
		});

		return view;
	}

	void onButtonSearchClicked() {
		Intent intent = new Intent(this, SearchResultsActivity.class);
		startActivity(intent);
	}

	void clearSearchParams() {
		_propsList.clear();
	}
}
