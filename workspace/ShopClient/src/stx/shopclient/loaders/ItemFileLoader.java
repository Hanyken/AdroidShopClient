package stx.shopclient.loaders;

import java.util.Collection;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.parsers.ItemParser;
import stx.shopclient.repository.ItemsManager;

public class ItemFileLoader
{
	private final String DATA_PATH = "/sdcard/StxData/";

	private ItemsManager _ItemsManager;

	public ItemFileLoader(ItemsManager itemManager)
	{
		_ItemsManager = itemManager;
	}

	public void Load(long nodeId)
	{
		ItemParser cParser = new ItemParser();
		Collection<CatalogItem> items = cParser.parseFile(DATA_PATH + nodeId + ".txt");
		for (CatalogItem catalog : items)
		{
			_ItemsManager.add(catalog, nodeId);
		}
	}
}
