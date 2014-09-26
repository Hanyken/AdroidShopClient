package stx.shopclient.utils;

import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.styles.ColorButtonDrawable;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.DisplayMetrics;

public final class DisplayUtil {
	public static int dpToPx(int dp, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		
		return (int)((dp * displayMetrics.density) + 0.5);
	}

	public static int pxToDp(int px, Context context) {
		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		
		return (int) (px / displayMetrics.density + 0.5);
	}
	
	public static Drawable getButtonDrawable(CatalogSettings settings)
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[]
		{ android.R.attr.state_pressed }, press);
		drawable.addState(new int[]
		{ -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
	}
}
