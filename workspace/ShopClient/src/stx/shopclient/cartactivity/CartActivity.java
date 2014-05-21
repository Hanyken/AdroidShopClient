package stx.shopclient.cartactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.order_properties_activity.OrderPropertiesActivity;

public class CartActivity extends BaseActivity implements OnItemClickListener {

	ListView _list;
	CartListAdapter _adapter;

	List<CatalogItem> _cartItems = new ArrayList<CatalogItem>();

	@Override
	protected View createMainView(ViewGroup parent) {

		generateData();

		getActionBar().setTitle("Корзина");

		View view = getLayoutInflater().inflate(R.layout.cart_activity, parent,
				false);

		_list = (ListView) view.findViewById(R.id.listView);

		_adapter = new CartListAdapter();

		_list.setAdapter(_adapter);
		_list.setOnItemClickListener(this);

		return view;
	}

	void generateData() {
		for (int i = 1; i <= 5; i++) {
			CatalogItem item = new CatalogItem();
			item.setName("Товар " + Integer.toString(i));
			_cartItems.add(item);
		}
	}

	void removeImageClicked(View view) {
		CatalogItem item = (CatalogItem) view.getTag();
		_cartItems.remove(item);

		_adapter.notifyDataSetChanged();
	}

	class CartListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return _cartItems.size();
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
		public View getView(int index, View arg1, ViewGroup container) {

			CatalogItem item = _cartItems.get(index);

			View view = getLayoutInflater().inflate(
					R.layout.cart_activity_item, container, false);

			view.setTag(item);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(item.getName());

			TextView descrTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			descrTextView.setText("1 шт.");

			ImageView removeImage = (ImageView) view
					.findViewById(R.id.removeImageView);
			removeImage.setTag(item);
			removeImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					removeImageClicked(v);
				}
			});

			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		CatalogItem item = (CatalogItem) view.getTag();
		
		Intent intent = new Intent(this, OrderPropertiesActivity.class);
		intent.putExtra(OrderPropertiesActivity.TITLE_EXTRA_KEY, item.getName());
		startActivity(intent);
	}
}
