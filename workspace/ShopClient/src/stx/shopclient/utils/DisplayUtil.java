package stx.shopclient.utils;

import android.content.Context;
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
}
