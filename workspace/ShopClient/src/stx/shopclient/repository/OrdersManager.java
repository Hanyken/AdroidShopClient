package stx.shopclient.repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

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
	private ArrayList<Order> _Orders;
	private ArrayList<OrderProperty> _Properties;

	public OrdersManager()
	{
		_Orders = new ArrayList<Order>();
		_Properties = new ArrayList<OrderProperty>();
	}

	public void addOrderItem(long itemId, Collection<OrderProperty> properties)
	{
		Order item = new Order();

		item.setId(_Orders.size() + 1);
		item.setItemId(itemId);

		for (OrderProperty el : properties)
		{
			el.setOrderId(item.getId());
			_Properties.add(el);
		}

		_Orders.add(item);
	}

	public void removeOrderItem(long orderId)
	{
		for (Order order : _Orders)
		{
			if (order.getId() == orderId)
			{
				_Orders.remove(order);
				break;
			}
		}
	}

	public Collection<Order> getOrderItems()
	{
		return _Orders;
	}

	public int getOrderItemsCount()
	{
		return _Orders.size();
	}

	public Collection<OrderProperty> getOrderProperties(long orderId)
	{
		ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();
		for (OrderProperty el : _Properties)
		{
			if (el.getOrderId() == orderId)
			{
				items.add(el);
			}
		}
		return items;
	}
	
	public Collection<PropertyDescriptor> getOrderPropertiesAsDescriptiors(Collection<PropertyDescriptor> itemOrderProperties, long orderId)
	{
		Collection<OrderProperty> orderItems = getOrderProperties(orderId);
		for(PropertyDescriptor prop : itemOrderProperties)
		{
			for(OrderProperty op : orderItems)
			{
				if (prop.getName().equals(op.getName()))
				{
					if (prop.getClass() == NumberPropertyDescriptor.class)
					{
						((NumberPropertyDescriptor)prop).setCurrentMaxValue(Double.parseDouble(op.getValue()));
					}
					else if (prop.getClass() == DatePropertyDescriptor.class)
					{
						try
						{
						DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						GregorianCalendar calendar = new GregorianCalendar();
						calendar.setTime(dateParser.parse(op.getValue()));
						((DatePropertyDescriptor)prop).setCurrentMaxValue(calendar);
						}
						catch(Exception ex){}
					}
					else if (prop.getClass() == BooleanPropertyDescriptor.class)
					{
						((BooleanPropertyDescriptor)prop).setCurrentValue(Boolean.parseBoolean(op.getValue()));
					}
					else if (prop.getClass() == EnumPropertyDescriptor.class)
					{
						List<EnumValue> enumValues = new ArrayList<EnumValue>();
						String[] values = op.getValue().split(EnumPropertyDescriptor.SEPARATE_STRING);
						for(int i=0; i< values.length; i++)
						{
							for(EnumValue val : ((EnumPropertyDescriptor)prop).getValues())
							{
								if (val.getName().equals(values[i]))
								{
									enumValues.add(val);
								}
							}
						}
						((EnumPropertyDescriptor)prop).setCurrentValues(enumValues);
						
					}
					else if (prop.getClass() == StringPropertyDescriptor.class)
					{
						((StringPropertyDescriptor)prop).setValue(op.getValue());
					}
				}	
			}
		}
		return itemOrderProperties;
	}

}
