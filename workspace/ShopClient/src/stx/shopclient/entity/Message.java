package stx.shopclient.entity;

import java.util.GregorianCalendar;

public class Message
{
	private long _id;
	private int _type;
	private String _title;
	private String _text;
	private GregorianCalendar _createDate;
	
	public long getId()
	{
		return _id;
	}
	public void setId(long id)
	{
		_id = id;
	}
	public int getType()
	{
		return _type;
	}
	public void setType(int type)
	{
		_type = type;
	}
	public String getTitle()
	{
		return _title;
	}
	public void setTitle(String title)
	{
		_title = title;
	}
	public String getText()
	{
		return _text;
	}
	public void setText(String text)
	{
		_text = text;
	}
	public GregorianCalendar getCreateDate()
	{
		return _createDate;
	}
	public void setCreateDate(GregorianCalendar createDate)
	{
		_createDate = createDate;
	}
}
