package stx.shopclient.entity;

public class Tag {
	private long _id;
	private String _name;
	
	long getId() {
		return _id;
	}
	void setId(long id) {
		_id = id;
	}
	String getName() {
		return _name;
	}
	void setName(String name) {
		_name = name;
	}
}
