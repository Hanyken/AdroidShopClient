package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CatalogNode;

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
	
	public NodeParser()
	{
		
	}

	public CatalogNode getElement(Element e)
	{
		CatalogNode item = new CatalogNode();

		item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
		item.setId(Long.parseLong(super.getValue(e, ID_NAME)));
		item.setCatalogId(super.getValueLong(e, CATALOG_ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		item.setMajor(super.getValueBool(e, IS_MAJOR_NAME));
		item.setIcon(super.getValue(e, ICON_NAME));
		item.setGroupField(super.getValue(e, GROUP_FIELD_NAME));
		item.setCount(super.getValueInt(e, ITEMS_COUNT_NAME));
		item.setMaxPrice(super.getValueDouble(e, MAX_PRICE_NAME));
		item.setMinPrice(super.getValueDouble(e, MIN_PRICE_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		
		return item;
	}

	protected String getElementName()
	{
		return ITEM_NAME;
	}
	
	
}
