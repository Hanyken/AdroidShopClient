package stx.shopclient.entity;

import java.util.Date;

public class Token extends ResultEntity
{
	public static String TOKEN_ARG_NAME = "token";
	private static Token current;	
	
	
	private String _token;
	private Date _begDate;
	private int _interval;
	
	
	
	public String getToken()
	{
		return _token;
	}
	public void setToken(String token)
	{
		_token= token;
	}
	
	public Date getBegDate()
	{
		return _begDate;
	}
	public void setBegDate(Date begDate)
	{
		_begDate = begDate;
	}
	
	public int getInterval()
	{
		return _interval;
	}
	public void setInterval(int interval)
	{
		_interval = interval;
	}
	public static Token getCurrent()
	{
		return current;
	}
	public static void setCurrent(Token current)
	{
		Token.current = current;
	}
}
