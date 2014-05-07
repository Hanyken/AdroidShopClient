package stx.shopclient.entity.searchproperties;

public class NumberPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Number";
	
	private double _minValue;
	private double _maxValue;
	
	private double _currentMinValue;
	private double _currentMaxValue;
	
	private boolean _isCurrentValueDefined = false;
	
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
}
