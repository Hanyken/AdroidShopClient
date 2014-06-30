package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.City;

public class CityParser extends BaseParser<City>
{
	private static final String ITEM_NAME = "City";
	private static final String ID_NAME = "Id";
	private static final String NAME_NAME = "Name";
	
	@Override
	public City getElement(Element e)
	{
		City item = new City();
		
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
