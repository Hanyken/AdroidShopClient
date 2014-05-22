package stx.shopclient.itemactivity;


import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import stx.shopclient.repository.Repository;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePageAdapter extends PagerAdapter implements OnClickListener
{
	Context _Context;
	private Long ItemId;
	private List<String> _Items;
	
    public ImagePageAdapter(Context context, long itemId) 
    {
        _Context = context;
        ItemId = itemId;
        
        String[] images = Repository.getImages(itemId);
        _Items = new ArrayList<String>();
        for(String el : images)
        	_Items.add(el);
        
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
		//LayoutInflater inflater = (LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View root = inflater.inflate(R.layout.item_image_activity_view, null);
		
		ImageView view = new ImageView(_Context);
		view.setImageResource(R.drawable.ic_launcher);
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
		((ItemImageActivity)_Context).onImageClick(v);
	}
}