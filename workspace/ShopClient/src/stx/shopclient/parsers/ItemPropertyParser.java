package stx.shopclient.parsers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.CatalogItemProperty;
import stx.shopclient.entity.properties.BooleanPropertyDescriptor;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.entity.properties.StringPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;

public class ItemPropertyParser extends BaseParser<PropertyDescriptor>
{
	public String TEST;
	
	private final String ITEM_NAME = "Property";
	private final String NAME_NAME = "Name";
	private final String TYPE_NAME = "Type";
	private final String TITLE_NAME = "Title";
	//private final String SEARCHABLE_NAME = "Searchable";
	//private final String QUICK_SEARCHABLE_NAME = "QuickSearch";
	private final String SHORT_LIST_NAME = "ShortList";
	private final String ORDER_NAME = "Order";
	private final String VALUE_NAME = "Value";
	private final String RANGE_NAME = "Range";
	
	
	public ItemPropertyParser()
	{
		TEST = "<Properties><Property><Name>Price</Name><Type>Numeric</Type><Title>����</Title><Searchable>1</Searchable><QuickSearch>1</QuickSearch><ShortList>0</ShortList><Order>1</Order><Value>30000.00000</Value></Property>";
		TEST += "<Property><Name>String_Key</Name><Type>String</Type><Title>������</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>2</Order><Value>����� �� �����</Value></Property>";
		TEST += "<Property><Name>Date_Key</Name><Type>Date</Type><Title>����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>2014-05-26 18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Time_Key</Name><Type>Time</Type><Title>�����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Number_Key</Name><Type>Integer</Type><Title>�����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>4</Order><Value>100.00000</Value></Property>";
		TEST += "<Property><Name>Bool_Key</Name><Type>Boolean</Type><Title>���������� ��������</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>5</Order><Value>1</Value></Property></Properties>";
	}
	
	public PropertyDescriptor getElement(Element e)
	{
		String propType = super.getValue(e, TYPE_NAME);

		PropertyDescriptor item = null;

		if (propType.equals(PropertyParser.DATE_TYPE))
		{
			item = new DatePropertyDescriptor();
			GregorianCalendar val = new GregorianCalendar();
			val.setTime(super.getValueDate(e, VALUE_NAME));
			((DatePropertyDescriptor) item).setCurrentMaxValue(val);
		}
		else
			if (propType.equals(PropertyParser.TIME_TYPE))
			{
				item = new DatePropertyDescriptor();
				GregorianCalendar val = new GregorianCalendar();
				val.setTime(super.getValueDate(e, VALUE_NAME));
				((DatePropertyDescriptor) item).setCurrentMaxValue(val);
			}
			else
				if (propType.equals(PropertyParser.INTEGER_TYPE))
				{
					item = new NumberPropertyDescriptor();
					((NumberPropertyDescriptor) item).setCurrentMaxValue(super.getValueInt(e, VALUE_NAME));
				}
				else
					if (propType.equals(PropertyParser.NUMERIC_TYPE))
					{
						item = new NumberPropertyDescriptor();
						((NumberPropertyDescriptor) item).setCurrentMaxValue(super.getValueDouble(e, VALUE_NAME));
					}
					else
						if (propType.equals(PropertyParser.ENUM_TYPE))
						{
							item = new EnumPropertyDescriptor();
							((EnumPropertyDescriptor) item).setValues(getEnumList(e));
						}
						else
							if (propType.equals(PropertyParser.ARRAY_TYPE))
							{
								item = new EnumPropertyDescriptor();
								((EnumPropertyDescriptor) item).setValues(getArrayList(e));
							}
						else
							if (propType.equals(PropertyParser.BOOL_TYPE))
							{
								item = new BooleanPropertyDescriptor();
							}
							else
							{
								item = new StringPropertyDescriptor();
							}
		
		item.setName(super.getValue(e, NAME_NAME));
		item.setType(super.getValue(e, TYPE_NAME));
		item.setTitle(super.getValue(e, TITLE_NAME));
		//item.setShortList(super.getValueBool(e, SHORT_LIST_NAME));
		item.setOrder(super.getValueInt(e, ORDER_NAME));
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
	
	private List<EnumValue> getEnumList(Element e)
	{
		List<EnumValue> items = new ArrayList<EnumValue>();
		NodeList nl = e.getElementsByTagName(RANGE_NAME);
		for (int i = 0; i < nl.getLength(); i++)
		{
			EnumValue item = new EnumValue();
			item.setName(super.getValue((Element) nl.item(i), NAME_NAME));
			item.setValue(super.getValue((Element) nl.item(i), VALUE_NAME));
			items.add(item);
		}
		return items;
	}
	private List<EnumValue> getArrayList(Element e)
	{
		List<EnumValue> items = new ArrayList<EnumValue>();
		NodeList nl = e.getElementsByTagName(RANGE_NAME);
		for (int i = 0; i < nl.getLength(); i++)
		{
			EnumValue item = new EnumValue();
			item.setValue(super.getValue((Element) nl.item(i), VALUE_NAME));
			items.add(item);
		}
		return items;
	}
}