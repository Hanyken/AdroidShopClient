package stx.shopclient.entity.properties;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

public abstract class PropertyDescriptor implements Serializable {
	private String _type;
	private String _name;
	private String _title;
	private boolean _required;
	private int _order;
	private boolean _multiselect;
	private boolean _quickSearch;
	private boolean _isValueDefined;
	
	public abstract void clear();
	
	public abstract String getStringValue();
	
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		_title = title;
	}
	public String getType() {
		return _type;
	}
	public void setType(String type) {
		_type = type;
	}
	public Boolean getRequired(){
		return _required;
	}
	public void setRequired(Boolean required){
		_required = required;
	}
	public Integer getOrder(){
		return _order;
	}
	public void setOrder(Integer order){
		_order = order;
	}
	public Boolean getMultiselect(){
		return _multiselect;
	}
	public void setMultiselect(Boolean multiselect){
		_multiselect = multiselect;
	}
	public Boolean getQuickSearch(){
		return _quickSearch;
	}
	public void setQuickSearch(Boolean quickSearch){
		_quickSearch = quickSearch;
	}

	public boolean isValueDefined()
	{
		return _isValueDefined;
	}

	public void setValueDefined(boolean isValueDefined)
	{
		_isValueDefined = isValueDefined;
	}
	
	public PropertyDescriptor cloneProperty()
	{
		return (PropertyDescriptor)SerializationUtils.clone(this);
	}
}
