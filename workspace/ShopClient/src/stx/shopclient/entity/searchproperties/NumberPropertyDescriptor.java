package stx.shopclient.entity.searchproperties;

public class NumberPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Number";
	
	private double _minValue;
	private double _maxValue;
	
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
}
