package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CountResultEntity;

public class CountResultParser extends BaseParser<CountResultEntity>
{
	private final String COUNT_NAME = "Count";

	@Override
	public CountResultEntity getElement(Element e)
	{
		CountResultEntity item = new CountResultEntity();
		
		item.setCount(super.getValueLong(e, COUNT_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ResultParser.ITEM_NAME;
	}
	

}
