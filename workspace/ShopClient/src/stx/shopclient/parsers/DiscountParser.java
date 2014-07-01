package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.Discount;

public class DiscountParser extends BaseParser<Discount>
{
	private static final String ITEM_NAME = "Discount";
	private static final String NAME_NAME = "Name";
	private static final String DESCRIPTION_NAME = "Description";
	private static final String CODE_NAME = "Code";
	private static final String SIZE_NAME = "Size";
	private static final String UNIT_NAME = "Unit";
	private static final String CREATE_DATE_NAME = "CreateDate";
	private static final String CATALOG_ID_NAME = "CatalogId";
	private static final String CATALOG_NAME_NAME = "CatalogName";
	private static final String CATALOG_LOGO_NAME = "CatalogLogo";
	private static final String IMAGE_NAME = "Image";

	@Override
	public Discount getElement(Element e)
	{
		Discount item = new Discount();

		item.setName(super.getValue(e, NAME_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		item.setCode(super.getValue(e, CODE_NAME));
		item.setSize(super.getValueDouble(e, SIZE_NAME));
		item.setUnit(super.getValue(e, UNIT_NAME));
		item.setCreateDate(super.getValueDate(e, CREATE_DATE_NAME));
		item.setCatalogId(super.getValueLong(e, CATALOG_ID_NAME));
		item.setCatalogName(super.getValue(e, CATALOG_NAME_NAME));
		item.setCatalogLogo(super.getValue(e, CATALOG_LOGO_NAME));
		item.setImage(super.getValue(e, IMAGE_NAME));

		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
