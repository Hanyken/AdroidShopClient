package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Overview;

public class ItemsManager
{
	private ArrayList<CatalogItem> _Items;
	private OverviewsManager _OverviewsManager;
	
	public ItemsManager(OverviewsManager overviewsManager)
	{
		_Items = new ArrayList<CatalogItem>();
		_OverviewsManager = overviewsManager;
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
	
	public Collection<CatalogItem> getFavorits()
	{
		return _Items;
	}
	
	
	
	
	public void add(CatalogItem item, long nodeId)
	{
		item.setNodeId(nodeId);
		_Items.add(item);
		for(Overview el : item.getOverviews())
		{
			_OverviewsManager.add(el, item.getId());
		}
	}

}
