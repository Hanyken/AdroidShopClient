package stx.shopclient.cartactivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.itemactivity.ItemActivity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class CrosssaleActivity extends BaseActivity implements OnClickListener
{
	public static Collection<CatalogItem> crosssaleItems;

	List<CatalogItem> _items = new ArrayList<CatalogItem>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		_items.addAll(crosssaleItems);
		
		getActionBar().setTitle("Посмотрите также");

		View view = getLayoutInflater().inflate(R.layout.crosssale_activity,
				parent, false);
		GridLayout grid = (GridLayout) view.findViewById(R.id.grid);

		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);

		for (CatalogItem entity : _items)
		{
			View itemView = getLayoutInflater().inflate(
					R.layout.item_activity_analog_fragment_item, grid, false);

			itemView.setTag(entity);
			itemView.setOnClickListener(this);

			grid.addView(itemView);

			GridLayout.LayoutParams params = (GridLayout.LayoutParams) itemView
					.getLayoutParams();
			params.width = (int) (outMetrics.widthPixels / grid
					.getColumnCount());
			params.setGravity(Gravity.TOP);
			itemView.setLayoutParams(params);

			TextView textView = (TextView) itemView.findViewById(R.id.lblTitle);

			ImageView imgView = (ImageView) itemView.findViewById(R.id.imgIco);

			if (entity.getIco() != null)
				setImage(imgView, entity.getIco());			

			textView.setText(entity.getName());
		}

		return view;
	}

	@Override
	public void onClick(View view)
	{
		Intent intent = new Intent(this, ItemActivity.class);
		CatalogItem item = (CatalogItem) view.getTag();
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, item.getId());
		startActivity(intent);
	}
}
