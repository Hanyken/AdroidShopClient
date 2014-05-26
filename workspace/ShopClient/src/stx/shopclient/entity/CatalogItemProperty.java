package stx.shopclient.entity;

public class CatalogItemProperty
{
	private long _ItemId;
	private String _Name;
	private String _Type;
	private String _Title;
	private boolean _ShortList;
	private int _Order;
	private String _Value;
	
	public long getItemId()
	{
		return _ItemId;
	}
	public void setItemId(long itemId)
	{
		_ItemId = itemId;
	}
	
	public String getName()
	{
		return _Name;
	}
	public void setName(String name)
	{
		_Name = name;
	}
	
	public String getType()
	{
		return _Type;
	}
	public void setType(String type)
	{
		_Type = type;
	}
	
	public String getTitle()
	{
		return _Title;
	}
	public void setTitle(String title)
	{
		_Title = title;
	}
	
	public boolean getShortList()
	{
		return _ShortList;
	}
	public void setShortList(boolean shortList)
	{
		_ShortList = shortList;
	}
	
	public int getOrder()
	{
		return _Order;
	}
	public void setOrder(int order)
	{
		_Order = order;
	}
	
	public String getValue()
	{
		return _Value;
	}
	public void setValue(String value)
	{
		_Value = value;
	}
	
}
