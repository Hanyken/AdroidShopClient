package stx.shopclient.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import stx.shopclient.entity.AppSettings;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Token;
import stx.shopclient.loaders.CatalogFileLoader;
import stx.shopclient.parsers.CatalogParser;
import stx.shopclient.parsers.CatalogSettingParser;
import stx.shopclient.settings.ServerSettings;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.webservice.WebClient;

public class Repository
{
	static final String PREF_NAME = "Repository";
	static final String CATALOG_ID_PREF_KEY = "CatalogId";
	public static long CatalogId = 0;
	public static boolean _loadSettings = false;

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
		_CatalogManager = new CatalogManager();
		_MessagesManager = new MessagesManager();
	}

	public static void loadSelectedCatalogId(Context context)
	{
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		if (pref != null)
		{
			CatalogId = pref.getLong(CATALOG_ID_PREF_KEY, CatalogId);
		}
	}

	public static void saveSelectedCatalogId(Context context)
	{
		SharedPreferences pref = context.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = pref.edit();
		editor.putLong(CATALOG_ID_PREF_KEY, CatalogId);
		editor.commit();
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
		// String settingsFile = "settings_" + Long.toString(CatalogId);
		File file = context.getFileStreamPath(catalogFile);
		// File settingsfile = context.getFileStreamPath(settingsFile);

		CatalogParser parser = new CatalogParser();
		Collection<Catalog> catalogs = parser.parseFile(file.getAbsolutePath());
		if (catalogs.size() == 0)
			throw new RuntimeException("no catalogs load from file");
		else
		{
			Catalog catalog = catalogs.iterator().next();
			_CatalogManager.addCatalog(catalog);
		}
		/*
		 * if (settingsfile.exists()) { CatalogSettingParser settingsParser =
		 * new CatalogSettingParser(); Collection<CatalogSettings> settings =
		 * settingsParser.parseFile(settingsfile.getAbsolutePath()); if
		 * (settings.size() == 0) throw new
		 * RuntimeException("no catalogSettings load from file"); else {
		 * CatalogSettings setting = settings.iterator().next();
		 * _CatalogManager.setSettings(setting); setImagesFromSettings(setting,
		 * new WebClient(context)); } }
		 */
		loadSettingsFromWeb(context, new WebClient(context));
	}

	public void loadCatalogFromWeb(Context context)
	{
		String catalogFile = "catalog_" + Long.toString(CatalogId);

		WebClient client = new WebClient(context);
		StringBuilder xml = new StringBuilder();
		Catalog catalog = client.getCatalog(Token.getCurrent(), CatalogId, xml);

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

		loadSettingsFromWeb(context, client);
	}

	private void loadSettingsFromWeb(Context context, WebClient client)
	{
		String settingsFile = "settings_" + Long.toString(CatalogId);

		StringBuilder settingsXml = new StringBuilder();
		CatalogSettings settings = client.getCatalogSettings(
				Token.getCurrent(), CatalogId, settingsXml);

		FileOutputStream stream = null;
		OutputStreamWriter writer = null;
		try
		{
			stream = context.openFileOutput(settingsFile, 0);
			writer = new OutputStreamWriter(stream);
			writer.write(settingsXml.toString());
			writer.close();
			stream.close();

			_CatalogManager.setSettings(settings);
			if (settings != null)
				setImagesFromSettings(settings, client);
		}
		catch (Exception ex)
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

	private void setImagesFromSettings(CatalogSettings settings,
			WebClient client)
	{
		settings.setCommentImg(client.getImage(Token.getCurrent(),
				settings.getCommentImgKey()));
		settings.setCommentImgPress(client.getImage(Token.getCurrent(),
				settings.getCommentImgPressKey()));
		settings.setFavoriteImg(client.getImage(Token.getCurrent(),
				settings.getFavoriteImgKey()));
		settings.setFavoriteImgPress(client.getImage(Token.getCurrent(),
				settings.getFavoriteImgPressKey()));
		settings.setShareImg(client.getImage(Token.getCurrent(),
				settings.getShareImgKey()));
		settings.setShareImgPress(client.getImage(Token.getCurrent(),
				settings.getShareImgPressKey()));
	}

	public void loadCatalog(Context context)
	{
		if (!_loadSettings && updateAppSettings(new WebClient(context)))
		{
			_loadSettings =  true;
			ServerSettings.save(context);
			saveSelectedCatalogId(context);
		}
		
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
	
	private boolean updateAppSettings(WebClient client)
	{
		AppSettings settings = client.getAppSettings(Token.getCurrent(), null);
		if (settings == null) return false;
		ServerSettings.setUrl(settings.getFirstServerUri());
		ServerSettings.setUrlReserve(settings.getLastServerUri());
		if (CatalogId == 0)
		{
			Repository.CatalogId = settings.getDefaultCatalog();
		}
		
		return true;
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
