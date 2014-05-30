package stx.shopclient.parsers;

import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class NodeParser extends BaseParser<CatalogNode>
{
	public String TEST;
	private final String ITEM_NAME = "Node";
	private final String NUMBER_NAME = "RowNum";
	private final String ID_NAME = "Id";
	private final String CATALOG_ID_NAME = "CatalogId";
	private final String NAME_NAME = "Name";
	private final String IS_MAJOR_NAME = "IsMajor";
	private final String ICON_NAME = "Icon";
	private final String GROUP_FIELD_NAME = "GroupField";
	private final String ITEMS_COUNT_NAME = "ItemsCount";
	private final String MAX_PRICE_NAME = "MaxPrice";
	private final String MIN_PRICE_NAME = "MinPrice";
	private final String DESCRIPTION_NAME = "Description";
	private final String SEARCH_PROPERTIES_NAME = "SearchProperties";
	private final String PROPERTY_NAME = "Property";
	
	public NodeParser()
	{
		
	}

	public CatalogNode getElement(Element e)
	{
		CatalogNode item = new CatalogNode();

		item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
		item.setId(super.getValueLong(e, ID_NAME));
		item.setCatalogId(super.getValueLong(e, CATALOG_ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		item.setMajor(super.getValueBool(e, IS_MAJOR_NAME));
		item.setIcon(super.getValue(e, ICON_NAME));
		item.setGroupField(super.getValue(e, GROUP_FIELD_NAME));
		item.setCount(super.getValueInt(e, ITEMS_COUNT_NAME));
		item.setMaxPrice(super.getValueDouble(e, MAX_PRICE_NAME));
		item.setMinPrice(super.getValueDouble(e, MIN_PRICE_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		
		NodeList nl = e.getElementsByTagName(SEARCH_PROPERTIES_NAME);
		if (nl.getLength() > 0)
		{
			NodeList propertiesList = ((Element)nl.item(0)).getElementsByTagName(PROPERTY_NAME); 
			PropertyParser parser = new PropertyParser();
			Collection<PropertyDescriptor> properties = parser.getElements(propertiesList);
			item.setProperties(properties);
		}
		
		return item;
	}

	protected String getElementName()
	{
		return ITEM_NAME;
	}
	
	
}
