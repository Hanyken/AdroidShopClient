package stx.shopclient.entity;

import stx.shopclient.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CatalogSettings
{
	public static final String IMG_KEY_COMMENT = "Comment";
	public static final String IMG_KEY_SHARE = "Share";
	public static final String IMG_KEY_FAVOTITS = "Favorits";
	
	private int _background;
	private int _itemPanelColor;
	private int _ratingColor;
	private int _foregroundColor;
	private int _pressedColor;
	private int _disableColor;
	private int _countButtonLableColor;
	private Bitmap _shareImg;
	private String _shareImgKey;
	private Bitmap _shareImgPress;
	private String _shareImgPressKey;
	private Bitmap _commentImg;
	private String _commentImgKey;
	private Bitmap _commentImgPress;
	private String _commentImgPressKey;
	private Bitmap _favoriteImg;
	private String _favoriteImgKey;
	private Bitmap _favoriteImgPress;
	private String _favoriteImgPressKey;

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
	public String getShareImgKey()
	{
		return _shareImgKey;
	}
	public void setShareImgKey(String shareImgKey)
	{
		_shareImgKey = shareImgKey;
	}
	
	public Bitmap getShareImgPress()
	{
		return _shareImgPress;
	}
	public void setShareImgPress(Bitmap shareImgPress)
	{
		_shareImgPress = shareImgPress;
	}
	public String getShareImgPressKey()
	{
		return _shareImgPressKey;
	}
	public void setShareImgPressKey(String shareImgPressKey)
	{
		_shareImgPressKey = shareImgPressKey;
	}
	
	public Bitmap getCommentImg()
	{
		return _commentImg;
	}
	public void setCommentImg(Bitmap commentImg)
	{
		_commentImg = commentImg;
	}
	public String getCommentImgKey()
	{
		return _commentImgKey;
	}
	public void setCommentImgKey(String commentImgKey)
	{
		_commentImgKey = commentImgKey;
	}
	
	public Bitmap getCommentImgPress()
	{
		return _commentImgPress;
	}
	public void setCommentImgPress(Bitmap commentImgPress)
	{
		_commentImgPress = commentImgPress;
	}
	public String getCommentImgPressKey()
	{
		return _commentImgPressKey;
	}
	public void setCommentImgPressKey(String commentImgPressKey)
	{
		_commentImgPressKey = commentImgPressKey;
	}
	
	public Bitmap getFavoriteImg()
	{
		return _favoriteImg;
	}
	public void setFavoriteImg(Bitmap favoriteImg)
	{
		_favoriteImg = favoriteImg;
	}
	public String getFavoriteImgKey()
	{
		return _favoriteImgKey;
	}
	public void setFavoriteImgKey(String favoriteImgKey)
	{
		_favoriteImgKey = favoriteImgKey;
	}
	
	public Bitmap getFavoriteImgPress()
	{
		return _favoriteImgPress;
	}
	public void setFavoriteImgPress(Bitmap favoriteImgPress)
	{
		_favoriteImgPress = favoriteImgPress;
	}
	
	public String getFavoriteImgPressKey()
	{
		return _favoriteImgPressKey;
	}
	public void setFavoriteImgPressKey(String favoriteImgPressKey)
	{
		_favoriteImgPressKey = favoriteImgPressKey;
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
