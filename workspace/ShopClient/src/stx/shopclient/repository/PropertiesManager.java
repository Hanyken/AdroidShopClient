package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import stx.shopclient.entity.CatalogItemProperty;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class PropertiesManager
{
	public static final String PRICE_NAME = "Price";
	
	Random rand = new Random();
	private ArrayList<CatalogItemProperty> _ItemProperties;
	
	public PropertiesManager()
	{
		_ItemProperties = new ArrayList<CatalogItemProperty>();
	}
	
	public Collection<CatalogItemProperty> getItemProperties(long itemId)
	{
		List<CatalogItemProperty> items = new ArrayList<CatalogItemProperty>();
		for(CatalogItemProperty el : _ItemProperties)
		{
			if (el.getItemId() == itemId)
				items.add(el);
		}
		return items;
	}
	
	public Collection<PropertyDescriptor> getOrderProperties(long itemId)
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
	
	
	public void initItemProperties(long itemId)
	{
		addItemProperty(itemId, PRICE_NAME, "Цена", String.valueOf(rand.nextInt(30000)));
	}
	
	private void addItemProperty(long itemId, String name, String title, String value)
	{
		CatalogItemProperty price = new CatalogItemProperty();
		price.setItemId(itemId);
		price.setName(name);
		price.setTitle(title);
		price.setOrder(1);
		price.setShortList(true);
		price.setType("Numeric");
		price.setValue(value);
		_ItemProperties.add(price);
	}
	public Double getItemPrice(long itemId)
	{
		double value = 0d;
		for(CatalogItemProperty el : _ItemProperties)
		{
			if (el.getItemId() == itemId && el.getName() == PRICE_NAME)
			{
				value = Double.parseDouble(el.getValue());
				break;
			}
		}
		
		return value;
	}
}
