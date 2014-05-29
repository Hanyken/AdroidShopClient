package stx.shopclient.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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

import android.util.Log;

public abstract class BaseParser<T>
{

	private final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private final DateFormat timeParser = new SimpleDateFormat("HH:mm:ss");

	public BaseParser()
	{
	}
	
	public abstract T getElement(Element e);

	protected abstract String getElementName();

	
	
	public Collection<T> getElements(String xmlString)
	{
		Document doc = getDomElement(xmlString);
		NodeList nl = doc.getElementsByTagName(getElementName());
		return getElements(nl);
	}

	public Collection<T> getElements(NodeList nl)
	{
		ArrayList<T> items = new ArrayList<T>();
		for (int i = 0; i < nl.getLength(); i++)
		{
			try
			{
				Element e = (Element) nl.item(i);
				items.add(getElement(e));
			}
			catch (Exception ex)
			{
				Log.w("MyException", ex.getMessage());
			}
		}

		return items;
	}

	
	
	
	protected Document getDomElement(String xml)
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

	protected String getValue(Element item, String str)
	{
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	protected Boolean getValueBool(Element item, String str)
	{
		String value = getValue(item, str);
		return ("1".equals(value));
	}

	protected Integer getValueInt(Element item, String str)
	{
		String value = getValue(item, str);
		return Integer.parseInt(value);
	}

	protected Long getValueLong(Element item, String str)
	{
		String value = getValue(item, str);
		return Long.parseLong(value);
	}

	protected Double getValueDouble(Element item, String str)
	{
		String value = getValue(item, str);
		return Double.parseDouble(value);
	}

	protected Date getValueDate(Element item, String str)
	{
		Date date = null;
		try
		{
			String value = getValue(item, str);
			date = dateParser.parse(value);
		}
		catch (Exception ex)
		{
			Log.w("Date", ex.getMessage());
		}
		return date;
	}
	
	protected Date getValueTime(Element item, String str)
	{
		Date date = null;
		try
		{
			String value = getValue(item, str);
			date = timeParser.parse(value);
		}
		catch (Exception ex)
		{
			Log.w("Time", ex.getMessage());
		}
		return date;
	}

	protected final String getElementValue(Node elem)
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

	protected NodeList getChildElements(Node elem)
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
