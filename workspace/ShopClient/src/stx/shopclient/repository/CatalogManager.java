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
		addNode(id, null);
		
		id = 2l;
		addNode(id, null);
		
		id = 3l;
		addNode(id, null);
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
		return _Nodes;
	}

	
	
	
	
	
	private void addNode(long id, Long parentId)
	{
		_ItemsManager.initItems(id, _CatalogId);
		CatalogNode node = new CatalogNode();
		node.setCatalogId(_CatalogId);
		node.setName("Какое то имя");
		node.setDescription("Описание ноды");
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
