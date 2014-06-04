package stx.shopclient.entity;

import android.graphics.Color;

public class CatalogSettings
{
	private int _background;
	private int _itemPanelColor;
	private int _ratingColor;
	private int _foregroundColor;
	private int _nodeIconBackgroundColor = Color.parseColor("#1B86F4");
	
	public int getBackground()
	{
		return _background;
	}
	public void setBackground(int background)
	{
		_background = background;
	}
	
	public int getItemPanelColor()
	{
		return _itemPanelColor;
	}
	public void setItemPanelColor(int itemPanelColor)
	{
		_itemPanelColor = itemPanelColor;
	}
	
	public int getRatingColor()
	{
		return _ratingColor;
	}
	public void setRatingColor(int ratingColor)
	{
		_ratingColor = ratingColor;
	}
	
	public int getForegroundColor()
	{
		return _foregroundColor;
	}
	public void setForegroundColor(int foregroundColor)
	{
		_foregroundColor = foregroundColor;
	}
	public int getNodeIconBackgroundColor()
	{
		return _nodeIconBackgroundColor;
	}
	public void setNodeIconBackgroundColor(int nodeIconBackgroundColor)
	{
		_nodeIconBackgroundColor = nodeIconBackgroundColor;
	}
}
