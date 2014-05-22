package stx.shopclient.cartactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.order_properties_activity.OrderPropertiesActivity;

public class CartActivity extends BaseActivity implements OnItemClickListener {

	ListView _list;
	CartListAdapter _adapter;

	List<CatalogItem> _cartItems = new ArrayList<CatalogItem>();

	@Override
	protected View createMainView(ViewGroup parent) {

		generateData();

		getActionBar().setTitle("�������");

		View view = getLayoutInflater().inflate(R.layout.cart_activity, parent,
				false);

		_list = (ListView) view.findViewById(R.id.listView);
		_list.setOnItemClickListener(this);

		_adapter = new CartListAdapter();

		_list.setAdapter(_adapter);
		
		registerForContextMenu(_list);

		return view;
	}

	void generateData() {
		for (int i = 1; i <= 5; i++) {
			CatalogItem item = new CatalogItem();
			item.setName("����� " + Integer.toString(i));
			_cartItems.add(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.cart_activity_item_menu, menu);
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

			final View view = getLayoutInflater().inflate(
					R.layout.cart_activity_item, container, false);

			view.setTag(item);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(item.getName());

			TextView descrTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			descrTextView.setText("1 ��.");

			Button menuButton = (Button) view.findViewById(R.id.menuButton);

			menuButton.setTag(item);
			menuButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					openContextMenu(view);
				}
			});

			return view;
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		CatalogItem catalogItem = _cartItems.get(info.position);

		if (item.getItemId() == R.id.edit) {
			Intent intent = new Intent(this, OrderPropertiesActivity.class);
			intent.putExtra(OrderPropertiesActivity.TITLE_EXTRA_KEY,
					catalogItem.getName());
			startActivity(intent);
		} else if (item.getItemId() == R.id.delete) {
			_cartItems.remove(catalogItem);

			_adapter.notifyDataSetChanged();
			Toast.makeText(this, String.format("������� (%s)",catalogItem.getName()), Toast.LENGTH_SHORT).show();
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		CatalogItem item = (CatalogItem) view.getTag();

		Intent intent = new Intent(this, ItemActivity.class);
		intent.putExtra("ItemTitle", item.getName());
		startActivity(intent);
	}
}
