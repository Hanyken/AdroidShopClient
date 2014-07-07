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
	private CatalogSettings _catalogSettings;

	public CatalogManager()
	{
		_Items = new ArrayList<Catalog>();
	}

	public Catalog getCatalog()
	{
		if (_Items.size() == 0)
			return null;
		return _Items.get(0);
	}

	public void clearCatalog()
	{
		_Items.clear();
	}

	public CatalogSettings getSettings()
	{
		if (_catalogSettings != null)
			return _catalogSettings;
		else
		{
			CatalogSettings item = new CatalogSettings();
			item.setBackground(Color.rgb(117, 149, 179));
			item.setItemPanelColor(Color.rgb(235, 235, 235));
			item.setRatingColor(Color.rgb(255, 149, 0));
			item.setForegroundColor(Color.WHITE);
			item.setPressedColor(item.getRatingColor());
			item.setDisableColor(item.getItemPanelColor());
			// TODO: Загрузку картинок надо переделать т.к. они берутся пока что
			// из
			// ресурсов
			// item.setShareImg("Share.png");
			// item.setShareImgPress("SharePress.png");
			item.setCountButtonLableColor(Color.rgb(125, 125, 125));
			return item;
		}
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

	public CatalogNode getNodeById(long nodeId)
	{
		Catalog item = getCatalog();

		for (CatalogNode el : item.getNodes())
		{
			if (el.getCatalogId() == item.getId() && el.getId() == nodeId)
				return el;
		}

		return null;
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
