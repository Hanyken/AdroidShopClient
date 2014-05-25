package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.CatalogItem;

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
