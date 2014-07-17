package stx.shopclient.entity;

import java.util.GregorianCalendar;

public class Message
{
	private int _rowNum;
	private long _id;
	private int _type;
	private String _title;
	private String _text;
	private String _image;
	private GregorianCalendar _createDate;
	private boolean _isRead;
	
	public int getRowNum()
	{
		return _rowNum;
	}
	public void setRowNum(int rowNum)
	{
		_rowNum = rowNum;
	}
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
	public boolean isRead()
	{
		return _isRead;
	}
	public void setRead(boolean isRead)
	{
		_isRead = isRead;
	}
	public String getImage()
	{
		return _image;
	}
	public void setImage(String image)
	{
		_image = image;
	}
}
