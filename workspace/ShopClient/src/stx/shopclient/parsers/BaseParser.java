package stx.shopclient.parsers;

import java.io.FileInputStream;
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

	public static final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat timeParser = new SimpleDateFormat("HH:mm:ss");

	public BaseParser()
	{}

	public abstract T getElement(Element e);

	protected abstract String getElementName();

	public Collection<T> parseFile(String filePath)
	{
		Document doc = getDomElementFromFile(filePath);
		NodeList nl = doc.getElementsByTagName(getElementName());
		return getElements(nl);
	}

	public Collection<T> parseString(String xmlString)
	{
		Document doc = getDomElement(xmlString);
		NodeList nl = doc.getElementsByTagName(getElementName());
		return getElements(nl);
	}
	
	public Collection<T> parseElement(Element e)
	{
		NodeList nl = e.getElementsByTagName(getElementName());
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
				//Log.w("MyException", ex.getMessage());
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
			// is.setEncoding("UTF-8");
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

	protected Document getDomElementFromFile(String path)
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setCoalescing(true);
		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(new FileInputStream(path), "UTF-8");
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

	protected boolean getExistsValue(Element e, String str)
	{
		String value = getValue(e, str);
		return !value.equals(null) && value != null && !value.equals("");
	}
	
	protected String getValue(Element e, String str)
	{
		NodeList n = e.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	protected Boolean getValueBool(Element e, String str)
	{
		String value = getValue(e, str);
		return ("1".equals(value));
	}

	protected Integer getValueInt(Element e, String str)
	{
		String value = getValue(e, str);
		if (value.equals(null) || value == null || value.equals(""))
			value = "0";
		return Integer.parseInt(value);
	}

	protected Long getValueLong(Element e, String str)
	{
		String value = getValue(e, str);
		if (value.equals(null) || value == null || value.equals(""))
			value = "0";
		return Long.parseLong(value);
	}

	protected Double getValueDouble(Element e, String str)
	{
		String value = getValue(e, str);
		if (value.equals(null) || value == null || value.equals(""))
			value = "0";
		return Double.parseDouble(value);
	}

	protected Date getValueDate(Element e, String str)
	{
		Date date = null;
		try
		{
			String value = getValue(e, str);
			date = dateParser.parse(value);
		}
		catch (Exception ex)
		{
			Log.w("Date", ex.getMessage());
		}
		return date;
	}

	protected Date getValueTime(Element e, String str)
	{
		Date date = null;
		try
		{
			String value = getValue(e, str);
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
