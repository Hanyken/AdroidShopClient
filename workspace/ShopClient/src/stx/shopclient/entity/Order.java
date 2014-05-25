package stx.shopclient.entity;

public class Order 
{
	private long _Id;
	private long _itemId;
	
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
}
