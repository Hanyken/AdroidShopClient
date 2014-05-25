package stx.shopclient.entity.properties;

import java.util.ArrayList;
import java.util.List;

public class EnumPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Enum";

	private List<EnumValue> _values = new ArrayList<EnumValue>();
	private List<EnumValue> _currentValues = new ArrayList<EnumValue>();

	public List<EnumValue> getValues() {
		return _values;
	}

	public void setValues(List<EnumValue> values) {
		_values = values;
	}

	public List<EnumValue> getCurrentValues() {
		return _currentValues;
	}

	public void setCurrentValues(List<EnumValue> currentValues) {
		_currentValues = currentValues;
	}

	public static class EnumValue {
		private String _value;
		private String _name;

		public EnumValue() {
		}

		public EnumValue(String value, String name) {
			_value = value;
			_name = name;
		}

		public String getValue() {
			return _value;
		}

		public void setValue(String value) {
			_value = value;
		}

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}
	}

	@Override
	public void clear() {
		_currentValues.clear();
	}

	@Override
	public String getStringValue() {
		if (_currentValues != null && _currentValues.size() > 0)
			return _currentValues.get(0).getValue();
		return null;
	}
}
