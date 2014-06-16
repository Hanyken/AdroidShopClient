package stx.shopclient.itemactivity;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.repository.ImagesManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ItemImagePageAdapter extends PagerAdapter implements OnClickListener
{
	Context _Context;
	private long ItemId;
	private List<String> _Items;
	private ImagesManager _ImgManager;
	private CatalogItem _Item;

	public ItemImagePageAdapter(Context context, long itemId)
	{
		_Context = context;
		ItemId = itemId;
		_Item = Repository.get(_Context).getItemsManager().getItem(itemId);
		_Items = new ArrayList<String>();
		_ImgManager = Repository.get(_Context).getImagesManager();
		_Items.addAll(_Item.getImages());
	}

	@Override
	public int getCount()
	{
		return _Items.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0.equals(arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		ImageView view = new ImageView(_Context);
		view.setTag(position);

		ImageDownloadTask.startNew(view, _Context, _Items.get(position));
		
		view.setOnClickListener(this);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view)
	{
		container.removeView((View) view);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(_Context, ItemImageActivity.class);
		intent.putExtra(ItemActivity.ITEM_ID_EXTRA_KEY, ItemId);
		intent.putExtra(ItemImageActivity.SELECTED_ITEM_POSITION, (Integer)v.getTag());
		_Context.startActivity(intent);
	}


}
