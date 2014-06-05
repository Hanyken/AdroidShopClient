package stx.shopclient.styles;

import android.graphics.Canvas;
import android.graphics.Color;
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
		p.setColor(Color.parseColor("#0B000000"));
		Paint p2 = new Paint();
		p2.setColor(Color.parseColor("#15000000"));
		int bw1 = 2;
		int bw2 = 3;
		canvas.drawRect(0, 0, bw1, (canvas.getHeight()-bw1), p); // Left
		canvas.drawRect(bw1, bw1, (bw1 + bw2), (canvas.getHeight()-bw2), p2); // Left
		
		canvas.drawRect(bw1, 0, canvas.getWidth(), bw1, p); // Top
		canvas.drawRect((bw1 + bw2), bw1, canvas.getWidth(), (bw1 +  bw2), p2); // Top
		
		canvas.drawRect(canvas.getWidth() - bw1, bw1, canvas.getWidth(), canvas.getHeight(), p); // Right
		canvas.drawRect(canvas.getWidth() - (bw1 + bw2), (bw1 + bw2), canvas.getWidth() - bw1, canvas.getHeight() - bw1, p2); // Right
		
		canvas.drawRect(0, canvas.getHeight() - bw1, canvas.getWidth() - bw1, canvas.getHeight(), p); // Bottom
		canvas.drawRect(bw1, canvas.getHeight() - (bw1 + bw2), canvas.getWidth() - (bw1 + bw2), canvas.getHeight() - bw1, p2); // Bottom
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