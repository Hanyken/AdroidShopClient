package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Application;
import android.graphics.Color;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.loaders.ItemFileLoader;

public class CatalogManager
{
	private ArrayList<Catalog> _Items;
	private ItemsManager _ItemsManager;
	private CatalogSettings _catalogSettings;

	public CatalogManager(ItemsManager itemsManager)
	{
		_Items = new ArrayList<Catalog>();
		_ItemsManager = itemsManager;
	}

	public Catalog getCatalog()
	{
		if (_Items.size() == 0)
			return null;
		return _Items.get(0);
	}

	public CatalogSettings getSettings()
	{
		CatalogSettings item = new CatalogSettings();
		item.setBackground(Color.rgb(117, 149, 179));
		item.setItemPanelColor(Color.rgb(235, 235, 235));
		item.setRatingColor(Color.rgb(255, 149, 0));
		item.setForegroundColor(Color.WHITE);
		item.setPressedColor(item.getRatingColor());
		item.setDisableColor(item.getItemPanelColor());
		// TODO: Загрузку картинок надо переделать т.к. они берутся пока что из
		// ресурсов
		// item.setShareImg("Share.png");
		// item.setShareImgPress("SharePress.png");
		item.setCountButtonLableColor(Color.rgb(125, 125, 125));
		return item;
		//return _catalogSettings;
	}
	
	public void setSettings(CatalogSettings settings)
	{
		_catalogSettings = settings;
	}

	public Collection<CatalogNode> getNodes()
	{
		Catalog item = getCatalog();
		ArrayList<CatalogNode> items = new ArrayList<CatalogNode>();
		for (CatalogNode el : item.getNodes())
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
		for (CatalogNode el : item.getNodes())
		{
			if (el.getCatalogId() == item.getId() && el.getParentId() != null
					&& el.getParentId() == parentNodeId)
				items.add(el);
		}
		return items;
	}

	public void addCatalog(Catalog item)
	{
		_Items.clear();
		_Items.add(item);
		// ItemFileLoader loader = new ItemFileLoader(_ItemsManager);
		// for (CatalogNode node : item.getNodes())
		// {
		// loader.Load(node.getId());
		// }
	}
}
