package stx.shopclient.entity;

import android.util.EventLogTags.Description;

public class Overview
{
	private double _rating;
	private String _description;
	
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
}
