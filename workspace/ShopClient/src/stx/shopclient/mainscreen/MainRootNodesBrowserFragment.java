package stx.shopclient.mainscreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogNode;
import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainRootNodesBrowserFragment extends Fragment {

	List<CatalogNode> _nodes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.screen_main_rootnodes_browser,
				container, false);

		TabHost tabHost = (TabHost) view.findViewById(R.id.tabhost);

		tabHost.setup();

		TabHost.TabSpec spec = tabHost.newTabSpec("tag1");

		spec.setContent(R.id.linearLayout);
		spec.setIndicator("Список");
		tabHost.addTab(spec);

		spec = tabHost.newTabSpec("tag2");
		spec.setContent(R.id.gridLayout);
		spec.setIndicator("Плитки");
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		loadDataFromDatabase();
		
		loadList(view);
		loadGrid(view);

		return view;
	}
	
	void loadDataFromDatabase(){
		_nodes = generateData();
	}

	void loadGrid(View view) {
		
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpHeight = outMetrics.heightPixels / density;
		float dpWidth = outMetrics.widthPixels / density;

		GridLayout gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
		
		gridLayout.removeAllViews();

		for (CatalogNode node : _nodes) {
			View itemView = getActivity().getLayoutInflater()
					.inflate(R.layout.screen_main_rootnodes_browser_griditem, gridLayout, false);

			gridLayout.addView(itemView);

			GridLayout.LayoutParams params = (GridLayout.LayoutParams) itemView
					.getLayoutParams();
			params.width = (int) (outMetrics.widthPixels / gridLayout
					.getColumnCount());
			params.setGravity(Gravity.CENTER);
			itemView.setLayoutParams(params);

			TextView textView = (TextView) itemView.findViewById(R.id.textView);
	
			textView.setText(node.getName());
		}
	}

	void loadList(View view) {

		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.linearLayout);
		
		linearLayout.removeAllViews();

		for (CatalogNode node : _nodes) {
			View itemView = getActivity().getLayoutInflater().inflate(
					R.layout.screen_main_rootnodes_browser_listitem, linearLayout, false);

			TextView nameTextView = (TextView) itemView.findViewById(R.id.nodeNameTextView);
			TextView descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);

			nameTextView.setText(node.getName());
			
			descriptionTextView.setText(getDescriptionForListItem(node));

			linearLayout.addView(itemView);
		}
	}
	
	String getDescriptionForListItem(CatalogNode node){
		String description = String.format("%d товаров от %d рублей", node.getCount(), (int)node.getMinPrice());
		return description;
	}

	List<CatalogNode> generateData() {
		
		Random random = new Random();		
		
		List<CatalogNode> data = new ArrayList<CatalogNode>();

		for (int i = 0; i < 30; i++) {
			CatalogNode node = new CatalogNode();
			node.setName("Категория " + Integer.toString(i));
			node.setMinPrice(random.nextInt(10000));
			node.setCount(random.nextInt(200));
			data.add(node);
		}

		return data;
	}
}
