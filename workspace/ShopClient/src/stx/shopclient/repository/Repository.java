package stx.shopclient.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.Token;
import stx.shopclient.loaders.CatalogFileLoader;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.webservice.WebClient;


public class Repository
{
	public static long CatalogId = 1;
	
	private static Repository _intent;
	
	public static Repository getIntent(Context context)
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
		
		CatalogFileLoader catalogLoader = new CatalogFileLoader(_CatalogManager);
		catalogLoader.Load();
		/*
		CatalogLoad load = new CatalogLoad();
		load._Context = context;
		load._Manager = _CatalogManager;
		load.execute();
		*/
	}
	
	
	//----------Catalog---------
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
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			Token.setCurrent(client.login("admin", "123", displayMetrics.widthPixels, displayMetrics.heightPixels));
		}
		return Token.getCurrent();
	}
	
	
	class CatalogLoad extends AsyncTask<Void, Void, Void>
	{
		public Context _Context;
		public CatalogManager _Manager;

		@Override
		protected Void doInBackground(Void... arg0)
		{
			WebClient client = new WebClient(_Context);
			//Catalog cat = client.getCatalog(getToken(_Context), CatalogId);
			client.getNodeItems(getToken(_Context), 1, 0, 10, 0);
			
			return null;
		}
	}
	
}
