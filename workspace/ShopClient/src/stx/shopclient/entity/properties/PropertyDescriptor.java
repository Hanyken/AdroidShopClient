package stx.shopclient.entity.properties;

public abstract class PropertyDescriptor {
	private String _type;
	private String _name;
	private String _title;
	private Boolean _required;
	
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
}
