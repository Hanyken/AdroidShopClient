package stx.shopclient.repository;

import java.util.Random;

import stx.shopclient.entity.CatalogItem;

public class Repository
{
	public static CatalogItem getCatalogItem(String itemId, String name)
	{
		CatalogItem item = new CatalogItem();
		item.setName(name);
		Random rand = new Random();
		item.setPrice(rand.nextFloat() * 30000);
		item.setRating(rand.nextFloat() * 5);
		item.setOverviewsCount(rand.nextInt(256));
		//item.setRepostCount(rand.nextInt(512));
		item.setDescription("Это самый лучший тавар на планете");
		
		return item;
	}
	
	public static String[] getImages(long itemId)
	{
		return new String[] { "1", "2", "3", "4" };
	}
}
