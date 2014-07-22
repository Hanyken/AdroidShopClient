package stx.shopclient.utils;

import stx.shopclient.entity.Token;
import stx.shopclient.webservice.WebClient;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDownloadTask extends AsyncTask<Void, Void, Bitmap>
{
	ImageView _view;
	String _key;
	Context _context;
	ActionBar _actionBar;

	public static ImageCache Cache = null;

	public ImageDownloadTask(ImageView view, Context context, String key)
	{
		_view = view;
		_context = context;
		_key = key;
	}

	public ImageDownloadTask(ActionBar actionBar, Context context, String key)
	{
		_actionBar = actionBar;
		_context = context;
		_key = key;
	}

	public static void startNew(ImageView view, Context context, String key)
	{
		synchronized (Cache)
		{
			Bitmap bmp = Cache.getImage(key);
			if (bmp != null)
			{
				view.setImageBitmap(bmp);
				return;
			}
		}

		new ImageDownloadTask(view, context, key).execute();
	}

	public static void startNew(ActionBar actionBar, Context context, String key)
	{
		synchronized (Cache)
		{
			Bitmap bmp = Cache.getImage(key);
			if (bmp != null)
			{
				actionBar.setIcon(new BitmapDrawable(context.getResources(),
						bmp));
				return;
			}
		}

		new ImageDownloadTask(actionBar, context, key).execute();
	}

	@Override
	protected Bitmap doInBackground(Void... params)
	{
		synchronized (Cache)
		{
			Bitmap bmp = Cache.getImage(_key);
			if (bmp != null)
				return bmp;
		}

		try
		{
			WebClient client = new WebClient(_context);
			Bitmap result = client.getImage(Token.getCurrent(), _key);

			if (result != null)
			{
				synchronized (Cache)
				{
					Cache.putImage(_key, result);
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
		{
			if (_view != null)
				_view.setImageBitmap(result);
			if (_actionBar != null)
				_actionBar.setIcon(new BitmapDrawable(_context.getResources(),
						result));
		}
	}

}
