package stx.shopclient.settings;

import android.app.Activity;
import android.content.SharedPreferences;

public class ServerSettings
{
	static final String PREF_NAME = "ServerSettings";
	static final String URL_KEY = "url";
	static final String URL_RESERVE_KEY = "url_reserve";
	
	static final String DEFAULT_URL = "http://5.19.235.173:8080/service";
	static final String DEFAULT_RESERVE_URL = "http://5.19.235.173:8080/service";
	
	private static String _url = DEFAULT_URL;
	private static String _urlReserve = DEFAULT_RESERVE_URL;
	
	public static String getUrl()
	{
		return _url;
	}
	public static void setUrl(String url)
	{
		_url = url;
	}
	public static String getUrlReserve()
	{
		return _urlReserve;
	}
	public static void setUrlReserve(String urlReserve)
	{
		_urlReserve = urlReserve;
	}
	
	public static void load(Activity activity)
	{
		SharedPreferences pref = activity.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		if (pref != null)
		{
			setUrl(pref.getString(URL_KEY, null));
			setUrlReserve(pref.getString(URL_RESERVE_KEY, null));
		}
	}

	public static void save(Activity activity)
	{
		SharedPreferences pref = activity.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(URL_KEY, getUrl());
		editor.putString(URL_RESERVE_KEY, getUrlReserve());
		editor.commit();
	}
	
	public static void switchToReserve()
	{
		String _tmp = _url;
		_url = _urlReserve;
		_urlReserve = _tmp;
	}
}
