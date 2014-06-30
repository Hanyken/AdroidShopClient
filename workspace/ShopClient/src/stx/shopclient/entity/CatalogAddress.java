package stx.shopclient.entity;

public class CatalogAddress
{
	private long _CityId;
	private String  _CityName;
	private String  _Address;
	
	public long getCityId()
	{
		return _CityId;
	}
	
	public void setCityId(long cityId)
	{
		_CityId = cityId;
	}
	
	public String getCityName()
	{
		return _CityName;
	}
	
	public void setCityName(String cityName)
	{
		_CityName = cityName;
	}
	
	public String getAddress()
	{
		return _Address;
	}
	
	public void setAddress(String address)
	{
		_Address = address;
	}
}
