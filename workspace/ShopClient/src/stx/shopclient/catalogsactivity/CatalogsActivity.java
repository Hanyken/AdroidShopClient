package stx.shopclient.catalogsactivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Catalog;

public class CatalogsActivity extends BaseActivity
{
	PullToRefreshListView _listView;
	List<Catalog> _catalogs = new ArrayList<Catalog>();
	CatalogListAdapter _adapter = new CatalogListAdapter();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.catalogs_activity,
				parent, false);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.PULL_FROM_END);
		_listView.setAdapter(_adapter);

		return view;
	}

	class CatalogListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return _catalogs.size();
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
		public View getView(int index, View arg1, ViewGroup arg2)
		{
			Catalog catalog = _catalogs.get(index);

			View view = getLayoutInflater().inflate(
					R.layout.catalogs_activity_item, arg2, false);

			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			TextView textView = (TextView) view.findViewById(R.id.nameTextView);
			TextView descriptionTextView = (TextView) view
					.findViewById(R.id.descriptionTextView);

			if (StringUtils.isNoneBlank(catalog.getLogo()))
				setImage(imageView, catalog.getLogo());
			textView.setText(catalog.getName());
			descriptionTextView.setText(catalog.getDescription());

			return view;
		}
	}
		
}
