package stx.shopclient.entity.properties;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import stx.shopclient.parsers.BaseParser;

public class DatePropertyDescriptor extends PropertyDescriptor
{
	public static final String TYPE_STRING = "Date";

	private static DatePropertyDescriptor _currentEditedProperty;

	static SimpleDateFormat _dateFormat = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

	private GregorianCalendar _minValue;
	private GregorianCalendar _maxValue;
	private GregorianCalendar _currentMinValue;
	private GregorianCalendar _currentMaxValue;
	private boolean _isCurrentValueDefined = false;
	private boolean _isCurrentMinValueDefined = false;
	private boolean _isCurrentMaxValueDefined = false;
	private boolean _isRange = true;

	public GregorianCalendar getMinValue()
	{
		return _minValue;
	}

	public void setMinValue(GregorianCalendar minValue)
	{
		_minValue = minValue;
	}

	public GregorianCalendar getMaxValue()
	{
		return _maxValue;
	}

	public void setMaxValue(GregorianCalendar maxValue)
	{
		_maxValue = maxValue;
	}

	@Override
	public void clear()
	{
		_isCurrentValueDefined = false;
	}

	public GregorianCalendar getCurrentMinValue()
	{
		return _currentMinValue;
	}

	public GregorianCalendar getCurrentValue()
	{
		return _currentMinValue;
	}

	public void setCurrentMinValue(GregorianCalendar currentMinValue)
	{
		_currentMinValue = currentMinValue;
	}

	public GregorianCalendar getCurrentMaxValue()
	{
		return _currentMaxValue;
	}

	public void setCurrentMaxValue(GregorianCalendar currentMaxValue)
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

	public static DatePropertyDescriptor getCurrentEditedProperty()
	{
		return _currentEditedProperty;
	}

	public static void setCurrentEditedProperty(
			DatePropertyDescriptor currentEditedProperty)
	{
		_currentEditedProperty = currentEditedProperty;
	}

	public boolean isRange()
	{
		return _isRange;
	}

	public void setRange(boolean isRange)
	{
		_isRange = isRange;
	}

	@Override
	public String getStringValue()
	{
		return BaseParser.dateParser.format(_currentMaxValue.getTime());
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

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Document doc = root.getOwnerDocument();
		Element el = doc.createElement(getName());

		if (_isRange)
		{
			if (_isCurrentMinValueDefined)
			{
				Element elMin = doc.createElement("Min");
				elMin.setTextContent(format.format(_currentMinValue.getTime()));
				el.appendChild(elMin);
			}

			if (_isCurrentMaxValueDefined)
			{
				Element elMax = doc.createElement("Max");
				elMax.setTextContent(format.format(_currentMaxValue.getTime()));
				el.appendChild(elMax);
			}
		}
		else
		{
			Element elValue = doc.createElement("Value");
			elValue.setTextContent(format.format(getCurrentValue().getTime()));
			el.appendChild(elValue);
		}

		root.appendChild(el);
	}

	String getStringValue(GregorianCalendar value)
	{
		return _dateFormat.format(value.getTime());
	}

	@Override
	public String getDescription()
	{
		if (_isRange)
		{
			String descr = "";
			if (isCurrentMinValueDefined())
				descr += "от " + getStringValue(_currentMinValue);
			if (isCurrentMaxValueDefined())
			{
				if (isCurrentMinValueDefined())
					descr += " ";
				descr += "до " + getStringValue(_currentMaxValue);
			}

			return descr;
		}
		else
		{
			return getStringValue(_currentMinValue);
		}
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
}
