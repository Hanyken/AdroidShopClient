package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CatalogAddress;

public class CatalogAddressParser extends BaseParser<CatalogAddress>
{
	private static final String ITEM_NAME = "Address";
	private static final String CITY_ID_NAME = "CityId";
	private static final String CITY_NAME_NAME = "CityName";
	private static final String ADDRESS_NAME = "Address";
	
	
	@Override
	public CatalogAddress getElement(Element e)
	{
		CatalogAddress item = new CatalogAddress();
		
		item.setCityId(super.getValueLong(e, CITY_ID_NAME));
		item.setCityName(super.getValue(e, CITY_NAME_NAME));
		item.setAddress(super.getValue(e, ADDRESS_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		// TODO Auto-generated method stub
		return ITEM_NAME;
	}

}
