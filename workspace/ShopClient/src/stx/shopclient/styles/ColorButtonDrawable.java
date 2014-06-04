package stx.shopclient.styles;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class ColorButtonDrawable extends Drawable
{
	private int _Color;

	public ColorButtonDrawable(int color)
	{
		_Color = color;
	}

	@Override
	public void draw(Canvas canvas)
	{
		canvas.drawColor(_Color);
		Paint p = new Paint();
		p.setColor(Resources.getSystem().getColor(android.R.color.black));
		canvas.drawRect(1, 1, 2, canvas.getHeight(), p);
		canvas.drawRect(1, canvas.getHeight() - 2, canvas.getWidth(),
				canvas.getHeight(), p);
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