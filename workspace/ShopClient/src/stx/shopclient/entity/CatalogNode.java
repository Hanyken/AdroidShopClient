package stx.shopclient.entity;

public class CatalogNode {
	private long _id;
	private long _parentId;
	private long _catalogId;
	private String _name;
	private int _count;
	private boolean _isMajor;
	private String _icon;
	private String _description;
	private double _minPrice;
	private double _maxPrice;
	
	long getId() {
		return _id;
	}	
	void setId(long id) {
		_id = id;
	}	
	long getParentId() {
		return _parentId;
	}	
	void setParentId(long parentId) {
		_parentId = parentId;
	}	
	long getCatalogId() {
		return _catalogId;
	}	
	void setCatalogId(long catalogId) {
		_catalogId = catalogId;
	}	
	String getName() {
		return _name;
	}	
	void setName(String name) {
		_name = name;
	}	
	int getCount() {
		return _count;
	}	
	void setCount(int count) {
		_count = count;
	}	
	boolean isMajor() {
		return _isMajor;
	}	
	void setMajor(boolean isMajor) {
		_isMajor = isMajor;
	}	
	String getIcon() {
		return _icon;
	}	
	void setIcon(String icon) {
		_icon = icon;
	}	
	String getDescription() {
		return _description;
	}	
	void setDescription(String description) {
		_description = description;
	}	
	double getMinPrice() {
		return _minPrice;
	}	
	void setMinPrice(double minPrice) {
		_minPrice = minPrice;
	}	
	double getMaxPrice() {
		return _maxPrice;
	}	
	void setMaxPrice(double maxPrice) {
		_maxPrice = maxPrice;
	}
	
}
