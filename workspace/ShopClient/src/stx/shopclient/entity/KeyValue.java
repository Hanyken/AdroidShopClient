package stx.shopclient.entity;

// Базовый клас свойств
public class KeyValue<T>
{
	private String _Name;
	private T _Value;
	
	public String getName()
	{
		return _Name;
	}
	public void setName(String name)
	{
		_Name = name;
	}
	
	public T getValue()
	{
		return _Value;
	}
	public void setValue(T value)
	{
		_Value = value;
	}
}
