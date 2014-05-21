package stx.shopclient.entity.properties;

public class StringPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "String";
	
	private String _value = "";

	@Override
	public void clear() {
		_value = "";
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}
}
