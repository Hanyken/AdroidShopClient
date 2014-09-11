package stx.shopclient.favoriteactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.order_properties_activity.OrderPropertiesActivity;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.WebClient;

public class FavoriteActivity extends BaseActivity implements
		OnItemClickListener
{
	ListView _list;
	List<CatalogItem> _items = new ArrayList<CatalogItem>();
	FavoriteAdapter _adapter;
	
	final int ADD_TO_CART_MENU_ID = 1;
	final int REMOVE_MENU_ID = 2;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Избранное");
 
		View view = getLayoutInflater().inflate(R.layout.favorite_activity,
				parent, false);
		_list = (ListView) view.findViewById(R.id.list);
		_adapter = new FavoriteAdapter();
		_list.setAdapter(_adapter);
		_list.setOnItemClickListener(this);
		
		registerForContextMenu(_list);		

		new LoadTask().execute();

		return view;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo)
	{

		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.add(0, ADD_TO_CART_MENU_ID, 0, "В корзину");
		menu.add(0, REMOVE_MENU_ID, 0, "Убрать из избранных");	
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		
		CatalogItem favItem = (CatalogItem) info.targetView.getTag();

		if (item.getItemId() == ADD_TO_CART_MENU_ID)
		{
			Intent orderIntent = new Intent(this, OrderActivity.class);
			orderIntent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, favItem.getId());
			orderIntent.putExtra("ItemTitle", favItem.getName());
			startActivity(orderIntent);
		}
		else if (item.getItemId() == REMOVE_MENU_ID)
		{
			deleteButtonClick(favItem);
		}

		return true;
	}

	class FavoriteAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _items.size();
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
		public View getView(int index, View arg1, ViewGroup root)
		{
			final CatalogItem item = _items.get(index);
			final View view = getLayoutInflater().inflate(
					R.layout.favorite_activity_item, root, false);

			view.setTag(item);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);
			
			nameTextView.setText(item.getName());
			descriptionTextView.setText(Double.toString(item.getPrice())
					+ " рублей");

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

			if (item.getIco() != null)
				ImageDownloadTask.startNew(imgView, FavoriteActivity.this,
						item.getIco());

			Button menuButton = (Button) view.findViewById(R.id.menuButton);
			menuButton.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					openContextMenu(view);
				}
			});

			return view;
		}

	}

	void deleteButtonClick(CatalogItem item)
	{
		DeleteTask task = new DeleteTask();
		task.item = item;
		task.execute();
	}

	class LoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;
		Collection<CatalogItem> items;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(FavoriteActivity.this, "Загрузка",
					"Получение списка избранных");
			ProgressDlgUtil.setCancellable(dialog, FavoriteActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				items = client.getFavorite(Token.getCurrent());
			}
			catch (Throwable ex)
			{
				exception = ex;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			if (isDestroyed())
				return;
			
			dialog.dismiss();

			if (exception != null)
				Toast.makeText(FavoriteActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				_items.clear();
				_items.addAll(items);
				_adapter.notifyDataSetChanged();
			}
		}
	}

	class DeleteTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception;
		ProgressDialog dialog;
		public CatalogItem item;

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(FavoriteActivity.this, "Загрузка",
					"Удаление элемента из избранного");
			ProgressDlgUtil.setCancellable(dialog, FavoriteActivity.this);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				client.delFavorite(Token.getCurrent(), item.getId());
			}
			catch (Throwable ex)
			{
				exception = ex;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			if (isDestroyed())
				return;
			
			dialog.dismiss();

			if (exception != null)
				Toast.makeText(FavoriteActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
			else
			{
				new LoadTask().execute();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3)
	{
		CatalogItem item = (CatalogItem) view.getTag();
		Intent intent = new Intent(this, ItemActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, item.getId());
		startActivity(intent);
	}
}
