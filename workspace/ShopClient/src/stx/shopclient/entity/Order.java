package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Order 
{
	private long _Id;
	private long _itemId;
	private List<OrderProperty> _properties = new ArrayList<OrderProperty>();
	private Date _date;
	private CatalogItem _item;
	
	public long getId()
	{
		return _Id;
	}
	public void setId(long id)
	{
		_Id = id;
	}
	
	public long getItemId()
	{
		return _itemId;
	}
	public void setItemId(long itemId)
	{
		_itemId = itemId;
	}
	
	public List<OrderProperty> getProperties()
	{
		return _properties;
	}
	public void setProperties(Collection<OrderProperty> properties)
	{
		_properties.clear();
		_properties.addAll(properties);
	}
	
	public Date getDate()
	{
		return _date;
	}
	public void setDate(Date date)
	{
		_date = date;
	}
	
	public CatalogItem getItem()
	{
		return _item;
	}
	public void setItem(CatalogItem item)
	{
		_item = item;
	}
}
