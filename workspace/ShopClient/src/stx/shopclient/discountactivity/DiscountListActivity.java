package stx.shopclient.discountactivity;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Discount;
import stx.shopclient.mainmenu.MainMenuItem;

public class DiscountListActivity extends BaseActivity implements
		OnItemClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
	PullToRefreshListView _listView;

	List<Discount> _discountList = new ArrayList<Discount>();
	List<Discount> _discountFilteredList = new ArrayList<Discount>();
	DiscountListAdapter _adapter = new DiscountListAdapter();

	public static Discount SelectedDiscount;

	void generateData() {
		Discount disc = new Discount();
		disc.setCatalogName("Ecco");
		disc.setName("5% скидка на зимнюю обувь");
		disc.setDescription("Описание скидки");
		disc.setSize(5);
		disc.setUnitType(Discount.UNIT_TYPE_PERCENT);
		disc.setCode("123234235345345");
		_discountFilteredList.add(disc);

		disc = new Discount();
		disc.setCatalogName("Nike");
		disc.setName("Скидка 10% на футболки");
		disc.setDescription("Описание скидки");
		disc.setSize(10);
		disc.setUnitType(Discount.UNIT_TYPE_PERCENT);
		disc.setCode("00032050345");
		_discountFilteredList.add(disc);

		disc = new Discount();
		disc.setCatalogName("Mobishop");
		disc.setName("Скидка 30% на Nokia Lumia");
		disc.setDescription("Описание скидки");
		disc.setSize(30);
		disc.setUnitType(Discount.UNIT_TYPE_PERCENT);
		disc.setCode("123234235345345");
		_discountFilteredList.add(disc);

		disc = new Discount();
		disc.setCatalogName("Re:Store");
		disc.setName("Скидка 500 рублей на iPad");
		disc.setDescription("Описание скидки");
		disc.setSize(500);
		disc.setUnitType(Discount.UNIT_TYPE_RUB);
		disc.setCode("345345ljh7876593456l");
		_discountFilteredList.add(disc);

		_discountList.addAll(_discountFilteredList);
	}
	
	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		generateData();

		getActionBar().setTitle("Скидочные карты");

		View view = getLayoutInflater().inflate(
				R.layout.discount_list_activity, parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		//_listView.setMode(Mode.BOTH);
		_listView.setOnItemClickListener(this);
		_listView.setAdapter(_adapter);
		
		_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
		{

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView)
			{
				new RefreshTask().execute();
			}
		});

		return view;
	}
	
	class RefreshTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			_listView.onRefreshComplete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.discount_list_activity_menu, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);

		return true;
	}

	class DiscountListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return _discountFilteredList.size();
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
		public View getView(int index, View arg1, ViewGroup parent) {

			View view = getLayoutInflater().inflate(
					R.layout.discount_list_activity_item, parent, false);

			Discount discount = _discountFilteredList.get(index);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(discount.getCatalogName());

			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			descriptionTextView.setText(discount.getName());

			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		Discount disc = _discountFilteredList.get(index - 1);
		SelectedDiscount = disc;
		Intent intent = new Intent(this, DiscountActivity.class);

		startActivity(intent);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {

		_discountFilteredList.clear();

		for (Discount disc : _discountList) {
			if (query == null || query.equals(""))
				_discountFilteredList.add(disc);
			else if (disc.getCatalogName().toLowerCase().contains(query.toLowerCase()))
				_discountFilteredList.add(disc);
		}
		
		_adapter.notifyDataSetChanged();

		return true;
	}

	@Override
	public boolean onClose() {
		_discountFilteredList.clear();
		_discountFilteredList.addAll(_discountList);
		return false;
	}
}
