package stx.shopclient.catalogsactivity;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.view.View;
import android.view.ViewGroup;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;

public class CatalogsActivity extends BaseActivity
{
	PullToRefreshListView _listView;
	
	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.catalogs_activity, parent, false);
		
		_listView = (PullToRefreshListView)view.findViewById(R.id.listView);
		
		return view;
	}
}
