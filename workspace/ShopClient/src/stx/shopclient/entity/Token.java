package stx.shopclient.entity;

import java.util.Date;

public class Token
{
	private String _code;
	private String _token;
	private Date _begDate;
	private int _interval;
	
	public String getCode()
	{
		return _code;
	}
	public void setCode(String code)
	{
		_code= code;
	}
	
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
}
