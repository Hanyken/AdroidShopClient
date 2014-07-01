package stx.shopclient.entity.properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NumberPropertyDescriptor extends PropertyDescriptor
{
	public static final String TYPE_STRING = "Numeric";

	private double _minValue;
	private double _maxValue;

	private double _currentMinValue;
	private double _currentMaxValue;

	private boolean _isCurrentValueDefined = false;
	private boolean _isCurrentMinValueDefined = false;
	private boolean _isCurrentMaxValueDefined = false;
	private boolean _isRange = true;
	private boolean _isFloat = true;
	
	private String _unitName;

	public double getMinValue()
	{
		return _minValue;
	}

	public void setMinValue(double minValue)
	{
		_minValue = minValue;
	}

	public double getMaxValue()
	{
		return _maxValue;
	}

	public void setMaxValue(double maxValue)
	{
		_maxValue = maxValue;
	}

	public double getCurrentMinValue()
	{
		return _currentMinValue;
	}

	public double getCurrentValue()
	{
		return _currentMinValue;
	}

	public void setCurrentMinValue(double currentMinValue)
	{
		_currentMinValue = currentMinValue;
	}

	public double getCurrentMaxValue()
	{
		return _currentMaxValue;
	}

	public void setCurrentMaxValue(double currentMaxValue)
	{
		_currentMaxValue = currentMaxValue;
	}

	public boolean isCurrentValueDefined()
	{
		return _isCurrentValueDefined;
	}

	public void setCurrentValueDefined(boolean isCurrentValueDefined)
	{
		_isCurrentValueDefined = isCurrentValueDefined;
	}

	@Override
	public void clear()
	{
		_isCurrentValueDefined = false;
		_isCurrentMinValueDefined = false;
		_isCurrentMaxValueDefined = false;
	}

	public boolean isRange()
	{
		return _isRange;
	}

	public void setRange(boolean isRange)
	{
		_isRange = isRange;
	}

	public boolean isFloat()
	{
		return _isFloat;
	}

	public void setFloat(boolean isFloat)
	{
		_isFloat = isFloat;
	}

	@Override
	public String getStringValue()
	{
		return getStringValue(getCurrentValue()) + getUnitName();
	}

	@Override
	public boolean isValueDefined()
	{
		return _isCurrentValueDefined;
	}

	@Override
	public void appendSearchPropertyXml(Element root)
	{
		if (!isValueDefined())
			return;

		Document doc = root.getOwnerDocument();
		Element el = doc.createElement(getName());

		if (_isRange)
		{
			if (_isCurrentMinValueDefined)
			{
				Element elMin = doc.createElement("Min");
				elMin.setTextContent(Double.toString(_currentMinValue));
				el.appendChild(elMin);
			}

			if (_isCurrentMaxValueDefined)
			{
				Element elMax = doc.createElement("Max");
				elMax.setTextContent(Double.toString(_currentMaxValue));
				el.appendChild(elMax);
			}
		}
		else
		{
			Element elValue = doc.createElement("Value");
			elValue.setTextContent(Double.toString(getCurrentValue()));
			el.appendChild(elValue);
		}

		root.appendChild(el);
	}

	public boolean isCurrentMinValueDefined()
	{
		return _isCurrentMinValueDefined;
	}

	public void setCurrentMinValueDefined(boolean isCurrentMinValueDefined)
	{
		_isCurrentMinValueDefined = isCurrentMinValueDefined;
	}

	public boolean isCurrentMaxValueDefined()
	{
		return _isCurrentMaxValueDefined;
	}

	public void setCurrentMaxValueDefined(boolean isCurrentMaxValueDefined)
	{
		_isCurrentMaxValueDefined = isCurrentMaxValueDefined;
	}
	
	public String getUnitName()
	{
		return _unitName != null ? " "+_unitName : "";
	}

	public void setUnitName(String unitName)
	{
		_unitName = unitName;
	}
	

	String getStringValue(double value)
	{
		if (_isFloat)
			return String.format("%.2f", value);
		else
			return String.format("%d", (int) value);
	}

	@Override
	public String getDescription()
	{
		if (_isRange)
		{
			String descr = "";
			if (_isCurrentMinValueDefined)
				descr += "от " + getStringValue(_currentMinValue);
			if (_isCurrentMaxValueDefined)
			{
				if (_isCurrentMinValueDefined)
					descr += " ";
				descr += "до " + getStringValue(_currentMaxValue);
			}

			return descr + getUnitName();
		}
		else
		{
			return getStringValue(_currentMinValue);
		}
	}
}
