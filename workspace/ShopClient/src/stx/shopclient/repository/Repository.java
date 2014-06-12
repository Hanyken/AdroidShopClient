package stx.shopclient.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.Token;
import stx.shopclient.loaders.CatalogFileLoader;
import stx.shopclient.parsers.CatalogParser;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.webservice.WebClient;

public class Repository
{
	public static long CatalogId = 1;

	private static Repository _intent;

	public static Repository get(Context context)
	{
		if (_intent == null)
			_intent = new Repository(context);
		return _intent;
	}

	private ImagesManager _ImagesManager;
	private OrdersManager _OrderManager;
	private CatalogManager _CatalogManager;
	private OverviewsManager _OverviewsManager;
	private ItemsManager _ItemsManager;
	private MessagesManager _MessagesManager;

	private Repository(Context context)
	{
		_ImagesManager = new ImagesManager();
		_OrderManager = new OrdersManager();
		_OverviewsManager = new OverviewsManager();
		_ItemsManager = new ItemsManager(_OverviewsManager);
		_CatalogManager = new CatalogManager(_ItemsManager);
		_MessagesManager = new MessagesManager();

		// CatalogFileLoader catalogLoader = new
		// CatalogFileLoader(_CatalogManager);
		// catalogLoader.Load();
		/*
		 * CatalogLoad load = new CatalogLoad(); load._Context = context;
		 * load._Manager = _CatalogManager; load.execute();
		 */
	}

	// ----------Catalog---------
	public CatalogManager getCatalogManager()
	{
		return _CatalogManager;
	}

	public ItemsManager getItemsManager()
	{
		return _ItemsManager;
	}

	public OrdersManager getOrderManager()
	{
		return _OrderManager;
	}

	public ImagesManager getImagesManager()
	{
		return _ImagesManager;
	}

	public OverviewsManager getOverviewsManager()
	{
		return _OverviewsManager;
	}

	public MessagesManager getMessagesManager()
	{
		return _MessagesManager;
	}

	private Token getToken(Context context)
	{
		if (Token.getCurrent() == null)
		{
			WebClient client = new WebClient(context);
			DisplayMetrics displayMetrics = context.getResources()
					.getDisplayMetrics();
			Token.setCurrent(client.login("admin", "123",
					displayMetrics.widthPixels, displayMetrics.heightPixels));
		}
		return Token.getCurrent();
	}
	
	public void loadCatalogFromFile(Context context)
	{
		String catalogFile = "catalog_" + Long.toString(CatalogId);
		File file = context.getFileStreamPath(catalogFile);
		
		CatalogParser parser = new CatalogParser();
		Collection<Catalog> catalogs = parser.parseFile(file.getAbsolutePath());
		if(catalogs.size()==0)
			throw new RuntimeException("no catalogs load from file");
		else
		{
			Catalog catalog = catalogs.iterator().next();
			_CatalogManager.addCatalog(catalog);
		}
	}
	
	public void loadCatalogFromWeb(Context context)
	{
		String catalogFile = "catalog_" + Long.toString(CatalogId);
		
		WebClient client = new WebClient(context);
		StringBuilder xml = new StringBuilder();
		Catalog catalog = client.getCatalog(Token.getCurrent(), CatalogId,
				xml);

		FileOutputStream stream = null;
		OutputStreamWriter writer = null;
		try
		{
			stream = context.openFileOutput(catalogFile, 0);
			writer = new OutputStreamWriter(stream);
			writer.write(xml.toString());
			writer.close();
			stream.close();
			
			_CatalogManager.addCatalog(catalog);
		}
		catch (Throwable ex)
		{
			throw new RuntimeException(ex);
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
				if (stream != null)
					stream.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void loadCatalog(Context context)
	{
		String catalogFile = "catalog_" + Long.toString(CatalogId);
		File file = context.getFileStreamPath(catalogFile);
		if (file.exists())
		{
			loadCatalogFromFile(context);
		}
		else
		{
			loadCatalogFromWeb(context);
		}
	}

	class CatalogLoad extends AsyncTask<Void, Void, Void>
	{
		public Context _Context;
		public CatalogManager _Manager;

		@Override
		protected Void doInBackground(Void... arg0)
		{
			WebClient client = new WebClient(_Context);
			// Catalog cat = client.getCatalog(getToken(_Context), CatalogId);
			client.getNodeItems(getToken(_Context), 1, 0, 10, 0);

			return null;
		}
	}

}
