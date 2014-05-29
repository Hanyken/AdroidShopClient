package stx.shopclient.entity.properties;

public abstract class PropertyDescriptor {
	private String _type;
	private String _name;
	private String _title;
	private Boolean _required;
	private Integer _order;
	private boolean _multiselect;
	private boolean _quickSearch;
	
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
}
