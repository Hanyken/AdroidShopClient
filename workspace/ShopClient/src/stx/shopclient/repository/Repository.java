package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class Repository
{
	private static Repository _intent;
	
	public static Repository getIntent()
	{
		if (_intent == null)
			_intent = new Repository();
		return _intent;
	}
	
	private CatalogManager _CatalogManager;
	private OverviewsManager _OverviewsManager;
	private ItemsManager _ItemsManager;
	private PropertiesManager _PropertiesManager;
	
	private Repository()
	{
		_PropertiesManager = new PropertiesManager();
		_OverviewsManager = new OverviewsManager();
		_ItemsManager = new ItemsManager(_OverviewsManager);
		_CatalogManager = new CatalogManager(_ItemsManager);
	}
	
	
	//----------Catalog---------
	public CatalogManager getCatalogManager()
	{
		return _CatalogManager; 
	}
	public ItemsManager getItemsManager()
	{
		return _ItemsManager;
	}
	public PropertiesManager getPropertiesManager()
	{
		return _PropertiesManager;
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
