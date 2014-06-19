package stx.shopclient.parsers;

import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogItemGroup;
import stx.shopclient.entity.Overview;
import stx.shopclient.entity.properties.PropertyDescriptor;

public class ItemParser extends BaseParser<CatalogItem>
{
	private final String ITEM_NAME = "Item";
	private final String NUMBER_NAME = "RowNum";
	private final String ID_NAME = "Id";
	private final String NAME_NAME = "Name";
	private final String CATALOG_ID_NAME = "CatalogId";
	private final String IS_LEAF_NAME = "IsLeaf";
	private final String CHILD_COUNT_NAME = "ChildCount";
	private final String CAN_BUBBLE_NAME = "CanBubble";
	private final String RATING_NAME = "Rating";
	private final String OVERVIEW_COUNT_NAME = "OverviewsCount";
	private final String DESCRIPTION_NAME = "Description";
	
	private final String OVERVIEWS_NAME = "Overviews";
	private final String OVERVIEW_NAME = "Overview";
	private final String PROPERTIES_NAME = "Properties";
	private final String PROPERTY_NAME = "Property";
	private final String ORDER_PROPERTYES_NAME = "OrderProperties";
	private final String GROUPS_NAME = "Groups";
	
	
	
	public CatalogItem getElement(Element e)
	{
		CatalogItem item = new CatalogItem();

		item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
		item.setId(super.getValueLong(e, ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		item.setCatalogId(super.getValueLong(e, CATALOG_ID_NAME));
		item.setIsLeaf(super.getValueBool(e, IS_LEAF_NAME));
		item.setChildCount(super.getValueInt(e, CHILD_COUNT_NAME));
		item.setCanBubble(super.getValueBool(e, CAN_BUBBLE_NAME));
		item.setRating(super.getValueDouble(e, RATING_NAME));
		item.setOverviewsCount(super.getValueInt(e, OVERVIEW_COUNT_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		
		NodeList oNl = e.getElementsByTagName(OVERVIEWS_NAME);
		if (oNl.getLength() > 0)
		{
			NodeList itemsList = ((Element)oNl.item(0)).getElementsByTagName(OVERVIEW_NAME); 
			OverviewParser parser = new OverviewParser();
			Collection<Overview> items = parser.getElements(itemsList);
			item.setOverviews(items);
		}
		
		NodeList pNl = e.getElementsByTagName(PROPERTIES_NAME);
		if (pNl.getLength() > 0)
		{
			NodeList itemsList = ((Element)pNl.item(0)).getElementsByTagName(PROPERTY_NAME); 
			ItemPropertyParser parser = new ItemPropertyParser();
			Collection<PropertyDescriptor> items = parser.getElements(itemsList);
			item.setProperties(items);
		}
		
		NodeList opNl = e.getElementsByTagName(ORDER_PROPERTYES_NAME);
		if (opNl.getLength() > 0)
		{
			NodeList itemsList = ((Element)opNl.item(0)).getElementsByTagName(PROPERTY_NAME); 
			PropertyParser parser = new PropertyParser(false);
			Collection<PropertyDescriptor> items = parser.getElements(itemsList);
			item.setOrderProperties(items);
		}
		
		NodeList gpNl = e.getElementsByTagName(GROUPS_NAME);
		if (gpNl.getLength() > 0)
		{
			ItemGroupParser parser = new ItemGroupParser();
			Collection<CatalogItemGroup> items = parser.parseElement(((Element)gpNl.item(0)));
			item.setGroups(items);
		}
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
