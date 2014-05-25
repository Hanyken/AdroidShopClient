package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Random;

import stx.shopclient.entity.Overview;;

public class OverviewsManager
{
	private ArrayList<Overview> _Items;
	
	public OverviewsManager()
	{
		_Items = new ArrayList<Overview>();
	}
	
	
	public void initOverviews(long itemId)
	{
		Random rand = new Random();
		
		addOverview(rand.nextDouble() * 5, "Тут какой то коментарий №1");
		addOverview(rand.nextDouble() * 5, "Тут какой то коментарий №2");
		addOverview(rand.nextDouble() * 5, "Тут какой то коментарий №3");
		addOverview(rand.nextDouble() * 5, "Тут какой то коментарий №4");
		addOverview(rand.nextDouble() * 5, "Тут какой то коментарий №5");
	}
	private void addOverview(double rating, String description)
	{
		Overview overview = new Overview();
		overview.setRating(rating);
		overview.setDescription(description);
		_Items.add(overview);
	}
	public int getOverviewsCount(long itemId)
	{
		return _Items.size();
	}
	public double getAvgRating(long itemId)
	{
		double value = 0;
		for(Overview el : _Items)
			value += el.getRating();
		
		return value / _Items.size();
	}
}
