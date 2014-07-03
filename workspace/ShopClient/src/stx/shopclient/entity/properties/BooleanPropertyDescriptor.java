package stx.shopclient.entity.properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BooleanPropertyDescriptor extends PropertyDescriptor {
	public static final String TYPE_STRING = "Boolean";
	
	private boolean _currentValue;

	public boolean getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(boolean currentValue) {
		_currentValue = currentValue;
	}

	@Override
	public void clear() {
		_currentValue = false;
	}

	@Override
	public String getStringValue() {
		return Boolean.toString(_currentValue);
	}
	@Override
	public String getValueWithoutUnit()
	{
		return getStringValue();
	}
	
	@Override
	public boolean isValueDefined()
	{	
		return true;
	}
	
	@Override
	public void appendSearchPropertyXml(Element root)
	{
		if (!isValueDefined())
			return;

		Document doc = root.getOwnerDocument();
		Element el = doc.createElement(getName());

		Element elValue = doc.createElement("Value");
		elValue.setTextContent(Boolean.toString(_currentValue));
		el.appendChild(elValue);

		root.appendChild(el);
	}
}
