package stx.shopclient.itemactivity;

import java.util.List;

import stx.shopclient.R;
import stx.shopclient.ui.common.GridPagerAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class ItemImagePageAdapter extends GridPagerAdapter<String>
{
	Context _Context;
	private long ItemId;

	public ItemImagePageAdapter(Context context, long itemId,
			List<String> list)
	{
		super(list, context);
		_Context = context;
		ItemId = itemId;
		setGridColumnCount(1);
	}

	@Override
	protected View createItemView(String item)
	{
		// LayoutInflater inflater =
		// (LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View root = inflater.inflate(R.layout.item_image_activity_view,
		// null);

		ImageView view = new ImageView(_Context);
		view.setImageResource(R.drawable.ic_launcher);

		// TextView textView = (TextView)view.findViewById(R.id.textView);
		// textView.setText(item);

		// RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
		// ratingBar.setRating(3.5f);

		return view;
	}

	@Override
	protected void onItemClick(String item)
	{
		Intent intent = new Intent(_Context, ItemImageActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, ItemId);
		_Context.startActivity(intent);
	}
}
