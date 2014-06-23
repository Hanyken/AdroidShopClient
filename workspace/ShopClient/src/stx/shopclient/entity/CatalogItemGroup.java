package stx.shopclient.entity;

public class CatalogItemGroup
{
	private long _id;
	private String _name;

	public long getId()
	{
		return _id;

	}

	public void setId(long id)
	{
		_id = id;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof CatalogItemGroup)
		{
			return ((CatalogItemGroup) o).getId() == _id;
		}
		else
			return false;
	}

	@Override
	public int hashCode()
	{
		return Long.valueOf(_id).hashCode();
	}
}
