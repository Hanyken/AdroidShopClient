package stx.shopclient.entity;

public class Overview
{
	private float _rating;
	private String _description;
	
	public float getRating() 
	{
		return _rating;
	}
	public void setRating(float rating) 
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
