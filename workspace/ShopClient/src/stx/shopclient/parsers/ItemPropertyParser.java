package stx.shopclient.parsers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.properties.BooleanPropertyDescriptor;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.entity.properties.StringPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;

public class ItemPropertyParser extends BaseParser<PropertyDescriptor>
{
	
	private static final String ITEM_NAME = "Property";
	private static final String NAME_NAME = "Name";
	private static final String TYPE_NAME = "Type";
	private static final String TITLE_NAME = "Title";
	private static final String ORDER_NAME = "Order";
	private static final String VALUE_NAME = "Value";
	private static final String RANGE_NAME = "Range";
	public static final String UNIT_NAME_NAME = "UnitName";
	
	
	public PropertyDescriptor getElement(Element e)
	{
		String propType = super.getValue(e, TYPE_NAME);

		PropertyDescriptor item = null;

		if (propType.equals(DatePropertyDescriptor.TYPE_STRING))
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
					((NumberPropertyDescriptor) item).setCurrentMinValue(super.getValueInt(e, VALUE_NAME));
					((NumberPropertyDescriptor) item).setUnitName(super.getValue(e, UNIT_NAME_NAME));
				}
				else
					if (propType.equals(NumberPropertyDescriptor.TYPE_STRING))
					{
						item = new NumberPropertyDescriptor();
						((NumberPropertyDescriptor) item).setCurrentMinValue(super.getValueDouble(e, VALUE_NAME));
						((NumberPropertyDescriptor) item).setUnitName(super.getValue(e, UNIT_NAME_NAME));
					}
					else
						if (propType.equals(EnumPropertyDescriptor.TYPE_STRING))
						{
							item = new EnumPropertyDescriptor();
							List<EnumValue> enums = getEnumList(e);
							((EnumPropertyDescriptor) item).setValues(enums);
							List<EnumValue> current = new ArrayList<EnumValue>();
							String enumId = super.getValue(e, VALUE_NAME);
							if (enumId != null)
							{
								for(EnumValue el : enums)
								{
									if (enumId.equals(el.getValue()))
									{
										current.add(el);
										break;
									}
								}
							}
							((EnumPropertyDescriptor) item).setCurrentValues(current);
						}
						else
							if (propType.equals(PropertyParser.ARRAY_TYPE))
							{
								item = new EnumPropertyDescriptor();
								((EnumPropertyDescriptor) item).setValues(getArrayList(e));
							}
						else
							if (propType.equals(BooleanPropertyDescriptor.TYPE_STRING))
							{
								item = new BooleanPropertyDescriptor();
								((BooleanPropertyDescriptor)item).setCurrentValue(super.getValueBool(e, VALUE_NAME));
							}
							else
							{
								item = new StringPropertyDescriptor();
								((StringPropertyDescriptor)item).setValue(super.getValue(e, VALUE_NAME));
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
		NodeList ranges = e.getElementsByTagName(RANGE_NAME);
		if (ranges.getLength() <= 0) return items;
		NodeList nl = ranges.item(0).getChildNodes();
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
		NodeList ranges = e.getElementsByTagName(RANGE_NAME);
		if (ranges.getLength() <= 0) return items;
		NodeList nl = ranges.item(0).getChildNodes();
		for (int i = 0; i < nl.getLength(); i++)
		{
			EnumValue item = new EnumValue();
			item.setValue(super.getElementValue((Element) nl.item(i)));
			items.add(item);
		}
		return items;
	}
}
