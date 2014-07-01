package stx.shopclient.discountactivity;

import java.util.ArrayList;
import java.util.Collection;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Discount;
import stx.shopclient.entity.Token;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.utils.ProgressDialogAsyncTask;
import stx.shopclient.webservice.WebClient;

public class DiscountListActivity extends BaseActivity implements
		OnItemClickListener, SearchView.OnQueryTextListener,
		SearchView.OnCloseListener
{
	PullToRefreshListView _listView;

	List<Discount> _discountList = new ArrayList<Discount>();
	List<Discount> _discountFilteredList = new ArrayList<Discount>();
	DiscountListAdapter _adapter = new DiscountListAdapter();

	public static Discount SelectedDiscount;

	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Скидочные карты");

		View view = getLayoutInflater().inflate(
				R.layout.discount_list_activity, parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.DISABLED);
		_listView.setOnItemClickListener(this);
		_listView.setAdapter(_adapter);

		new RefreshTask().execute();

		return view;
	}

	class RefreshTask extends ProgressDialogAsyncTask<Collection<Discount>>
	{
		public RefreshTask()
		{
			super(DiscountListActivity.this, "Получение списка скидочных карт");
		}

		@Override
		protected Collection<Discount> backgroundTask() throws Throwable
		{
			return createWebClient().getDiscounts(Token.getCurrent());
		}

		@Override
		protected void onPostExecuteNoError(Collection<Discount> result)
		{
			if (result == null)
				return;

			_discountList.clear();
			_discountFilteredList.clear();
			_discountList.addAll(result);
			_discountFilteredList.addAll(result);
			_adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.discount_list_activity_menu, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);

		return true;
	}

	class DiscountListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _discountFilteredList.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View arg1, ViewGroup parent)
		{
			View view = getLayoutInflater().inflate(
					R.layout.discount_list_activity_item, parent, false);

			Discount discount = _discountFilteredList.get(index);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(discount.getCatalogName());

			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			descriptionTextView.setText(discount.getName());
			
			ImageView image = (ImageView)view.findViewById(R.id.imageView);
			setImage(image, discount.getCatalogLogo());

			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
	{
		Discount disc = _discountFilteredList.get(index - 1);
		SelectedDiscount = disc;
		Intent intent = new Intent(this, DiscountActivity.class);

		startActivity(intent);
	}

	@Override
	public boolean onQueryTextChange(String newText)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{

		_discountFilteredList.clear();

		for (Discount disc : _discountList)
		{
			if (query == null || query.equals(""))
				_discountFilteredList.add(disc);
			else if (disc.getCatalogName().toLowerCase()
					.contains(query.toLowerCase()))
				_discountFilteredList.add(disc);
		}

		_adapter.notifyDataSetChanged();

		return true;
	}

	@Override
	public boolean onClose()
	{
		_discountFilteredList.clear();
		_discountFilteredList.addAll(_discountList);
		return false;
	}
}
