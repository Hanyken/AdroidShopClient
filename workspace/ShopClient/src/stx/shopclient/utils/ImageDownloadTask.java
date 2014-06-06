package stx.shopclient.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>
{
	ImageView _view;

	static Map<String, Bitmap> Cache = new HashMap<String, Bitmap>();

	public ImageDownloadTask(ImageView view)
	{
		_view = view;
	}

	public static void startNew(ImageView view, String url)
	{
		synchronized (Cache)
		{
			if (Cache.containsKey(url))
			{
				view.setImageBitmap(Cache.get(url));
				return;
			}
		}

		new ImageDownloadTask(view).execute(url);
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		String urlString = params[0];

		synchronized (Cache)
		{
			if (Cache.containsKey(urlString))
				return Cache.get(urlString);
		}

		InputStream stream = null;

		try
		{
			Thread.sleep(700);
		}
		catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try
		{
			URL url = new URL(urlString);

			URLConnection connection = url.openConnection();
			stream = connection.getInputStream();
			Bitmap result = BitmapFactory.decodeStream(stream);

			synchronized (Cache)
			{
				Cache.put(urlString, result);
			}

			return result;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					e.printStackTrace();
				}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		if (result != null)
			_view.setImageBitmap(result);
	}

}
