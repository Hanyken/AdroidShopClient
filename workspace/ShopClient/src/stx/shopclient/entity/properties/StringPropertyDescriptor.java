package stx.shopclient.entity.properties;

import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import stx.shopclient.utils.StringUtils;

public class StringPropertyDescriptor extends PropertyDescriptor
{
	public static final String TYPE_STRING = "String";

	private String _value = "";

	@Override
	public void clear()
	{
		_value = "";
	}

	public String getValue()
	{
		return _value;
	}

	public void setValue(String value)
	{
		_value = value;
	}

	@Override
	public String getStringValue()
	{
		return _value;
	}

	@Override
	public boolean isValueDefined()
	{
		return !StringUtils.isNullOrEmpty(_value);
	}

	@Override
	public void appendSearchPropertyXml(Element root)
	{
		if (!isValueDefined())
			return;

		Document doc = root.getOwnerDocument();
		Element el = doc.createElement(getName());

		Element elValue = doc.createElement("Value");
		elValue.setTextContent(_value);
		el.appendChild(elValue);

		root.appendChild(el);
	}
}
