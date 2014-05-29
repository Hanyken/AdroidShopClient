package stx.shopclient.entity;

public class CatalogItemProperty extends PropertyBase<String>
{
	private long _ItemId;
	private boolean _ShortList;
	
	public long getItemId()
	{
		return _ItemId;
	}
	public void setItemId(long itemId)
	{
		_ItemId = itemId;
	}
	
	public boolean getShortList()
	{
		return _ShortList;
	}
	public void setShortList(boolean shortList)
	{
		_ShortList = shortList;
	}
}
