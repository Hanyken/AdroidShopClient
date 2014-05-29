package stx.shopclient.entity;

import java.util.Date;

public class Overview
{
	private int _rowNumber;
	private long _itemId;
	private boolean _isCurrentUser;
	private double _rating;
	private String _description;
	private Date _createDate;
	
	public int getRowNumber()
	{
		return _rowNumber;
	}
	public void setRowNumber(int rowNumber)
	{
		_rowNumber = rowNumber;
	}
	
	public long getItemId()
	{
		return _itemId;
	}
	public void setItemId(long itemId)
	{
		_itemId = itemId;
	}
	public double getRating() 
	{
		return _rating;
	}
	public void setRating(double rating) 
	{
		_rating = rating;
	}
	public String getDescription() 
	{
		return _description;
	}
	public void setDescription(String description) 
	{
		_description = description;
	}
	
	public Overview()
	{
		
	}
	public Overview(float rating, String description)
	{
		_rating = rating;
		_description = description;
	}
	public boolean getIsCurrentUser()
	{
		return _isCurrentUser;
	}
	public void setIsCurrentUser(boolean isCurrentUser)
	{
		_isCurrentUser = isCurrentUser;
	}
	public Date getCreateDate()
	{
		return _createDate;
	}
	public void setCreateDate(Date createDate)
	{
		_createDate = createDate;
	}
	
}
