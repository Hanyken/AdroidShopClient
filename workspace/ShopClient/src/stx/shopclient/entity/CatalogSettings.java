package stx.shopclient.entity;

import stx.shopclient.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CatalogSettings
{
	private int _background;
	private int _itemPanelColor;
	private int _ratingColor;
	private int _foregroundColor;
	private int _pressedColor;
	private int _disableColor;
	private int _countButtonLableColor;
	private Bitmap _shareImg;
	private Bitmap _shareImgPress;
	private Bitmap _commentImg;
	private Bitmap _commentImgPress;
	private Bitmap _favoriteImg;
	private Bitmap _favoriteImgPress;

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

	public int getPressedColor()
	{
		return _pressedColor;
	}

	public void setPressedColor(int pressedColor)
	{
		_pressedColor = pressedColor;
	}

	public int getDisableColor()
	{
		return _disableColor;
	}

	public void setDisableColor(int disableColor)
	{
		_disableColor = disableColor;
	}

	public Bitmap getShareImg()
	{
		return _shareImg;
	}
	public void setShareImg(Bitmap shareImg)
	{
		_shareImg = shareImg;
	}
	
	public Bitmap getShareImgPress()
	{
		return _shareImgPress;
	}
	public void setShareImgPress(Bitmap shareImgPress)
	{
		_shareImgPress = shareImgPress;
	}
	
	public Bitmap getCommentImg()
	{
		return _commentImg;
	}
	public void setCommentImg(Bitmap commentImg)
	{
		_commentImg = commentImg;
	}
	
	public Bitmap getCommentImgPress()
	{
		return _commentImgPress;
	}
	public void setCommentImgPress(Bitmap commentImgPress)
	{
		_commentImgPress = commentImgPress;
	}
	
	public Bitmap getFavoriteImg()
	{
		return _favoriteImg;
	}
	public void setFavoriteImg(Bitmap favoriteImg)
	{
		_favoriteImg = favoriteImg;
	}
	
	public Bitmap getFavoriteImgPress()
	{
		return _favoriteImgPress;
	}
	public void setFavoriteImgPress(Bitmap favoriteImgPress)
	{
		_favoriteImgPress = favoriteImgPress;
	}
	
	public int getCountButtonLableColor()
	{
		return _countButtonLableColor;
	}
	public void setCountButtonLableColor(int countButtonLableColor)
	{
		_countButtonLableColor = countButtonLableColor;
	}
	
	

	public Bitmap getImageFromPath(Resources resources, String imgName)
	{
		if (imgName.equals("Share"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_share);
		}
		else if (imgName.equals("SharePress"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_share_press);
		}
		else if (imgName.equals("Comment"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_comment);
		}
		else if (imgName.equals("CommentPress"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_comment_press);
		}
		else if (imgName.equals("Favorits"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_like); 
		}
		else if (imgName.equals("FavoritsPress"))
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.img_like_press); 
		}
		else
		{
			return BitmapFactory.decodeResource(resources,
					R.drawable.ic_launcher);
		}
	}
}
