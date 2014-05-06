package stx.shopclient.searchactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.EnumPropertyDescriptor;
import stx.shopclient.entity.searchproperties.PropertyDescriptor;

public class SearchActivity extends BaseActivity implements
		OnItemClickListener, DialogResultProcessor {

	@Override
	protected View createMainView(ViewGroup parent) {
		View view = getLayoutInflater().inflate(R.layout.search_activity,
				parent, false);

		PropertiesListAdapter adapter = new PropertiesListAdapter(this);

		ListView list = (ListView) view.findViewById(R.id.listView);

		list.setAdapter(adapter);
		list.setOnItemClickListener(this);

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		PropertyDescriptor prop = (PropertyDescriptor) view.getTag();

		if (prop instanceof EnumPropertyDescriptor) {
			EnumSelectDialog dialog = new EnumSelectDialog();
			dialog.setItemView(view);
			dialog.setProperty((EnumPropertyDescriptor) prop);

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

	class PropertiesListAdapter extends BaseAdapter {

		List<PropertyDescriptor> _props = new ArrayList<PropertyDescriptor>();
		Context _context;

		public PropertiesListAdapter(Context context) {
			_context = context;
			generateData();
		}

		void generateData() {
			Random random = new Random();

			for (int i = 0; i < 30; i++) {
				EnumPropertyDescriptor prop = new EnumPropertyDescriptor();
				prop.setName("OS");
				if (i == 0)
					prop.setTitle("Платформа");
				else
					prop.setTitle("Параметр " + Integer.toString(i));

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
					int size = random.nextInt(20);
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
						R.layout.search_activity_enum_item, parent, false);
				view.setTag(propDescriptor);

				initEnumListItem(view, (EnumPropertyDescriptor) propDescriptor);
			} else
				throw new RuntimeException("Unknown PropertyDescriptor");

			return view;
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
					property.getCurrentValues().clear();
					updateEnumListItem(itemView, property);
				}
			});
		}
	}

	@Override
	public void onPositiveDialogResult(View view) {
		if (view.getTag() instanceof EnumPropertyDescriptor)
			updateEnumListItem(view, (EnumPropertyDescriptor) view.getTag());
	}

	@Override
	public void onNegativeDialogResult(View view) {

	}
}
