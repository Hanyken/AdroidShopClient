package stx.shopclient.entity;

public class AnalogGroup
{
	private String _name;
	private long[] _ids;
	
	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		_name = name;
	}
	
	public long[] getIds()
	{
		return _ids;
	}
	public void setIds(long[] ids)
	{
		_ids = ids;
	}
}
