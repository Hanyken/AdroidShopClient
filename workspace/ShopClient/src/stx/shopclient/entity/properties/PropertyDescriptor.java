package stx.shopclient.entity.properties;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.SerializationUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class PropertyDescriptor implements Serializable
{
	private String _type;
	private String _name;
	private String _title;
	private boolean _required;
	private int _order;
	private boolean _multiselect;
	private boolean _quickSearch;
	private boolean _isValueDefined;

	public abstract void clear();

	public abstract String getStringValue();

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public String getTitle()
	{
		return _title;
	}

	public void setTitle(String title)
	{
		_title = title;
	}

	public String getType()
	{
		return _type;
	}

	public void setType(String type)
	{
		_type = type;
	}

	public Boolean getRequired()
	{
		return _required;
	}

	public void setRequired(Boolean required)
	{
		_required = required;
	}

	public Integer getOrder()
	{
		return _order;
	}

	public void setOrder(Integer order)
	{
		_order = order;
	}

	public Boolean getMultiselect()
	{
		return _multiselect;
	}

	public void setMultiselect(Boolean multiselect)
	{
		_multiselect = multiselect;
	}

	public Boolean getQuickSearch()
	{
		return _quickSearch;
	}

	public void setQuickSearch(Boolean quickSearch)
	{
		_quickSearch = quickSearch;
	}

	public boolean isValueDefined()
	{
		return _isValueDefined;
	}

	public void setValueDefined(boolean isValueDefined)
	{
		_isValueDefined = isValueDefined;
	}

	public PropertyDescriptor cloneProperty()
	{
		return (PropertyDescriptor) SerializationUtils.clone(this);
	}

	public void appendSearchPropertyXml(Element root)
	{

	}

	public static String getSearchPropertiesXML(
			Collection<PropertyDescriptor> properties)
	{
		try
		{
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();

			Element searchEl = doc.createElement("Search");

			for (PropertyDescriptor prop : properties)
				prop.appendSearchPropertyXml(searchEl);

			doc.appendChild(searchEl);

			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		}
		catch (Throwable ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public String getDescription()
	{
		return "";
	}
}
