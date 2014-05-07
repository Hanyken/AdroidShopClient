package stx.shopclient.entity.searchproperties;

public class BooleanPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Boolean";
	
	private boolean _currentValue;

	public boolean getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(boolean currentValue) {
		_currentValue = currentValue;
	}

	@Override
	public void clear() {
		_currentValue = false;
	}
}
