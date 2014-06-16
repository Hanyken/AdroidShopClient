package stx.shopclient.itemactivity;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.repository.ImagesManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.utils.ImageDownloadTask;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePageAdapter extends PagerAdapter
{
	Context _Context;
	private List<String> _Items;
	private ImagesManager _ImgManager;
	private CatalogItem _Item;

	public ImagePageAdapter(Context context, long itemId)
	{
		_Context = context;
		_Item = Repository.get(_Context).getItemsManager().getItem(itemId);
		_Items = new ArrayList<String>();
		_ImgManager = Repository.get(_Context).getImagesManager();
		//_Items.addAll(_ImgManager.getItemImages(itemId));
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
		
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view)
	{
		container.removeView((View) view);
	}
}