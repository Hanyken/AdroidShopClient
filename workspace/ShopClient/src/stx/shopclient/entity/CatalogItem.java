package stx.shopclient.entity;

public class CatalogItem
{
	private long _rowNumber;
	private long _id;
	private String _name;
	private long _catalogId;
	private boolean _isLeaf;
	private int _childCount;
	private boolean _canBubble;
	private double _rating;
	private int _overviewsCount;
	
	private String _description;
	private long _nodeId;

	public long getRowNumber()
	{
		return _rowNumber;
	}
	public void setRowNumber(long rowNumber)
	{
		_rowNumber = rowNumber;
	}
	
	public long getId()
	{
		return _id;
	}
	public void setId(long id)
	{
		_id = id;
	}

	public long getCatalogId()
	{
		return _catalogId;
	}
	public void setCatalogId(long catalogId)
	{
		_catalogId = catalogId;
	}

	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		_name = name;
	}

	public boolean getIsLeaf()
	{
		return _isLeaf;
	}
	public void setIsLeaf(boolean isLeaf)
	{
		_isLeaf = isLeaf;
	}

	public int getChildCount()
	{
		return _childCount;
	}
	public void setChildCount(int childCount)
	{
		_childCount = childCount;
	}
	
	public boolean getCanBubble()
	{
		return _canBubble;
	}
	public void setCanBubble(boolean canBubble)
	{
		_canBubble = canBubble;
	}
	
	public double getRating()
	{
		return _rating;
	}

	public void setRating(double rating)
	{
		_rating = rating;
	}

	public int getOverviewsCount()
	{
		return _overviewsCount;
	}

	public void setOverviewsCount(int overviewsCount)
	{
		_overviewsCount = overviewsCount;
	}

	
	

	public String getDescription()
	{
		return _description;
	}

	public void setDescription(String description)
	{
		_description = description;
	}
	
	public long getNodeId()
	{
		return _nodeId;
	}
	public void setNodeId(long nodeId)
	{
		_nodeId = nodeId;
	}
}
