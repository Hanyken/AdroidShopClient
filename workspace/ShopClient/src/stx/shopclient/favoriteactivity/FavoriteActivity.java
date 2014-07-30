package stx.shopclient.favoriteactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Token;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.webservice.WebClient;

public class FavoriteActivity extends BaseActivity implements
		OnItemClickListener
{
	PullToRefreshListView _list;
	List<CatalogItem> _items = new ArrayList<CatalogItem>();
	FavoriteAdapter _adapter;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Избранное");

		View view = getLayoutInflater().inflate(R.layout.favorite_activity,
				parent, false);
		_list = (PullToRefreshListView) view.findViewById(R.id.list);
		_list.setMode(Mode.DISABLED);
		_adapter = new FavoriteAdapter();
		_list.setAdapter(_adapter);
		_list.setOnItemClickListener(this);

		new LoadTask().execute();

		return view;
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
			View view = getLayoutInflater().inflate(
					R.layout.favorite_activity_item, root, false);

			view.setTag(item);

			TextView nameTextView = (TextView) view
					.findViewById(R.id.nameTextView);
			nameTextView.setText(item.getName());

			ImageView imgView = (ImageView) view.findViewById(R.id.imageView);

			if (item.getIco() != null)
				ImageDownloadTask.startNew(imgView, FavoriteActivity.this,
						item.getIco());

			Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
			deleteButton.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					deleteButtonClick(item);
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
