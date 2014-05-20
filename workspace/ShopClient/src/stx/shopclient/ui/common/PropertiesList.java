package stx.shopclient.ui.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.BooleanPropertyDescriptor;
import stx.shopclient.entity.searchproperties.DatePropertyDescriptor;
import stx.shopclient.entity.searchproperties.EnumPropertyDescriptor;
import stx.shopclient.entity.searchproperties.NumberPropertyDescriptor;
import stx.shopclient.entity.searchproperties.PropertyDescriptor;
import stx.shopclient.entity.searchproperties.StringPropertyDescriptor;
import stx.shopclient.searchactivity.DateTimeRangeSelectDialog;
import stx.shopclient.searchactivity.DialogResultProcessor;
import stx.shopclient.searchactivity.EnumSelectDialog;
import stx.shopclient.searchactivity.NumberSelectDialog;
import stx.shopclient.searchactivity.StringEditDialog;
import stx.shopclient.utils.DisplayUtil;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PropertiesList extends FrameLayout implements OnItemClickListener,
		DialogResultProcessor {

	public static final int LIST_ITEM_HEIGHT = 50;

	List<PropertyDescriptor> _props = new ArrayList<PropertyDescriptor>();
	PropertiesListAdapter adapter;
	ListView _list;

	SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	public PropertiesList(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.addView(createView());
		
		_list.setOnItemClickListener(this);
	}

	public List<PropertyDescriptor> getProperties() {
		return _props;
	}
	
	public void setProperties(List<PropertyDescriptor> props){
		_props = props;
		
		adapter = new PropertiesListAdapter(getContext());
		_list.setAdapter(adapter);		
	}
	
	public void clear() {

		for (PropertyDescriptor prop : _props) {
			prop.clear();
		}

		adapter.notifyDataSetChanged();
	}

	View createView() {
		_list = new ListView(getContext());

		return _list;
	}

	LayoutInflater getLayoutInflater() {
		return (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	FragmentManager getFragmentManager() {

		if (!(getContext() instanceof FragmentActivity))
			throw new RuntimeException(
					"PropertiesList must be in context of FragmentActivity");

		return ((FragmentActivity) getContext()).getFragmentManager();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		PropertyDescriptor prop = (PropertyDescriptor) view.getTag();

		if (prop instanceof EnumPropertyDescriptor) {

			EnumSelectDialog dialog = new EnumSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((EnumPropertyDescriptor) prop);
			dialog.setResultProcessor(this);
			dialog.show(getFragmentManager(), prop.getName());

		} else if (prop instanceof NumberPropertyDescriptor) {

			NumberSelectDialog dialog = new NumberSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((NumberPropertyDescriptor) prop);
			dialog.setResultProcessor(this);
			dialog.show(getFragmentManager(), prop.getName());

		} else if (prop instanceof DatePropertyDescriptor) {

			DateTimeRangeSelectDialog dialog = new DateTimeRangeSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((DatePropertyDescriptor) prop);
			dialog.setResultProcessor(this);
			dialog.show(getFragmentManager(), prop.getName());
		} else if (prop instanceof StringPropertyDescriptor) {

			StringEditDialog dialog = new StringEditDialog();
			dialog.setItemView(view);
			dialog.setProperty((StringPropertyDescriptor) prop);
			dialog.setResultProcessor(this);
			dialog.show(getFragmentManager(), prop.getName());
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
				checkBox.setLayoutParams(new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, DisplayUtil.dpToPx(
								LIST_ITEM_HEIGHT, getContext())));
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

}
