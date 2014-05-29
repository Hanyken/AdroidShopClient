package stx.shopclient.entity;

public class CatalogNodeProperty<T> extends PropertyBase<T>
{
	private boolean _Multiselect;
	private boolean _QuickSearch;
	
	public boolean getMultiselect()
	{
		return _Multiselect;
	}
	public void setMultiselect(boolean multiselect)
	{
		_Multiselect = multiselect;
	}
	
	public boolean getQuickSearch()
	{
		return _QuickSearch;
	}
	public void setQuickSearch(boolean quickSearch)
	{
		_QuickSearch = quickSearch;
	}
}
