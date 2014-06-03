package stx.shopclient.repository;

import stx.shopclient.loaders.CatalogFileLoader;


public class Repository
{
	private static Repository _intent;
	
	public static Repository getIntent()
	{
		if (_intent == null)
			_intent = new Repository();
		return _intent;
	}
	
	private ImagesManager _ImagesManager;
	private OrdersManager _OrderManager;
	private CatalogManager _CatalogManager;
	private OverviewsManager _OverviewsManager;
	private ItemsManager _ItemsManager;
	private MessagesManager _MessagesManager;
	
	private Repository()
	{
		_ImagesManager = new ImagesManager();
		_OrderManager = new OrdersManager();
		_OverviewsManager = new OverviewsManager();
		_ItemsManager = new ItemsManager(_OverviewsManager);
		_CatalogManager = new CatalogManager(_ItemsManager);
		_MessagesManager = new MessagesManager();
		
		CatalogFileLoader catalogLoader = new CatalogFileLoader(_CatalogManager);
		catalogLoader.Load();
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
}
