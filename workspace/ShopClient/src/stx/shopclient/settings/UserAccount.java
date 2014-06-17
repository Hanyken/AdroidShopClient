package stx.shopclient.settings;

import android.app.Activity;
import android.content.SharedPreferences;

public class UserAccount
{
	public static final String PREF_NAME = "UserAccount";
	public static final String LOGIN_KEY = "login";
	public static final String PASSWORD_KEY = "password";
	public static final String WIDTH_KEY = "width";
	public static final String HEIGHT_KEY = "height";

	private static String _login;
	private static String _password;
	private static Integer _width;
	private static Integer _height;

	public static void load(Activity activity)
	{
		SharedPreferences pref = activity.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		if (pref != null)
		{
			setLogin(pref.getString(LOGIN_KEY, null));
			setPassword(pref.getString(PASSWORD_KEY, null));
			setWidth(pref.getInt(WIDTH_KEY, 0));
			setHeight(pref.getInt(HEIGHT_KEY, 0));
		}
	}

	public static void save(Activity activity)
	{
		SharedPreferences pref = activity.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(LOGIN_KEY, getLogin());
		editor.putString(PASSWORD_KEY, getPassword());
		editor.putInt(WIDTH_KEY, getWidth());
		editor.putInt(HEIGHT_KEY, getHeight());
		editor.commit();
	}

	public static String getLogin()
	{
		return _login;
	}

	public static void setLogin(String _login)
	{
		UserAccount._login = _login;
	}

	public static String getPassword()
	{
		return _password;
	}

	public static void setPassword(String password)
	{
		_password = password;
	}
	
	public static Integer getWidth()
	{
		return _width;
	}

	public static void setWidth(Integer width)
	{
		_width = width;
	}
	
	public static Integer getHeight()
	{
		return _height;
	}

	public static void setHeight(Integer height)
	{
		_height = height;
	}
}
