package stx.shopclient.parsers;

import org.w3c.dom.Element;

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
	
	
	@Override
	public CatalogSettings getElement(Element e)
	{
		CatalogSettings item = new CatalogSettings();
		
		item.setBackground(super.getValueColor(e, BACKGROUND_NAME));
		item.setCountButtonLableColor(super.getValueColor(e, COUNT_BUTTON_LABLE_COLOR_NAME));
		item.setDisableColor(super.getValueColor(e, DISABLE_COLOR_NAME));
		item.setForegroundColor(super.getValueColor(e, FOREGROUND_COLOR_NAME));
		item.setItemPanelColor(super.getValueColor(e, ITEM_PANEL_COLOR_NAME));
		item.setPressedColor(super.getValueColor(e, PRESSED_COLOR_NAME));
		item.setRatingColor(super.getValueColor(e, RATING_COLOR_NAME));
		item.setCommentImgKey(super.getValue(e, COMMENT_IMG_NAME));
		item.setCommentImgPressKey(super.getValue(e, COMMENT_IMG_PRESS_NAME));
		item.setFavoriteImgKey(super.getValue(e, FAVORITE_IMG_NAME));
		item.setFavoriteImgPressKey(super.getValue(e, FAVORITE_IMG_PRESS_NAME));
		item.setShareImgKey(super.getValue(e, SHARE_IMG_NAME));
		item.setShareImgPressKey(super.getValue(e, SHARE_IMG_PRESS_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
