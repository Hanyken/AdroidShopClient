package stx.shopclient.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import stx.shopclient.entity.Token;
import stx.shopclient.webservice.WebClient;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloadTask extends AsyncTask<Void, Void, Bitmap>
{
	ImageView _view;
	String _key;
	Context _context;

	static Map<String, Bitmap> Cache = new HashMap<String, Bitmap>();

	public ImageDownloadTask(ImageView view, Context context, String key)
	{
		_view = view;
		_context = context;
		_key = key;
	}

	public static void startNew(ImageView view, Context context, String key)
	{
		synchronized (Cache)
		{
			if (Cache.containsKey(key))
			{
				view.setImageBitmap(Cache.get(key));
				return;
			}
		}

		new ImageDownloadTask(view, context, key).execute();
	}

	@Override
	protected Bitmap doInBackground(Void... params)
	{
		synchronized (Cache)
		{
			if (Cache.containsKey(_key))
				return Cache.get(_key);
		}

		try
		{
			WebClient client = new WebClient(_context);
			Bitmap result = client.getImage(Token.getCurrent(), _key);

			if (result != null)
			{
				synchronized (Cache)
				{
					Cache.put(_key, result);
				}
			}

			return result;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
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
