package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.loaders.ItemFileLoader;

public class CatalogManager
{
	private ArrayList<Catalog> _Items;
	private ItemsManager _ItemsManager;
	
	public CatalogManager(ItemsManager itemsManager)
	{
		_Items = new ArrayList<Catalog>();
		_ItemsManager = itemsManager;
	}
	
	public Catalog getCatalog()
	{
		return _Items.get(0);
	}
	
	public Collection<CatalogNode> getNodes()
	{
		Catalog item = getCatalog();
		ArrayList<CatalogNode> items = new ArrayList<CatalogNode>();
		for(CatalogNode el : item.getNodes())
		{
			if (el.getCatalogId() == item.getId() && el.getParentId() == null)
				items.add(el);
		}
		return items;
	}
	
	public Collection<CatalogNode> getNodes(long parentNodeId)
	{
		Catalog item = getCatalog();
		ArrayList<CatalogNode> items = new ArrayList<CatalogNode>();
		for(CatalogNode el : item.getNodes())
		{
			if (el.getCatalogId() == item.getId() && el.getParentId() != null && el.getParentId() == parentNodeId)
				items.add(el);
		}
		return items;
	}
	
	
	public void addCatalog(Catalog item)
	{
		_Items.add(item);
		ItemFileLoader loader = new ItemFileLoader(_ItemsManager);
		for(CatalogNode node : item.getNodes())
		{
			loader.Load(node.getId());
		}
	}
}
