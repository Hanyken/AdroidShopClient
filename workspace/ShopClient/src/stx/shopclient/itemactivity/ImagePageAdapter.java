package stx.shopclient.itemactivity;


import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePageAdapter extends PagerAdapter
{
	Context _Context;
	private String ItemId;
	private int _Count = 4;
	private List<String> _Items;
	
    public ImagePageAdapter(Context context, String itemId) 
    {
        _Context = context;
        ItemId = itemId;
        
        _Items = new ArrayList<String>();
        _Items.add("1");
        _Items.add("2");
        _Items.add("3");
        _Items.add("4");
        
    }

	
	
	@Override
	public int getCount()
	{
		return _Count;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0.equals(arg1);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		//LayoutInflater inflater = (LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View root = inflater.inflate(R.layout.item_image_activity_view, null);
		
		ImageView view = new ImageView(_Context);
		view.setImageResource(R.drawable.ic_launcher);

		container.addView(view);
		
		return view;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object view) 
	{
		container.removeView((View) view);
    }
}