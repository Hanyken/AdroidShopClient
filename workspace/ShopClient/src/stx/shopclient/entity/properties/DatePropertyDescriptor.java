package stx.shopclient.entity.properties;

import java.util.GregorianCalendar;

public class DatePropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Date";

	private static DatePropertyDescriptor _currentEditedProperty;

	private GregorianCalendar _minValue;
	private GregorianCalendar _maxValue;
	private GregorianCalendar _currentMinValue;
	private GregorianCalendar _currentMaxValue;
	private boolean _isCurrentValueDefined = false;
	private boolean _isRange = true;

	public GregorianCalendar getMinValue() {
		return _minValue;
	}

	public void setMinValue(GregorianCalendar minValue) {
		_minValue = minValue;
	}

	public GregorianCalendar getMaxValue() {
		return _maxValue;
	}

	public void setMaxValue(GregorianCalendar maxValue) {
		_maxValue = maxValue;
	}

	@Override
	public void clear() {
		_isCurrentValueDefined = false;
	}

	public GregorianCalendar getCurrentMinValue() {
		return _currentMinValue;
	}
	
	public GregorianCalendar getCurrentValue(){
		return _currentMinValue;
	}

	public void setCurrentMinValue(GregorianCalendar currentMinValue) {
		_currentMinValue = currentMinValue;
	}

	public GregorianCalendar getCurrentMaxValue() {
		return _currentMaxValue;
	}

	public void setCurrentMaxValue(GregorianCalendar currentMaxValue) {
		_currentMaxValue = currentMaxValue;
	}

	public boolean isCurrentValueDefined() {
		return _isCurrentValueDefined;
	}

	public void setCurrentValueDefined(boolean isCurrentValueDefined) {
		_isCurrentValueDefined = isCurrentValueDefined;
	}

	public static DatePropertyDescriptor getCurrentEditedProperty() {
		return _currentEditedProperty;
	}

	public static void setCurrentEditedProperty(
			DatePropertyDescriptor currentEditedProperty) {
		_currentEditedProperty = currentEditedProperty;
	}

	public boolean isRange() {
		return _isRange;
	}

	public void setRange(boolean isRange) {
		_isRange = isRange;
	}

	@Override
	public String getStringValue() {
		return _minValue.toString();
	}
	
	@Override
	public boolean isValueDefined()
	{		
		return _isCurrentValueDefined;
	}
}
