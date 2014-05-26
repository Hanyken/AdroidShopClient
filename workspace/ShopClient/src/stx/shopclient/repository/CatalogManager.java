package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogNode;

public class CatalogManager
{
	private Long _CatalogId = 1l;
	private String _CatalogName = "Самый лучший магазин";
	private ArrayList<CatalogNode> _Nodes;
	
	private ItemsManager _ItemsManager;
	
	public CatalogManager(ItemsManager itemsManager)
	{
		_Nodes = new ArrayList<CatalogNode>();
		_ItemsManager = itemsManager;
		
		long id = 1l;
		addNode(id, null, "Какое то имя каталога №1");
		
		id = 2l;
		addNode(id, null, "Какое то имя каталога №2");
		
		id = 3l;
		addNode(id, null, "Какое то имя каталога №3");
		
		id = 10l;
		addNode(id, 1l, "Какое то имя подкаталога №1 каталога №1");
		
		id = 20l;
		addNode(id, 2l, "Какое то имя подкаталога №1 каталога №2");
	}
	
	public Catalog getCatalog()
	{
		Catalog catalog = new Catalog();
		catalog.setId(_CatalogId);
		catalog.setName(_CatalogName);
		catalog.setNodeCount(_Nodes.size());
		return catalog;
	}
	
	public Collection<CatalogNode> getNodes()
	{
		ArrayList<CatalogNode> items = new ArrayList<CatalogNode>();
		for(CatalogNode el : _Nodes)
		{
			if (el.getCatalogId() == _CatalogId && el.getParentId() == null)
				items.add(el);
		}
		return items;
	}
	
	public Collection<CatalogNode> getNodes(long parentNodeId)
	{
		ArrayList<CatalogNode> items = new ArrayList<CatalogNode>();
		for(CatalogNode el : _Nodes)
		{
			if (el.getCatalogId() == _CatalogId && el.getParentId() != null && el.getParentId() == parentNodeId)
				items.add(el);
		}
		return items;
	}

	
	
	
	
	
	private void addNode(long id, Long parentId, String name)
	{
		_ItemsManager.initItems(id, _CatalogId);
		CatalogNode node = new CatalogNode();
		node.setCatalogId(_CatalogId);
		node.setName(name);
		node.setDescription("Описание ноды: "+name);
		node.setIcon("node1_img1");
		node.setId(id);
		node.setMajor(false);
		node.setMaxPrice(_ItemsManager.getMaxPrice(id));
		node.setMinPrice(_ItemsManager.getMinPrice(id));
		node.setParentId(parentId);
		node.setCount(_ItemsManager.getCountItems(id));
		_Nodes.add(node);
	}
}
