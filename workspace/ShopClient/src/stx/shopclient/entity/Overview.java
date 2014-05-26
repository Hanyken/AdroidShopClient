package stx.shopclient.entity;

import android.util.EventLogTags.Description;

public class Overview
{
	private long _itemId;
	private boolean _isCurrentUser;
	private double _rating;
	private String _description;
	
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
}
