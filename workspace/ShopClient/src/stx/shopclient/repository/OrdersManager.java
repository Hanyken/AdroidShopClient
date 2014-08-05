package stx.shopclient.repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.properties.BooleanPropertyDescriptor;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.entity.properties.StringPropertyDescriptor;

public class OrdersManager
{
	private HashMap<Long, Order> _Orders = new HashMap<Long, Order>();
	private ArrayList<Catalog> _Catalogs;

	public OrdersManager()
	{
		_Catalogs = new ArrayList<Catalog>();
	}

	public void addOrder(Order item)
	{
		_Orders.put(item.getId(), item);
	}

	public void removeOrderItem(long orderId)
	{
		_Orders.remove(orderId);
	}

	public Collection<Order> getOrderItems(long catalogId)
	{
		return _Orders.values();
	}

	public int getOrderItemsCount()
	{
		return _Orders.size();
	}

	public Collection<Catalog> getOrderCatalogs()
	{
		return _Catalogs;
	}

	public void setOrderCatalogs(Collection<Catalog> catalogs)
	{
		_Catalogs.clear();
		_Catalogs.addAll(catalogs);
	}

	public Order getOrderById(long id)
	{
		return _Orders.get(id);
	}

	public Collection<OrderProperty> getOrderProperties(long orderId)
	{
		Order order = _Orders.get(orderId);
		if (order != null)
			return order.getProperties();
		
		return new ArrayList<OrderProperty>();
	}

	public Collection<PropertyDescriptor> getOrderPropertiesAsDescriptiors(
			Collection<PropertyDescriptor> itemOrderProperties, long orderId)
	{
		Collection<OrderProperty> orderItems = getOrderProperties(orderId);

		return getOrderPropertiesAsDescriptiors(itemOrderProperties, orderItems);
	}

	public static Collection<PropertyDescriptor> getOrderPropertiesAsDescriptiors(
			Collection<PropertyDescriptor> itemOrderProperties,
			Collection<OrderProperty> orderItems)
	{
		List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();

		for (PropertyDescriptor prop : itemOrderProperties)
		{
			result.add(prop.cloneProperty());
		}

		for (PropertyDescriptor prop : result)
		{
			for (OrderProperty op : orderItems)
			{
				if (prop.getName().equals(op.getName()))
				{
					if (prop.getClass() == NumberPropertyDescriptor.class)
					{
						((NumberPropertyDescriptor) prop)
								.setCurrentMinValue(Double.parseDouble(op
										.getValue()));
						((NumberPropertyDescriptor) prop)
								.setCurrentValueDefined(true);
						((NumberPropertyDescriptor) prop)
								.setCurrentMinValueDefined(true);
					}
					else if (prop.getClass() == DatePropertyDescriptor.class)
					{
						try
						{
							DateFormat dateParser = new SimpleDateFormat(
									"yyyy-MM-dd'T'HH:mm:ss");
							GregorianCalendar calendar = new GregorianCalendar();
							calendar.setTime(dateParser.parse(op.getValue()));
							((DatePropertyDescriptor) prop)
									.setCurrentMinValue(calendar);
							((DatePropertyDescriptor) prop)
									.setCurrentValueDefined(true);
						}
						catch (Exception ex)
						{
						}
					}
					else if (prop.getClass() == BooleanPropertyDescriptor.class)
					{
						((BooleanPropertyDescriptor) prop)
								.setCurrentValue(Boolean.parseBoolean(op
										.getValue()));
					}
					else if (prop.getClass() == EnumPropertyDescriptor.class)
					{
						List<EnumValue> enumValues = new ArrayList<EnumValue>();
						String[] values = op.getValue().split(
								EnumPropertyDescriptor.SEPARATE_STRING);
						for (int i = 0; i < values.length; i++)
						{
							for (EnumValue val : ((EnumPropertyDescriptor) prop)
									.getValues())
							{
								if (val.getName().equals(values[i]))
								{
									enumValues.add(val);
								}
							}
						}
						((EnumPropertyDescriptor) prop)
								.setCurrentValues(enumValues);

					}
					else if (prop.getClass() == StringPropertyDescriptor.class)
					{
						((StringPropertyDescriptor) prop).setValue(op
								.getValue());
					}
				}
			}
		}
		return result;
	}

	public static List<OrderProperty> getOrderPropertiesFromDescriptors(
			Collection<PropertyDescriptor> properties)
	{
		ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();

		for (PropertyDescriptor el : properties)
		{
			OrderProperty item = new OrderProperty();
			item.setName(el.getName());
			item.setValue(el.getValueWithoutUnit());
			items.add(item);
		}

		return items;
	}
}
