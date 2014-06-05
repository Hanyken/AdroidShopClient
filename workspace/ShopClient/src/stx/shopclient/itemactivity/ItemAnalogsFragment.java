package stx.shopclient.itemactivity;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.repository.ItemsManager;
import stx.shopclient.repository.Repository;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemAnalogsFragment extends Fragment implements OnClickListener
{
	private static final String TITLE_NAME = "Title";
	private static final String IDS_NAME = "Ids"; 
	
	public static Fragment getIntent(String title, long[] ids)
	{
		Fragment fragment = new ItemAnalogsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE_NAME, title);
		bundle.putLongArray(IDS_NAME, ids);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.item_activity_analog_fragment, container, false);
		
		Bundle args = getArguments();
		CatalogSettings settings = Repository.getIntent().getCatalogManager().getSettings();
		
		TextView lblTitle = (TextView)view.findViewById(R.id.lblTitle);
		lblTitle.setText(args.getString(TITLE_NAME));
		lblTitle.setBackgroundColor(settings.getBackground());
		lblTitle.setTextColor(settings.getForegroundColor());
		
		GridLayout glList = (GridLayout)view.findViewById(R.id.glList);
		loadGrid(glList, args.getLongArray(IDS_NAME), inflater);
		
		LinearLayout llSeparate = (LinearLayout) view.findViewById(R.id.llSeparate);
		llSeparate.setBackgroundColor(settings.getItemPanelColor());
		
		return view;
	}
	
	
	void loadGrid(GridLayout view, long[] ids, LayoutInflater inflater)
	{

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		//float density = getResources().getDisplayMetrics().density;
		//float dpHeight = outMetrics.heightPixels / density;
		//float dpWidth = outMetrics.widthPixels / density;

		view.removeAllViews();
		ItemsManager manager = Repository.getIntent().getItemsManager();
		
		for (long id : ids)
		{
			CatalogItem entity = manager.getItem(id);
			
			View itemView = inflater.inflate(R.layout.main_activity_rootnodes_browser_griditem, view, false);

			itemView.setTag(id);
			itemView.setOnClickListener(this);

			view.addView(itemView);

			GridLayout.LayoutParams params = (GridLayout.LayoutParams) itemView
					.getLayoutParams();
			params.width = (int) (outMetrics.widthPixels / view.getColumnCount());
			params.setGravity(Gravity.TOP);
			itemView.setLayoutParams(params);

			TextView textView = (TextView) itemView.findViewById(R.id.textView);

			ImageView imgView = (ImageView) itemView
					.findViewById(R.id.imageView);
			
			imgView.setImageBitmap(Repository.getIntent().getImagesManager().getImage(entity.getIco()));
			
			textView.setText(entity.getName());
		}
		
	}

	@Override
	public void onClick(View arg0)
	{
		Intent intent = new Intent(getActivity(), ItemActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, (Long)arg0.getTag());
		startActivity(intent);
	}
}
