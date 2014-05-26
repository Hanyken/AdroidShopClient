package stx.shopclient.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImagesManager 
{
	private ArrayList<String> _Items;
	
	public ImagesManager()
	{
		_Items = new ArrayList<String>();
		
		_Items.add("/sdcard/StxImages/1.jpg");
		_Items.add("/sdcard/StxImages/2.jpg");
		_Items.add("/sdcard/StxImages/3.jpg");
		_Items.add("/sdcard/StxImages/4.jpg");
		_Items.add("/sdcard/StxImages/5.jpg");
	}
	
	public Collection<String> getItemImages(long itemId)
	{
		return _Items;
	}
	
	
	
	
	
	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight)
	{
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{
			if (width > height)
			{
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
			else
			{
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}
}
