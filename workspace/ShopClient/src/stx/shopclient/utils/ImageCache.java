package stx.shopclient.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageCache
{
	static final String DIRECTORY_NAME = "image_cache";
	Context _context;

	public ImageCache(Context context)
	{
		_context = context;
	}

	public Bitmap getImage(String key)
	{
		final String fileName = key;
		File cacheDir = _context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);

		File[] files = cacheDir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				return filename.equals(fileName);
			}
		});

		if (files.length > 0)
		{
			return BitmapFactory.decodeFile(files[0].getAbsolutePath());
		}
		else
			return null;
	}

	public void putImage(String key, Bitmap image)
	{
		File cacheDir = _context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);

		File file = new File(cacheDir, key);
		FileOutputStream stream = null;
		try
		{
			stream = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.PNG, 100, stream);
		}
		catch (Throwable e)
		{
			Log.e("Stx", e.getLocalizedMessage());
		}
		finally
		{
			if (stream != null)
				try
				{
					stream.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void clear()
	{
		File cacheDir = _context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);
		File[] files = cacheDir.listFiles();
		for (File file : files)
			file.delete();
	}
}
