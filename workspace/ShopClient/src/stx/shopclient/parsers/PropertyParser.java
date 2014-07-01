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

public class PropertyParser extends BaseParser<PropertyDescriptor>
{
	private final String ITEM_NAME = "Property";
	private final String NAME_NAME = "Name";
	private final String TYPE_NAME = "Type";
	private final String TITLE_NAME = "Title";
	private final String ORDER_NAME = "Order";
	private final String VALUE_NAME = "Value";
	private final String RANGE_NAME = "Range";
	private final String REQUIRED_NAME = "Required";
	private final String MILTISELECT_NAME = "Multiselect";
	private final String QUICK_SEARCH_NAME = "QuickSearch";

	private final String MAX_NAME = "Max";
	private final String MIN_NAME = "Min";

	private final String ENUM_NAME = "Enum";

	public static final String TIME_TYPE = "Time";
	public static final String INTEGER_TYPE = "Integer";
	public static final String ARRAY_TYPE = "Array";

	private boolean _rangeNeeded = true;
	
	public PropertyParser()
	{}
	public PropertyParser(boolean rangeNeeded)
	{
		_rangeNeeded = rangeNeeded;
	}

	public PropertyDescriptor getElement(Element e)
	{
		String propType = super.getValue(e, TYPE_NAME);

		PropertyDescriptor item = null;

		if (propType.equals(DatePropertyDescriptor.TYPE_STRING))
		{
			item = new DatePropertyDescriptor();
			NodeList nl1 = e.getElementsByTagName(VALUE_NAME);
			if (nl1.getLength() > 0)
			{
				Element el = (Element) nl1.item(0);
				GregorianCalendar max = new GregorianCalendar();
				max.setTime(super.getValueDate(el, MAX_NAME));
				((DatePropertyDescriptor) item).setCurrentMaxValue(max);
				GregorianCalendar min = new GregorianCalendar();
				min.setTime(super.getValueDate(el, MIN_NAME));
				((DatePropertyDescriptor) item).setCurrentMinValue(min);
			}
		}
		else
			if (propType.equals(TIME_TYPE))
			{
				item = new DatePropertyDescriptor();
				NodeList nl2 = e.getElementsByTagName(VALUE_NAME);
				if (nl2.getLength() > 0)
				{
					Element el = (Element) nl2.item(0);
					GregorianCalendar max = new GregorianCalendar();
					max.setTime(super.getValueDate(el, MAX_NAME));
					((DatePropertyDescriptor) item).setMaxValue(max);
					GregorianCalendar min = new GregorianCalendar();
					min.setTime(super.getValueDate(el, MIN_NAME));
					((DatePropertyDescriptor) item).setMinValue(min);
				}
			}
			else
				if (propType.equals(INTEGER_TYPE))
				{
					item = new NumberPropertyDescriptor();
					NodeList nl3 = e.getElementsByTagName(VALUE_NAME);
					if (nl3.getLength() > 0)
					{
						Element el = (Element) nl3.item(0);
						((NumberPropertyDescriptor) item)
								.setMaxValue(super.getValueInt(el,
										MAX_NAME));
						((NumberPropertyDescriptor) item)
								.setMinValue(super.getValueInt(el,
										MIN_NAME));
						
							((NumberPropertyDescriptor) item).setRange(_rangeNeeded);
							((NumberPropertyDescriptor) item).setFloat(false);
							((NumberPropertyDescriptor) item).setUnitName(super.getValue(e, ItemPropertyParser.UNIT_NAME_NAME));
					}
				}
				else
					if (propType.equals(NumberPropertyDescriptor.TYPE_STRING))
					{
						item = new NumberPropertyDescriptor();
						NodeList nl4 = e.getElementsByTagName(VALUE_NAME);
						if (nl4.getLength() > 0)
						{
							Element el = (Element) nl4.item(0);
							((NumberPropertyDescriptor) item)
									.setMaxValue(super.getValueDouble(
											el, MAX_NAME));
							((NumberPropertyDescriptor) item)
									.setMinValue(super.getValueDouble(
											el, MIN_NAME));
							
								((NumberPropertyDescriptor) item).setRange(_rangeNeeded);
								((NumberPropertyDescriptor) item).setUnitName(super.getValue(e, ItemPropertyParser.UNIT_NAME_NAME));
						}
					}
					else
						if (propType.equals(EnumPropertyDescriptor.TYPE_STRING))
						{
							item = new EnumPropertyDescriptor();
							NodeList nl5 = e.getElementsByTagName(RANGE_NAME);
							if (nl5.getLength() > 0)
							{
								((EnumPropertyDescriptor) item)
										.setValues(getEnumList((Element) nl5
												.item(0)));
							}
						}
						else
							if (propType.equals(ARRAY_TYPE))
							{
								item = new EnumPropertyDescriptor();
								NodeList nl5 = e.getElementsByTagName(RANGE_NAME);
								if (nl5.getLength() > 0)
								{
									((EnumPropertyDescriptor) item)
											.setValues(getArrayList((Element) nl5
													.item(0)));
								}
							}
						else
							if (propType.equals(BooleanPropertyDescriptor.TYPE_STRING))
							{
								item = new BooleanPropertyDescriptor();
							}
							else
							{
								item = new StringPropertyDescriptor();
							}

		item.setName(super.getValue(e, NAME_NAME));
		item.setType(propType);
		item.setTitle(super.getValue(e, TITLE_NAME));
		item.setOrder(super.getValueInt(e, ORDER_NAME));
		item.setRequired(super.getValueBool(e, REQUIRED_NAME));
		item.setMultiselect(super.getValueBool(e, MILTISELECT_NAME));
		item.setQuickSearch(super.getValueBool(e, QUICK_SEARCH_NAME));

		return item;
	}

	protected String getElementName()
	{
		return ITEM_NAME;
	}
	
	private List<EnumValue> getEnumList(Element e)
	{
		List<EnumValue> items = new ArrayList<EnumValue>();
		NodeList nl = e.getElementsByTagName(ENUM_NAME);
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
		NodeList nl = e.getElementsByTagName(ENUM_NAME);
		for (int i = 0; i < nl.getLength(); i++)
		{
			EnumValue item = new EnumValue();
			item.setValue(super.getValue((Element) nl.item(i), VALUE_NAME));
			items.add(item);
		}
		return items;
	}
}
