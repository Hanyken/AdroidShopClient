package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

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
	
	
	public static Collection<PropertyDescriptor> getOrderProperties(long itemId)
	{
		List<PropertyDescriptor> items = new ArrayList<PropertyDescriptor>();
		NumberPropertyDescriptor prop = new NumberPropertyDescriptor();
		prop.setName("Count");
		prop.setTitle("Количество");
		prop.setMinValue(1);
		prop.setMaxValue(99999);
		prop.setFloat(false);
		prop.setRange(false);
		prop.setCurrentMinValue(1);
		prop.setCurrentValueDefined(true);
		items.add(prop);

		DatePropertyDescriptor prop1 = new DatePropertyDescriptor();
		prop1.setName("asd");
		prop1.setTitle("Дата");
		prop1.setMinValue(new GregorianCalendar(1997, 1, 1));
		prop1.setMaxValue(new GregorianCalendar(2020, 1, 1));
		prop1.setCurrentMinValue(new GregorianCalendar(2015, 1, 1));
		prop1.setCurrentValueDefined(true);
		prop1.setRange(false);
		items.add(prop1);
		
		return items;
	}
	public static void addOrderItem(long itemId, Collection<PropertyDescriptor> properties)
	{
		
	}
}
