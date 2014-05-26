package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

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
	private PropertiesManager _PropertiesManager;
	
	private Repository()
	{
		_ImagesManager = new ImagesManager();
		_OrderManager = new OrdersManager();
		_PropertiesManager = new PropertiesManager();
		_OverviewsManager = new OverviewsManager();
		_ItemsManager = new ItemsManager(_OverviewsManager, _PropertiesManager);
		_CatalogManager = new CatalogManager(_ItemsManager);
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
	public PropertiesManager getPropertiesManager()
	{
		return _PropertiesManager;
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
	
}
