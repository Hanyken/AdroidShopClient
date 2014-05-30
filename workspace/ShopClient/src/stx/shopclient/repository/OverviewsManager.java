package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.Overview;;

public class OverviewsManager
{
	private ArrayList<Overview> _Items;
	
	public OverviewsManager()
	{
		_Items = new ArrayList<Overview>();
	}
	
	public Collection<Overview> getOverviews(long itemId)
	{
		ArrayList<Overview> items = new ArrayList<Overview>();
		for(Overview el : _Items)
		{
			if (el.getItemId() == itemId)
			{
				items.add(el);
			}
		}
		return items;
	}
	
	public void addUserOverview(long itemId, Double rating, String description)
	{
		Overview item = null;
		for(Overview el : _Items)
		{
			if (el.getItemId() == itemId && el.getIsCurrentUser())
			{
				item = el;
				break;
			}
		}
		if (item != null)
		{
			item.setDescription(description);
			item.setRating(rating);
		}
		else
		{
			item = new Overview();
			item.setDescription(description);
			item.setRating(rating);
			item.setIsCurrentUser(true);
			add(item, itemId);
		}
	}
	public Overview getUserOverview(long itemId)
	{
		Overview item = new Overview();
		for(Overview el : _Items)
		{
			if (el.getItemId() == itemId && el.getIsCurrentUser())
			{
				item = el;
				break;
			}
		}
		return item;
	}
	
	public void add(Overview item, long itemId)
	{
		item.setItemId(itemId);
		_Items.add(item);
	}
	
}
