package stx.shopclient.catalogsactivity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.ActionType;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.ui.common.properties.PropertiesList;
import stx.shopclient.utils.ProgressDialogAsyncTask;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CatalogsActivity extends BaseActivity implements
		SearchView.OnQueryTextListener, SearchView.OnCloseListener,
		PropertiesList.OnChangeListener, OnItemClickListener
{
	static final int MENU_FILTER = 1;

	PullToRefreshListView _listView;
	List<Catalog> _catalogs = new ArrayList<Catalog>();
	CatalogListAdapter _adapter = new CatalogListAdapter();
	Collection<ActionType> _actionTypes = new ArrayList<ActionType>();
	SearchView _searchView;
	LinearLayout _filterLayout;
	EnumPropertyDescriptor _actionTypesProperty = new EnumPropertyDescriptor();
	PropertiesList _propList;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Каталоги");

		View view = getLayoutInflater().inflate(R.layout.catalogs_activity,
				parent, false);

		_filterLayout = (LinearLayout) view.findViewById(R.id.llFilter);

		_searchView = (SearchView) view.findViewById(R.id.searchView);
		_searchView.setOnQueryTextListener(this);
		_searchView.setOnCloseListener(this);

		_propList = (PropertiesList) view.findViewById(R.id.propList);
		_propList.setOnChangeListener(this);

		_listView = (PullToRefreshListView) view.findViewById(R.id.listView);
		_listView.setMode(Mode.PULL_FROM_END);
		_listView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
				{

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView)
					{
						new LoadCatalogsTask(false).execute();
					}
				});
		_listView.setOnItemClickListener(this);
		_listView.setAdapter(_adapter);

		new InitTask().execute();

		return view;
	}

	void initPropList()
	{
		_actionTypesProperty.setName("actionTypes");
		_actionTypesProperty.setTitle("Категории");

		List<EnumValue> values = new ArrayList<EnumPropertyDescriptor.EnumValue>();

		for (ActionType type : _actionTypes)
		{
			EnumValue value = new EnumValue();
			value.setName(type.getName());
			value.setValue(Long.toString(type.getId()));
			values.add(value);
		}

		_actionTypesProperty.setValues(values);
		List<PropertyDescriptor> props = new ArrayList<PropertyDescriptor>();
		props.add(_actionTypesProperty);
		_propList.setProperties(props);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.clear();

		MenuItem categoryFilterItem = menu.add(0, MENU_FILTER, 0, "Фильтр");
		categoryFilterItem.setIcon(R.drawable.img_filter);
		categoryFilterItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
		case MENU_FILTER:
			if (_filterLayout.getVisibility() == View.GONE)
				ShowFilter();
			else
				HideFilter();
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	String getActionsTypesXml()
	{
		if (_actionTypesProperty.isValueDefined())
		{
			try
			{
				Document doc = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().newDocument();

				Element searchEl = doc.createElement("Search");

				for (EnumValue value : _actionTypesProperty.getCurrentValues())
				{
					Element elValue = doc.createElement("Value");
					elValue.setTextContent(value.getName());
					searchEl.appendChild(elValue);
				}

				doc.appendChild(searchEl);

				Transformer transformer = TransformerFactory.newInstance()
						.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						"yes");
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(doc), new StreamResult(
						writer));
				String output = writer.getBuffer().toString();
				return output;
			}
			catch (Throwable ex)
			{
				throw new RuntimeException(ex);
			}
		}
		else
			return null;
	}

	private void ShowFilter()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(_filterLayout,
				"y", -150, 0);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				_filterLayout.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	private void HideFilter()
	{
		ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(_filterLayout,
				"y", 0, -150);
		objectAnimator.addListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
				_filterLayout.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
		objectAnimator.setDuration(500);
		objectAnimator.start();
	}

	@Override
	public boolean onQueryTextChange(String text)
	{
//		if (StringUtils.isBlank(text))
//			return false;

		LoadCatalogsTask task = new LoadCatalogsTask(true);
		task.showProgressDialog = false;
		task.execute();

		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String text)
	{
//		if (StringUtils.isBlank(text))
//			return false;

		new LoadCatalogsTask(true).execute();

		return true;
	}

	@Override
	public boolean onClose()
	{
		new LoadCatalogsTask(true).execute();
		return true;
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

			view.setTag(catalog);

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

	class InitTask extends ProgressDialogAsyncTask<Collection<ActionType>>
	{
		public InitTask()
		{
			super(CatalogsActivity.this, "Получение категорий каталогов");
		}

		@Override
		protected Collection<ActionType> backgroundTask() throws Throwable
		{
			return createWebClient().getActionTypes(Token.getCurrent());
		}

		@Override
		protected void onPostExecuteNoError(Collection<ActionType> result)
		{			
			_actionTypes = result;

			initPropList();

			new LoadCatalogsTask(true).execute();
		}
	}

	class LoadCatalogsTask extends ProgressDialogAsyncTask<Collection<Catalog>>
	{
		boolean firstLoad;

		public LoadCatalogsTask(boolean firstLoad)
		{
			super(CatalogsActivity.this, "Получение каталогов");

			this.firstLoad = firstLoad;

			if (!firstLoad)
				this.showProgressDialog = false;
		}

		@Override
		protected Collection<Catalog> backgroundTask() throws Throwable
		{
			return createWebClient().getCatalogs(Token.getCurrent(),
					_searchView.getQuery().toString(), getActionsTypesXml(),
					null, null, (firstLoad ? 1 : _catalogs.size() + 1), 30);
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			
			if (isDestroyed())
				return;
			
			_listView.onRefreshComplete();
		}

		@Override
		protected void onPostExecuteNoError(Collection<Catalog> result)
		{
			if (firstLoad)
				_catalogs.clear();

			_catalogs.addAll(result);
			_adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onPropertyChange(PropertyDescriptor property)
	{
		new LoadCatalogsTask(true).execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3)
	{
		Catalog catalog = (Catalog) view.getTag();

		Repository.CatalogId = catalog.getId();
		Repository.get(null).getCatalogManager().clearCatalog();
		Repository.saveSelectedCatalogId(this);

		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}
}
