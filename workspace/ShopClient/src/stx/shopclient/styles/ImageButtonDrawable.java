package stx.shopclient.styles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ImageButtonDrawable extends Drawable
{
	private int _Color;
	private Bitmap _Image;
	private int _width;
	private int _height;
	private int _xPos;
	private int _yPos;

	public ImageButtonDrawable(int color, Bitmap image)
	{
		_Color = color;
		_Image = image;
	}
	public ImageButtonDrawable(Bitmap image)
	{
		_Color = Resources.getSystem().getColor(android.R.color.transparent);
		_Image = image;
	}
	
	public void setSizePx(int width, int height)
	{
		_width = width;
		_height = height;
	}
	public void setSizeDp(Context context, float width, float height)
	{
		_width = dipToPixels(context, width);
		_height = dipToPixels(context, height);
	}
	public void setPositionPx(int x, int y)
	{
		_xPos = x;
		_yPos = y;
	}
	public void setPositionDp(Context context, float x, float y)
	{
		_xPos = dipToPixels(context, x);
		_yPos = dipToPixels(context, y);
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawColor(_Color);
		Paint p = new Paint();
		if (_width == 0 && _height == 0)
		{
		//Rect r1 = new Rect();
		//r1.set(0, 0, _Image.getWidth(), _Image.getHeight());
		//Rect r2 = new Rect();
		//r2.set(0, 0, canvas.getWidth(), canvas.getHeight());
		//canvas.drawBitmap(_Image, r1, r2, p);
			canvas.drawBitmap(_Image, 0, 0, p);
		}
		else
		{
			Rect r1 = new Rect();
			r1.set(0, 0, _Image.getWidth(), _Image.getHeight());
			Rect r2 = new Rect();
			r2.set(_xPos, _yPos, _width, _height);
			canvas.drawBitmap(_Image, r1, r2, p);
		}
		
		/*
		
		p.setColor(Resources.getSystem().getColor(android.R.color.black));
		canvas.drawRect(1, 1, 2, canvas.getHeight(), p);
		canvas.drawRect(1, canvas.getHeight() - 2, canvas.getWidth(),
				canvas.getHeight(), p);
				*/
	}

	@Override
	public int getOpacity()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter cf)
	{

	}
	
	
	public static int dipToPixels(Context context, float dipValue) 
	{
	    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
	    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
	}
}
