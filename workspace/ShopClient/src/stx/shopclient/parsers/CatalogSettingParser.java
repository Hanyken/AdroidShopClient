package stx.shopclient.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.CatalogSettings;

public class CatalogSettingParser extends BaseParser<CatalogSettings>
{
	private static final String ITEM_NAME = "Items";
	
	private static final String BACKGROUND_NAME = "Background";
	private static final String COUNT_BUTTON_LABLE_COLOR_NAME = "CountButtonLableColor";
	private static final String DISABLE_COLOR_NAME = "DisableColor";
	private static final String FOREGROUND_COLOR_NAME = "ForegroundColor";
	private static final String ITEM_PANEL_COLOR_NAME = "ItemPanelColor";
	private static final String PRESSED_COLOR_NAME = "PressedColor";
	private static final String RATING_COLOR_NAME = "RatingColor";
	private static final String COMMENT_IMG_NAME = "CommentImg";
	private static final String COMMENT_IMG_PRESS_NAME = "CommentImgPress";
	private static final String FAVORITE_IMG_NAME = "FavoriteImg";
	private static final String FAVORITE_IMG_PRESS_NAME = "FavoriteImgPress";
	private static final String SHARE_IMG_NAME = "ShareImg";
	private static final String SHARE_IMG_PRESS_NAME = "ShareImgPress";
	
	private static final String NAME_NAME = "Name";
	private static final String VALUE_NAME = "Value";
	//private static final String TYPE_NAME = "Type";
	
	
	@Override
	public CatalogSettings getElement(Element root)
	{
		CatalogSettings item = new CatalogSettings();
		
		NodeList nl = root.getElementsByTagName("Item");
		
		if (nl.getLength() == 0) return null;
		
		for(int i=0; i<nl.getLength(); i++)
		{
			Element e = (Element)nl.item(i);
			
			String name = super.getValue(e, NAME_NAME);
			if (name.equals(BACKGROUND_NAME))
			{
				item.setBackground(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(COUNT_BUTTON_LABLE_COLOR_NAME))
			{
				item.setCountButtonLableColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(DISABLE_COLOR_NAME))
			{
			item.setDisableColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(FOREGROUND_COLOR_NAME))
			{
			item.setForegroundColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(ITEM_PANEL_COLOR_NAME))
			{
			item.setItemPanelColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(PRESSED_COLOR_NAME))
			{
			item.setPressedColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(RATING_COLOR_NAME))
			{
			item.setRatingColor(super.getValueColor(e, VALUE_NAME));
			}
			else if (name.equals(COMMENT_IMG_NAME))
			{
			item.setCommentImgKey(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(COMMENT_IMG_PRESS_NAME))
			{
			item.setCommentImgPressKey(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(FAVORITE_IMG_NAME))
			{
			item.setFavoriteImgKey(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(FAVORITE_IMG_PRESS_NAME))
			{
			item.setFavoriteImgPressKey(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(SHARE_IMG_NAME))
			{
			item.setShareImgKey(super.getValue(e, VALUE_NAME));
			}
			else if (name.equals(SHARE_IMG_PRESS_NAME))
			{
			item.setShareImgPressKey(super.getValue(e, VALUE_NAME));
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
