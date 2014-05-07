package stx.shopclient.entity.searchproperties;

public abstract class PropertyDescriptor {
	private String _type;
	private String _name;
	private String _title;
	
	public abstract void clear();
	
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
}
