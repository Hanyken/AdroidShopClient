package stx.shopclient.parsers;

import org.w3c.dom.Element;

import android.graphics.Color;

import stx.shopclient.entity.CatalogSettings;

public class CatalogSettingParser extends BaseParser<CatalogSettings>
{
	private static final String ITEM_NAME = "Items";
	
	private static final String BACKGROUND_NAME = "Background";
	
	@Override
	public CatalogSettings getElement(Element e)
	{
		CatalogSettings item = new CatalogSettings();
		
		item.setBackground(super.getValueColor(e, BACKGROUND_NAME));
		
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
