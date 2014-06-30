package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.Discount;

public class DiscountParser extends BaseParser<Discount>
{
	public static final String ITEM_NAME = "Discount";

	@Override
	public Discount getElement(Element e)
	{
		Discount item = new Discount();
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
