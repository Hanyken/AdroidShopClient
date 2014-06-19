package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Catalog
{
	private long _id;
	private String _name;
	private String _description;
	private String _logo;
	private Date _lastModification;
	private int _nodeCount;
	private List<CatalogNode> _nodes = new ArrayList<CatalogNode>();

	
	
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
	
	public String getLogo()
	{
		return _logo;
	}

	public void setLogo(String logo)
	{
		_logo = logo;
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
	
	public Collection<CatalogNode> getNodes()
	{
		return _nodes;
	}

	public void setNodes(Collection<CatalogNode> nodes)
	{
		_nodes.clear();
		_nodes.addAll(nodes);
	}
}
