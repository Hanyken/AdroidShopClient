package stx.shopclient.entity;

import java.util.Date;

public class Catalog
{
	private long _id;
	private String _name;
	private String _description;
	private Date _lastModification;
	private int _nodeCount;

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

	public String getDescription()
	{
		return _description;
	}

	public void setDescription(String description)
	{
		_description = description;
	}
	
	public Date getLastModification()
	{
		return _lastModification;
	}

	public void setLastModification(Date lastModification)
	{
		_lastModification = lastModification;
	}

	public int getNodeCount()
	{
		return _nodeCount;
	}

	public void setNodeCount(int nodeCount)
	{
		_nodeCount = nodeCount;
	}
}
