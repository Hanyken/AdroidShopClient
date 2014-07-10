package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class CatalogItem
{
	public static final String PRICE_PROPERTY_NAME = "Price";
	public static final String IMAGES_PROPERTY_NAME = "Images";
	
	private long _rowNumber;
	private long _id;
	private String _name;
	private long _catalogId;
	private boolean _isLeaf;
	private int _childCount;
	private boolean _canBubble;
	private double _rating;
	private int _overviewsCount;
	private boolean _unsaleable;
	
	private String _description;
	private long _nodeId;
	
	private List<Overview> _overviews = new ArrayList<Overview>();
	private List<PropertyDescriptor> _properties = new ArrayList<PropertyDescriptor>();
	private List<PropertyDescriptor> _orderProperties = new ArrayList<PropertyDescriptor>();
	
	private List<CatalogItemGroup> _groups = new ArrayList<CatalogItemGroup>();

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
		if (_description == null || _description.equals(""))
			return "Нет описания";
		else
			return _description;
	}

	public void setDescription(String description)
	{
		_description = description;
	}
	
	public Collection<Overview> getOverviews()
	{
		return _overviews;
	}
	public void setOverviews(Collection<Overview> overviews)
	{
		_overviews.clear();
		_overviews.addAll(overviews);
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
	
	public Collection<PropertyDescriptor> getOrderProperties()
	{
		return _orderProperties;
	}
	public void setOrderProperties(Collection<PropertyDescriptor> orderProperties)
	{
		_orderProperties.clear();
		_orderProperties.addAll(orderProperties);
	}
	
	public Collection<CatalogItemGroup> getGroups()
	{
		return _groups;
	}
	public void setGroups(Collection<CatalogItemGroup> groups)
	{
		_groups.clear();
		_groups.addAll(groups);
	}
	
	public Double getPrice()
	{
		Double price = 0d;
		for(PropertyDescriptor prop : _properties)
		{
			if (prop.getName().equals(PRICE_PROPERTY_NAME) && (prop instanceof NumberPropertyDescriptor))
			{
				price = ((NumberPropertyDescriptor)prop).getCurrentMinValue();
				break;
			}
		}
		return price;
	}
	
	public Collection<String> getImages()
	{
		ArrayList<String> items = new ArrayList<String>();
		for(PropertyDescriptor prop : _properties)
		{
			if (prop.getName().equals(IMAGES_PROPERTY_NAME) && (prop instanceof EnumPropertyDescriptor))
			{
				for(EnumValue value : ((EnumPropertyDescriptor)prop).getValues())
					items.add(value.getValue());
				break;
			}
		}
		return items;
	}
	
	public String getIco()
	{
		String ico = null;
		for(PropertyDescriptor prop : _properties)
		{
			if (prop.getName().equals(IMAGES_PROPERTY_NAME) && (prop instanceof EnumPropertyDescriptor))
			{
				for(EnumValue value : ((EnumPropertyDescriptor)prop).getValues())
				{
					ico = value.getValue();
					break;
				}
				break;
			}
		}
		return ico;
	}
	
	public String getPropertyString()
	{
		StringBuilder sb = new StringBuilder();
		for(PropertyDescriptor prop : this.getProperties())
		{
			if (!prop.getName().equals(PRICE_PROPERTY_NAME) && !prop.getName().equals(IMAGES_PROPERTY_NAME))
				sb.append("\t"+prop.getTitle() + " - " + prop.getStringValue()+"\n");
		}
		return sb.toString();
	}
	
	
	
	public long getNodeId()
	{
		return _nodeId;
	}
	public void setNodeId(long nodeId)
	{
		_nodeId = nodeId;
	}
	
	public boolean getUnsaleable()
	{
		return _unsaleable;
	}
	public void setUnsaleable(boolean unsaleable)
	{
		_unsaleable = unsaleable;
	}
	
	public boolean webVewShow()
	{
		return false;
	}
	public String webVewText()
	{
		return null;
	}
}
