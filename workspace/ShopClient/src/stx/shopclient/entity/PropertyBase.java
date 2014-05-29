package stx.shopclient.entity;

// Общий клас свойств
public class PropertyBase<T> extends KeyValue<T>
{
	private String _Type;
	private String _Title;
	private int _Order;
	
	

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

}
