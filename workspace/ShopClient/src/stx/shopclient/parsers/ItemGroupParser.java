package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CatalogItemGroup;

public class ItemGroupParser extends BaseParser<CatalogItemGroup>
{
	private final String ITEM_NAME = "Group";
	private final String ID_NAME = "Id";
	private final String NAME_NAME = "Name";
	

	@Override
	public CatalogItemGroup getElement(Element e)
	{
		CatalogItemGroup item = new CatalogItemGroup();
		item.setId(super.getValueLong(e, ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
