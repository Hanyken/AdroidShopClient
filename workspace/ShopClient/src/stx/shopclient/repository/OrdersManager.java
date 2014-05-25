package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;

import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;

public class OrdersManager 
{
	private ArrayList<Order> _Orders;
	private ArrayList<OrderProperty> _Properties;
	
	public OrdersManager()
	{
		_Orders = new ArrayList<Order>();
		_Properties = new ArrayList<OrderProperty>();
	}
	
	public void addOrder(long itemId, Collection<OrderProperty> properties)
	{
		Order item = new Order();
		
		item.setId(_Orders.size() +1);
		item.setItemId(itemId);
		
		for(OrderProperty el : properties)
		{
			el.setOrderId(item.getId());
			_Properties.add(el);
		}
		
		_Orders.add(item);
	}
	
	public Collection<Order> getOrders()
	{
		return _Orders;
	}
	public Collection<OrderProperty> getOrderProperties(long orderId)
	{
		ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();
		for(OrderProperty el : _Properties)
		{
			if (el.getOrderId() == orderId)
			{
				items.add(el);
			}
		}
		return items;
	}

}
