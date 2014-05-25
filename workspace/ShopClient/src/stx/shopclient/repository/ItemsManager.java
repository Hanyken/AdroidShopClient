package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class ItemsManager
{
	private ArrayList<CatalogItem> _Items;
	private OverviewsManager _OverviewsManager;
	
	public ItemsManager(OverviewsManager overviewsManager)
	{
		_Items = new ArrayList<CatalogItem>();
		_OverviewsManager = overviewsManager;
		
		addItem(1, 1, 123, "Тестовый элемент");
	}
	
	public CatalogItem getItem(long itemId)
	{
		CatalogItem item = null;
		for(CatalogItem el : _Items)
		{
			if (el.getId() == itemId)
			{
				item = el;
				break;
			}
		}
		return item;
	}
	public Collection<CatalogItem> getItems(long nodeId)
	{
		ArrayList<CatalogItem> items = new ArrayList<CatalogItem>();
		for(CatalogItem el : _Items)
		{
			if (el.getNodeId() == nodeId)
			{
				items.add(el);
			}
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
	
	public void initItems(long nodeId, long catalogId)
	{		
		addItem(nodeId, catalogId, (nodeId * 10 + 1), "Тут каке то имя первого item'а");
		
		addItem(nodeId, catalogId, (nodeId * 10 + 2), "Тут каке то имя второго item'а");
		
		addItem(nodeId, catalogId, (nodeId * 10 + 2), "Тут каке то имя третьего item'а");
	}
	private void addItem(long nodeId, long catalogId, long itemId, String itemName)
	{
		_OverviewsManager.initOverviews(itemId);
		
		CatalogItem item = new CatalogItem();
		item.setId(itemId);
		item.setRowNumber(item.getId());
		item.setName(itemName);
		item.setCatalogId(catalogId);
		item.setIsLeaf(false);
		item.setChildCount(0);
		item.setCanBubble(false);
		
		item.setRating(_OverviewsManager.getAvgRating(itemId));
		item.setOverviewsCount(_OverviewsManager.getOverviewsCount(item.getId()));
		
		item.setNodeId(nodeId);
		
		_Items.add(item);
	}
	public double getMaxPrice(long nodeId)
	{
		return 0;
	}
	public double getMinPrice(long nodeId)
	{
		return 0;
	}
	public int getCountItems(long nodeId)
	{
		return 0;
	}
}
