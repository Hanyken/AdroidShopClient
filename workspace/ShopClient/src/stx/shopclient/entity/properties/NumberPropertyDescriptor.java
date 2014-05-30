package stx.shopclient.entity.properties;

public class NumberPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Number";

	private double _minValue;
	private double _maxValue;

	private double _currentMinValue;
	private double _currentMaxValue;

	private boolean _isCurrentValueDefined = false;
	private boolean _isRange = true;
	private boolean _isFloat = true;

	public double getMinValue() {
		return _minValue;
	}

	public void setMinValue(double minValue) {
		_minValue = minValue;
	}

	public double getMaxValue() {
		return _maxValue;
	}

	public void setMaxValue(double maxValue) {
		_maxValue = maxValue;
	}

	public double getCurrentMinValue() {
		return _currentMinValue;
	}

	public double getCurrentValue() {
		return _currentMinValue;
	}

	public void setCurrentMinValue(double currentMinValue) {
		_currentMinValue = currentMinValue;
	}

	public double getCurrentMaxValue() {
		return _currentMaxValue;
	}

	public void setCurrentMaxValue(double currentMaxValue) {
		_currentMaxValue = currentMaxValue;
	}

	public boolean isCurrentValueDefined() {
		return _isCurrentValueDefined;
	}

	public void setCurrentValueDefined(boolean isCurrentValueDefined) {
		_isCurrentValueDefined = isCurrentValueDefined;
	}

	@Override
	public void clear() {
		_isCurrentValueDefined = false;
	}

	public boolean isRange() {
		return _isRange;
	}

	public void setRange(boolean isRange) {
		_isRange = isRange;
	}

	public boolean isFloat() {
		return _isFloat;
	}

	public void setFloat(boolean isFloat) {
		_isFloat = isFloat;
	}
	
	@Override
	public String getStringValue() {
		return Double.toString(_currentMaxValue);
	}
}
