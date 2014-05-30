package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.entity.properties.PropertyDescriptor;

public class CatalogNode
{
	private int _rowNumber;
	private long _id;
	private Long _parentId;
	private long _catalogId;
	private String _name;
	private int _count;
	private boolean _isMajor;
	private String _icon;
	private String _description;
	private double _minPrice;
	private double _maxPrice;
	private String _groupField;
	
	private List<PropertyDescriptor> _properties = new ArrayList<PropertyDescriptor>();

	public int getRowNumber()
	{
		return _rowNumber;
	}

	public void setRowNumber(int rowNumber)
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

	public Long getParentId()
	{
		return _parentId;
	}

	public void setParentId(Long parentId)
	{
		_parentId = parentId;
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

	public int getCount()
	{
		return _count;
	}

	public void setCount(int count)
	{
		_count = count;
	}

	public boolean isMajor()
	{
		return _isMajor;
	}

	public void setMajor(boolean isMajor)
	{
		_isMajor = isMajor;
	}

	public String getIcon()
	{
		return _icon;
	}

	public void setIcon(String icon)
	{
		_icon = icon;
	}

	public String getDescription()
	{
		return _description;
	}

	public void setDescription(String description)
	{
		_description = description;
	}

	public double getMinPrice()
	{
		return _minPrice;
	}

	public void setMinPrice(double minPrice)
	{
		_minPrice = minPrice;
	}

	public double getMaxPrice()
	{
		return _maxPrice;
	}

	public void setMaxPrice(double maxPrice)
	{
		_maxPrice = maxPrice;
	}
	
	public String getGroupField()
	{
		return _groupField;
	}

	public void setGroupField(String groupField)
	{
		_groupField = groupField;
	}

	public Collection<PropertyDescriptor> getProperties()
	{
		return _properties;
	}

	public void setProperties(Collection<PropertyDescriptor> properties)
	{
		_properties.clear();
		_properties.addAll(properties);
	}
}
