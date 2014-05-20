package stx.shopclient.itemactivity;

import java.util.List;

import stx.shopclient.R;
import stx.shopclient.ui.common.GridPagerAdapter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class ItemImagePageAdapter extends GridPagerAdapter<String> 
{
	Context _Context;
	
    public ItemImagePageAdapter(Context context, List<String> list) 
    {
        super(list, context);
        _Context = context;
        setGridColumnCount(1);
    }

	@Override
	protected View createItemView(String item) 
	{		
		//LayoutInflater inflater = (LayoutInflater)_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View view = inflater.inflate(R.layout.main_activity_catalogitems_viewpager_fragment_item, null);
		
		ImageView view = new ImageView(_Context);
		view.setImageResource(R.drawable.ic_launcher);
		
		//TextView textView = (TextView)view.findViewById(R.id.textView);
		//textView.setText(item);
		
		//RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
		//ratingBar.setRating(3.5f);
		
		return view;
	}
}
