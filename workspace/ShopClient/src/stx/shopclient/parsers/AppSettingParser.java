package stx.shopclient.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.AppSettings;

public class AppSettingParser extends BaseParser<AppSettings>
{
	private static final String ITEM_NAME = "Items";
	
	private static final String VERSION_SERVER_NAME = "VersionServer";
	private static final String FIRST_SERVER_NAME = "FirstServerUri";
	private static final String LAST_SERVER_NAME = "LastServerUri";
	private static final String DEFAULT_CATALOG_NAME = "DefaultCatalog";
	
	private static final String NAME_NAME = "Name";
	private static final String VALUE_NAME = "Value";
	
	
	@Override
	public AppSettings getElement(Element root)
	{
		AppSettings item = new AppSettings();
		
		NodeList nl = root.getElementsByTagName("Item");
		
		if (nl.getLength() == 0) return null;
		
		for(int i=0; i<nl.getLength(); i++)
		{
			Element e = (Element)nl.item(i);
			
			String name = super.getValue(e, NAME_NAME);
			if (name.equals(VERSION_SERVER_NAME))
			{
				item.setServerVersion(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(FIRST_SERVER_NAME))
			{
				item.setFirstServerUri(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(LAST_SERVER_NAME))
			{
				item.setLastServerUri(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(DEFAULT_CATALOG_NAME))
			{
				item.setDefaultCatalog(super.getValueLong(e, VALUE_NAME));
			}
		}
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
