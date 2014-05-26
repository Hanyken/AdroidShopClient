package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.CatalogItem;

public class ItemsManager
{
	private ArrayList<CatalogItem> _Items;
	private OverviewsManager _OverviewsManager;
	private PropertiesManager _PropertiesManager;
	
	public ItemsManager(OverviewsManager overviewsManager, PropertiesManager propertiesManager)
	{
		_Items = new ArrayList<CatalogItem>();
		_OverviewsManager = overviewsManager;
		_PropertiesManager = propertiesManager;
		
		addItem(0, 0, 123, "Тестовый элемент");
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
	
	public void initItems(long nodeId, long catalogId)
	{		
		addItem(nodeId, catalogId, (nodeId * 10 + 1), "Тут каке то имя первого item'а");
		
		addItem(nodeId, catalogId, (nodeId * 10 + 2), "Тут каке то имя второго item'а");
		
		addItem(nodeId, catalogId, (nodeId * 10 + 2), "Тут каке то имя третьего item'а");
	}
	private void addItem(long nodeId, long catalogId, long itemId, String itemName)
	{
		_OverviewsManager.initOverviews(itemId);
		_PropertiesManager.initItemProperties(itemId);
		
		CatalogItem item = new CatalogItem();
		item.setId(itemId);
		item.setRowNumber(item.getId());
		item.setName(itemName);
		item.setCatalogId(catalogId);
		item.setIsLeaf(false);
		item.setChildCount(0);
		item.setCanBubble(false);
		item.setDescription(itemName);//-------
		item.setRating(_OverviewsManager.getAvgRating(itemId));
		item.setOverviewsCount(_OverviewsManager.getOverviewsCount(item.getId()));
		
		item.setNodeId(nodeId);
		
		_Items.add(item);
	}
	public double getMaxPrice(long nodeId)
	{
		double value = Double.MIN_VALUE;
		Collection<CatalogItem> items = getItems(nodeId);
		for(CatalogItem el : items)
		{
			double tmpValue = _PropertiesManager.getItemPrice(el.getId());
			if (tmpValue > value)
				value = tmpValue;
		}
		return Math.round(value);
	}
	public double getMinPrice(long nodeId)
	{
		double value = Double.MAX_VALUE;
		Collection<CatalogItem> items = getItems(nodeId);
		for(CatalogItem el : items)
		{
			double tmpValue = _PropertiesManager.getItemPrice(el.getId());
			if (tmpValue < value)
				value = tmpValue;
		}
		return Math.round(value);
	}
	public int getCountItems(long nodeId)
	{
		return getItems(nodeId).size();
	}
}
