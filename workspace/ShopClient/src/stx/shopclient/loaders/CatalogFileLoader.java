package stx.shopclient.loaders;

import java.util.Collection;

import stx.shopclient.entity.Catalog;
import stx.shopclient.parsers.CatalogParser;
import stx.shopclient.repository.CatalogManager;

public class CatalogFileLoader
{
	private final String DATA_PATH = "/sdcard/StxData/";

	private CatalogManager _CatalogManager;

	public CatalogFileLoader(CatalogManager catalogManager)
	{
		_CatalogManager = catalogManager;
	}

	public void Load()
	{
		CatalogParser cParser = new CatalogParser();
		Collection<Catalog> catalogs = cParser.parseFile(DATA_PATH + "Catalog.txt");
		for (Catalog catalog : catalogs)
		{
			_CatalogManager.addCatalog(catalog);
		}
	}
}
