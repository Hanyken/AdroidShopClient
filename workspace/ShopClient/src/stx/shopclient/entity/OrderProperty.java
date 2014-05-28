package stx.shopclient.entity;

public class OrderProperty 
{
	private long _orderId;
	private String _name;
	private String _Type;
	private String _Title;
	private int _Order;
	private String _value;
	
	
	public long getOrderId()
	{
		return _orderId;
	}
	public void setOrderId(long orderId)
	{
		_orderId = orderId;
	}
	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		_name = name;
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
		return _value;
	}
	public void setValue(String value)
	{
		_value = value;
	}
}
