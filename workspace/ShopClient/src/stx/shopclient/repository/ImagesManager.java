package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

public class ImagesManager 
{
	private ArrayList<String> _Items;
	
	public ImagesManager()
	{
		_Items = new ArrayList<String>();
		
		_Items.add("1");
		_Items.add("2");
		_Items.add("3");
		_Items.add("4");
	}
	
	public Collection<String> getItemImages(long itemId)
	{
		return _Items;
	}
}
