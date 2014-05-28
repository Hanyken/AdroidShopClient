package stx.shopclient.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BaseParser
{

	private final SimpleDateFormat dateParser = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	public BaseParser()
	{

	}

	public Document getDomElement(String xml)
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setCoalescing(true);
		try
		{

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		}
		catch (ParserConfigurationException e)
		{
			return null;
		}
		catch (SAXException e)
		{
			return null;
		}
		catch (IOException e)
		{
			return null;
		}

		return doc;

	}

	public String getValue(Element item, String str)
	{
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public Boolean getValueBool(Element item, String str)
	{
		String value = getValue(item, str);
		return ("1".equals(value));
	}

	public Integer getValueInt(Element item, String str)
	{
		String value = getValue(item, str);
		return Integer.parseInt(value);
	}

	public Long getValueLong(Element item, String str)
	{
		String value = getValue(item, str);
		return Long.parseLong(value);
	}

	public Double getValueDouble(Element item, String str)
	{
		String value = getValue(item, str);
		return Double.parseDouble(value);
	}

	public Date getValueDate(Element item, String str)
	{
		try
		{
			String value = getValue(item, str);
			return dateParser.parse(value);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public final String getElementValue(Node elem)
	{
		Node child;
		if (elem != null)
		{
			if (elem.hasChildNodes())
			{
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling())
				{
					if (child.getNodeType() == Node.TEXT_NODE)
					{
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	public NodeList getChildElements(Node elem)
	{
		if (elem != null)
		{
			if (elem.hasChildNodes())
			{
				return elem.getChildNodes();
			}
		}
		return null;
	}
}
