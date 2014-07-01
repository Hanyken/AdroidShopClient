package stx.shopclient.parsers;

import java.util.GregorianCalendar;

import org.w3c.dom.Element;

import stx.shopclient.entity.Discount;

public class DiscountParser extends BaseParser<Discount>
{
	public static final String ITEM_NAME = "Discount";

	@Override
	public Discount getElement(Element e)
	{
		Discount item = new Discount();

		item.setName(super.getValue(e, "Name"));
		item.setDescription(super.getValue(e, "Description"));
		item.setCode(super.getValue(e, "Code"));
		item.setSize(super.getValueDouble(e, "Size"));
		item.setUnit(super.getValue(e, "Unit"));
		item.setCreateDate(super.getValueDate(e, "CreateDate"));
		item.setCatalogId(super.getValueLong(e, "CatalogId"));
		item.setCatalogName(super.getValue(e, "CatalogName"));
		item.setCatalogLogo(super.getValue(e, "CatalogLogo"));
		item.setImage(super.getValue(e, "Image"));

		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
