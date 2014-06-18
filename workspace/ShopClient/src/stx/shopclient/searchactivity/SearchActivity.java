package stx.shopclient.searchactivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.properties.BooleanPropertyDescriptor;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.entity.properties.StringPropertyDescriptor;
import stx.shopclient.repository.Repository;
import stx.shopclient.searchresultsactivity.SearchResultsActivity;
import stx.shopclient.ui.common.properties.PropertiesList;
import stx.shopclient.utils.DisplayUtil;

public class SearchActivity extends BaseActivity {

	public static final int LIST_ITEM_HEIGHT = 50;

	public static final String TITLE_EXTRA_KEY = "Title";
	public static final String NODE_ID_EXTRA_KEY = "nodeId";
	
	long _nodeId;
	
	PropertiesList _propsList;

	List<PropertyDescriptor> _props = new ArrayList<PropertyDescriptor>();

	SimpleDateFormat _dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Intent intent = getIntent();

		String title = intent.getStringExtra(TITLE_EXTRA_KEY);
		getActionBar().setTitle(title);
		_nodeId = intent.getLongExtra(NODE_ID_EXTRA_KEY, 0);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected View createMainView(ViewGroup parent) {
		
		CatalogNode node = Repository.get(null).getCatalogManager().getNodeById(_nodeId);
		_props.clear();
		_props.addAll(node.getPropertiesCopy());
		
		View view = getLayoutInflater().inflate(R.layout.search_activity,
				parent, false);

		_propsList = (PropertiesList)view.findViewById(R.id.propertiesList);
		_propsList.setProperties(_props);
		
		Button buttonClear = (Button) view.findViewById(R.id.buttonClear);		

		buttonClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clearSearchParams();
			}
		});

		Button buttonSearch = (Button) view.findViewById(R.id.buttonSearch);

		buttonSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onButtonSearchClicked();
			}
		});

		return view;
	}

	void onButtonSearchClicked() {
		SearchResultsActivity.searchProperties = _props;
		Intent intent = new Intent(this, SearchResultsActivity.class);
		intent.putExtra(SearchResultsActivity.EXTRA_KEY_NODE_ID, _nodeId);
		startActivity(intent);
	}

	void clearSearchParams() {
		_propsList.clear();
	}
}
