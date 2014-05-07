package stx.shopclient.entity.searchproperties;

import java.util.Date;

public class DatePropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Date";
	
	private Date _minValue;
	private Date _maxValue;
	
	public Date getMinValue() {
		return _minValue;
	}
	public void setMinValue(Date minValue) {
		_minValue = minValue;
	}
	public Date getMaxValue() {
		return _maxValue;
	}
	public void setMaxValue(Date maxValue) {
		_maxValue = maxValue;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
