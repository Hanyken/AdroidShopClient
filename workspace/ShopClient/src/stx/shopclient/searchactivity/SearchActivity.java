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
import stx.shopclient.utils.DisplayUtil;

public class SearchActivity extends BaseActivity implements
		OnItemClickListener, DialogResultProcessor {

	public static final int LIST_ITEM_HEIGHT = 50;

	public static final String TITLE_EXTRA_KEY = "Title";

	List<PropertyDescriptor> _props = new ArrayList<PropertyDescriptor>();
	PropertiesListAdapter adapter;

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
		View view = getLayoutInflater().inflate(R.layout.search_activity,
				parent, false);

		adapter = new PropertiesListAdapter(this);

		ListView list = (ListView) view.findViewById(R.id.listView);
		Button buttonClear = (Button) view.findViewById(R.id.buttonClear);

		generateData();

		list.setAdapter(adapter);
		list.setOnItemClickListener(this);

		buttonClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clearSearchParams();
			}
		});

		return view;
	}

	void clearSearchParams() {

		for (PropertyDescriptor prop : _props) {
			prop.clear();
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		PropertyDescriptor prop = (PropertyDescriptor) view.getTag();

		if (prop instanceof EnumPropertyDescriptor) {

			EnumSelectDialog dialog = new EnumSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((EnumPropertyDescriptor) prop);

			dialog.show(getFragmentManager(), prop.getName());

		} else if (prop instanceof NumberPropertyDescriptor) {

			NumberSelectDialog dialog = new NumberSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((NumberPropertyDescriptor) prop);

			dialog.show(getFragmentManager(), prop.getName());

		} else if (prop instanceof DatePropertyDescriptor) {

			DateTimeRangeSelectDialog dialog = new DateTimeRangeSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((DatePropertyDescriptor) prop);
			dialog.show(getFragmentManager(), prop.getName());
		} else if(prop instanceof StringPropertyDescriptor){
			
			StringEditDialog dialog = new StringEditDialog();
			dialog.setItemView(view);
			dialog.setProperty((StringPropertyDescriptor) prop);
			dialog.show(getFragmentManager(), prop.getName());
		}
	}

	void updateEnumListItem(View view, EnumPropertyDescriptor prop) {
		ImageView resetImage = (ImageView) view.findViewById(R.id.imageView);
		TextView captionTextView = (TextView) view.findViewById(R.id.textView);

		captionTextView.setText(prop.getTitle());

		if (prop.getCurrentValues().size() > 0) {
			resetImage.setVisibility(View.VISIBLE);
			captionTextView.setText(prop.getTitle()
					+ String.format(" (%d)", prop.getCurrentValues().size()));
		} else
			resetImage.setVisibility(View.GONE);
	}

	void updateNumberListItem(View view, NumberPropertyDescriptor prop) {
		ImageView resetImage = (ImageView) view.findViewById(R.id.imageView);
		TextView captionTextView = (TextView) view.findViewById(R.id.textView);

		captionTextView.setText(prop.getTitle());

		if (prop.isCurrentValueDefined()) {
			resetImage.setVisibility(View.VISIBLE);
			captionTextView.setText(prop.getTitle()
					+ String.format(" (%.2f - %.2f)",
							prop.getCurrentMinValue(),
							prop.getCurrentMaxValue()));
		} else
			resetImage.setVisibility(View.GONE);
	}

	void updateDateListItem(View view, DatePropertyDescriptor prop) {
		ImageView resetImage = (ImageView) view.findViewById(R.id.imageView);
		TextView captionTextView = (TextView) view.findViewById(R.id.textView);

		captionTextView.setText(prop.getTitle());

		if (prop.isCurrentValueDefined()) {
			resetImage.setVisibility(View.VISIBLE);
			captionTextView.setText(prop.getTitle()
					+ String.format(" (%s - %s)", _dateFormat.format(prop
							.getCurrentMinValue().getTime()), _dateFormat
							.format(prop.getCurrentMaxValue().getTime())));
		} else
			resetImage.setVisibility(View.GONE);
	}

	void updateStringListItem(View view, StringPropertyDescriptor prop) {
		ImageView resetImage = (ImageView) view.findViewById(R.id.imageView);
		TextView captionTextView = (TextView) view.findViewById(R.id.textView);

		captionTextView.setText(prop.getTitle());

		if (prop.getValue() != null && !prop.getValue().equals("")) {
			resetImage.setVisibility(View.VISIBLE);
			captionTextView.setText(prop.getTitle()
					+ String.format(" (%s)", prop.getValue()));
		} else
			resetImage.setVisibility(View.GONE);
	}

	class PropertiesListAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {

		Context _context;

		public PropertiesListAdapter(Context context) {
			_context = context;
		}

		@Override
		public int getCount() {
			return _props.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int idx, View arg1, ViewGroup parent) {
			PropertyDescriptor propDescriptor = _props.get(idx);

			View view = null;

			if (propDescriptor instanceof EnumPropertyDescriptor) {
				view = getLayoutInflater().inflate(
						R.layout.search_activity_item, parent, false);
				view.setTag(propDescriptor);

				initEnumListItem(view, (EnumPropertyDescriptor) propDescriptor);
			} else if (propDescriptor instanceof BooleanPropertyDescriptor) {
				BooleanPropertyDescriptor prop = (BooleanPropertyDescriptor) propDescriptor;

				CheckBox checkBox = new CheckBox(_context);
				checkBox.setChecked(prop.getCurrentValue());
				checkBox.setTag(prop);
				checkBox.setText(prop.getTitle());
				checkBox.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, DisplayUtil.dpToPx(
								LIST_ITEM_HEIGHT, SearchActivity.this)));
				checkBox.setTextSize(20);

				checkBox.setOnCheckedChangeListener(this);

				view = checkBox;
			} else if (propDescriptor instanceof NumberPropertyDescriptor) {
				view = getLayoutInflater().inflate(
						R.layout.search_activity_item, parent, false);
				view.setTag(propDescriptor);

				initNumberListItem(view,
						(NumberPropertyDescriptor) propDescriptor);
			} else if (propDescriptor instanceof DatePropertyDescriptor) {
				view = getLayoutInflater().inflate(
						R.layout.search_activity_item, parent, false);
				view.setTag(propDescriptor);

				initDateListItem(view, (DatePropertyDescriptor) propDescriptor);
			} else if (propDescriptor instanceof StringPropertyDescriptor) {
				view = getLayoutInflater().inflate(
						R.layout.search_activity_item, parent, false);
				view.setTag(propDescriptor);

				initStringListItem(view,
						(StringPropertyDescriptor) propDescriptor);
			} else
				throw new RuntimeException("Unknown PropertyDescriptor");

			return view;
		}

		void initDateListItem(View view, DatePropertyDescriptor prop) {
			final View itemView = view;
			final DatePropertyDescriptor property = prop;

			ImageView resetImage = (ImageView) view
					.findViewById(R.id.imageView);

			updateDateListItem(itemView, property);

			resetImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					property.clear();
					updateDateListItem(itemView, property);
				}
			});
		}

		void initEnumListItem(View view, EnumPropertyDescriptor prop) {
			final View itemView = view;
			final EnumPropertyDescriptor property = prop;

			final ImageView resetImage = (ImageView) view
					.findViewById(R.id.imageView);

			updateEnumListItem(itemView, property);

			resetImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					property.clear();
					updateEnumListItem(itemView, property);
				}
			});
		}

		void initNumberListItem(View view, NumberPropertyDescriptor prop) {
			final View itemView = view;
			final NumberPropertyDescriptor property = prop;

			final ImageView resetImage = (ImageView) view
					.findViewById(R.id.imageView);

			updateNumberListItem(itemView, property);

			resetImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					property.clear();
					updateNumberListItem(itemView, property);
				}
			});
		}

		void initStringListItem(View view, StringPropertyDescriptor prop) {
			final View itemView = view;
			final StringPropertyDescriptor property = prop;

			final ImageView resetImage = (ImageView) view
					.findViewById(R.id.imageView);

			updateStringListItem(itemView, property);

			resetImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					property.clear();
					updateStringListItem(itemView, property);
				}
			});
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			BooleanPropertyDescriptor prop = (BooleanPropertyDescriptor) buttonView
					.getTag();
			prop.setCurrentValue(isChecked);
		}
	}

	@Override
	public void onPositiveDialogResult(View view) {
		if (view.getTag() instanceof EnumPropertyDescriptor)
			updateEnumListItem(view, (EnumPropertyDescriptor) view.getTag());
		else if (view.getTag() instanceof NumberPropertyDescriptor)
			updateNumberListItem(view, (NumberPropertyDescriptor) view.getTag());
		else if (view.getTag() instanceof DatePropertyDescriptor)
			updateDateListItem(view, (DatePropertyDescriptor) view.getTag());
	}

	@Override
	public void onNegativeDialogResult(View view) {

	}
}
