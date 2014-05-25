package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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
	
	public void initOverviews(long itemId)
	{
		Random rand = new Random();
		
		addOverview(itemId, rand.nextDouble() * 5, "Тут какой то коментарий №1");
		addOverview(itemId, rand.nextDouble() * 5, "Тут какой то коментарий №2");
		addOverview(itemId, rand.nextDouble() * 5, "Тут какой то коментарий №3");
		addOverview(itemId, rand.nextDouble() * 5, "Тут какой то коментарий №4");
		addOverview(itemId, rand.nextDouble() * 5, "Тут какой то коментарий №5");
	}
	private void addOverview(long itemId, double rating, String description)
	{
		Overview overview = new Overview();
		overview.setItemId(itemId);
		overview.setRating(rating);
		overview.setDescription(description);
		_Items.add(overview);
	}
	public int getOverviewsCount(long itemId)
	{
		return getOverviews(itemId).size();
	}
	public double getAvgRating(long itemId)
	{
		double value = 0;
		for(Overview el : getOverviews(itemId))
			value += el.getRating();
		
		return value / _Items.size();
	}
}
