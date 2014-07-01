package stx.shopclient.entity.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EnumPropertyDescriptor extends PropertyDescriptor
{
	public static final String TYPE_STRING = "Enum";
	public static final String SEPARATE_STRING = "|";

	private List<EnumValue> _values = new ArrayList<EnumValue>();
	private List<EnumValue> _currentValues = new ArrayList<EnumValue>();

	public List<EnumValue> getValues()
	{
		return _values;
	}

	public void setValues(List<EnumValue> values)
	{
		_values = values;
	}

	public List<EnumValue> getCurrentValues()
	{
		return _currentValues;
	}

	public void setCurrentValues(List<EnumValue> currentValues)
	{
		_currentValues = currentValues;
	}

	public static class EnumValue implements Serializable
	{
		private String _value;
		private String _name;

		public EnumValue()
		{
		}

		public EnumValue(String value, String name)
		{
			_value = value;
			_name = name;
		}

		public String getValue()
		{
			return _value;
		}

		public void setValue(String value)
		{
			_value = value;
		}

		public String getName()
		{
			return _name;
		}

		public void setName(String name)
		{
			_name = name;
		}
	}

	@Override
	public void clear()
	{
		_currentValues.clear();
	}

	@Override
	public String getStringValue()
	{
		String value = "";
		if (_currentValues != null && _currentValues.size() > 0)
		{
			for (EnumValue el : _currentValues)
			{
				if (!value.equals(""))
					value += SEPARATE_STRING;
				value += el.getName();
			}
		}
		return value;
	}

	@Override
	public boolean isValueDefined()
	{
		return _currentValues.size() > 0;
	}

	@Override
	public void appendSearchPropertyXml(Element root)
	{
		if (!isValueDefined())
			return;

		Document doc = root.getOwnerDocument();
		Element el = doc.createElement(getName());

		Element elRange = doc.createElement("Range");

		for (EnumValue enumValue : _currentValues)
		{
			Element elValue = doc.createElement("Value");
			elValue.setTextContent(enumValue.getValue());
			elRange.appendChild(elValue);
		}
		
		el.appendChild(elRange);		

		root.appendChild(el);
	}
}
