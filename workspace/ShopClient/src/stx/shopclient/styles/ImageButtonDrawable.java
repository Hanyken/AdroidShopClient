package stx.shopclient.styles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class ImageButtonDrawable extends Drawable
{
	private int _Color;
	private Bitmap _Image;

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

	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawColor(_Color);
		Paint p = new Paint();
		canvas.drawBitmap(_Image, 0, 0, p);
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
}
