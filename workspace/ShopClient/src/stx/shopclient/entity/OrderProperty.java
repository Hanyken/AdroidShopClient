package stx.shopclient.entity;

public class OrderProperty 
{
	private long _orderId;
	private String _name;
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
	public String getValue()
	{
		return _value;
	}
	public void setValue(String value)
	{
		_value = value;
	}
}
