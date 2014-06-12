package stx.shopclient.mainactivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.astuetz.PagerSlidingTabStrip;

import stx.shopclient.R;
import stx.shopclient.catalogbrowseractivity.CatalogBrowserActivity;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainRootNodesBrowserFragment extends Fragment implements
		OnClickListener
{

	List<CatalogNode> _nodes;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.main_activity_rootnodes_browser,
				container, false);

		PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) view
				.findViewById(R.id.tabs);

		tabStrip.setTabNames(new String[]
		{ "Список", "Плитки" });

		tabStrip.setIndicatorColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setUnderlineColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getBackground());
		tabStrip.setBackgroundColor(Repository.get(getActivity()).getCatalogManager()
				.getSettings().getItemPanelColor());

		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout);
		tabStrip.setFrameLayout(frameLayout);

		loadDataFromDatabase();

		loadList(view);
		loadGrid(view);

		return view;
	}

	void loadDataFromDatabase()
	{
		// _nodes = generateData();
		_nodes = new ArrayList<CatalogNode>();
		_nodes.addAll(Repository.get(getActivity()).getCatalogManager().getNodes());
	}

	void loadGrid(View view)
	{

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		float density = getResources().getDisplayMetrics().density;
		float dpHeight = outMetrics.heightPixels / density;
		float dpWidth = outMetrics.widthPixels / density;

		GridLayout gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);

		gridLayout.removeAllViews();

		for (CatalogNode node : _nodes)
		{
			View itemView = getActivity().getLayoutInflater().inflate(
					R.layout.main_activity_rootnodes_browser_griditem,
					gridLayout, false);

			itemView.setTag(node);
			itemView.setOnClickListener(this);

			gridLayout.addView(itemView);

			GridLayout.LayoutParams params = (GridLayout.LayoutParams) itemView
					.getLayoutParams();
			params.width = (int) (outMetrics.widthPixels / gridLayout
					.getColumnCount());
			params.setGravity(Gravity.TOP);
			itemView.setLayoutParams(params);

			TextView textView = (TextView) itemView.findViewById(R.id.textView);

			ImageView imgView = (ImageView) itemView
					.findViewById(R.id.imageView);
			imgView.setBackground(getNodeIconBackground());
			ImageDownloadTask.startNew(imgView, getActivity(), node.getIcon(), false);			

			textView.setText(node.getName());
		}
	}

	void loadList(View view)
	{

		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.linearLayout);

		linearLayout.removeAllViews();

		for (CatalogNode node : _nodes)
		{
			View itemView = getActivity().getLayoutInflater().inflate(
					R.layout.main_activity_rootnodes_browser_listitem,
					linearLayout, false);

			itemView.setTag(node);
			itemView.setOnClickListener(this);

			TextView nameTextView = (TextView) itemView
					.findViewById(R.id.nodeNameTextView);
			TextView descriptionTextView = (TextView) itemView
					.findViewById(R.id.descriptionTextView);

			nameTextView.setText(node.getName());

			descriptionTextView.setText(getDescriptionForListItem(node));

			ImageView imgView = (ImageView) itemView
					.findViewById(R.id.imageView);
			imgView.setBackground(getNodeIconBackground());
			ImageDownloadTask.startNew(imgView, getActivity(), node.getIcon(), false);

			linearLayout.addView(itemView);
		}
	}

	public Drawable getNodeIconBackground()
	{
		float radius = 20.0f;
		float[] rads = new float[]
		{ radius, radius, radius, radius, radius, radius, radius, radius };
		RoundRectShape shape = new RoundRectShape(rads, null, null);
		ShapeDrawable drawable = new ShapeDrawable(shape);
		drawable.getPaint().setColor(
				Repository.get(getActivity()).getCatalogManager().getSettings()
						.getBackground());

		return drawable;
	}

	String getDescriptionForListItem(CatalogNode node)
	{
		String description = String.format("%d товаров от %d рублей",
				node.getCount(), (int) node.getMinPrice());
		return description;
	}

	List<CatalogNode> generateData()
	{

		Random random = new Random();

		List<CatalogNode> data = new ArrayList<CatalogNode>();

		for (int i = 0; i < 30; i++)
		{
			CatalogNode node = new CatalogNode();
			node.setName("Категория " + Integer.toString(i));
			node.setMinPrice(random.nextInt(10000));
			node.setCount(random.nextInt(200));
			data.add(node);
		}

		return data;
	}

	void onCatalogNodeClick(CatalogNode node)
	{
		Intent intent = new Intent(getActivity(), CatalogBrowserActivity.class);

		intent.putExtra(CatalogBrowserActivity.NODE_ID_EXTRA_KEY, node.getId());
		intent.putExtra(CatalogBrowserActivity.NODE_NAME_EXTRA_KEY,
				node.getName());

		startActivity(intent);
	}

	@Override
	public void onClick(View v)
	{
		if (v.getTag() == null)
			return;

		CatalogNode node = (CatalogNode) v.getTag();

		onCatalogNodeClick(node);
	}
}
