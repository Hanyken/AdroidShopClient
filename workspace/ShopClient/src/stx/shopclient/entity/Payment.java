package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Payment
{
	private long _id;
	private long _number;
	private Date _date;
	private double _sum;
	private int _state;
	private List<Order> _orders = new ArrayList<Order>();
	
	public long getId()
	{
		return _id;
	}
	public void setId(long id)
	{
		_id = id;
	}
	
	public long getNumber()
	{
		return _number;
	}
	public void setNumber(long number)
	{
		_number = number;
	}
	
	public Date getDate()
	{
		return _date;
	}
	public void setDate(Date date)
	{
		_date = date;
	}
	
	public double getSum()
	{
		return _sum;
	}
	public void setSum(double sum)
	{
		_sum = sum;
	}
	
	public int getState()
	{
		return _state;
	}
	public void setState(int state)
	{
		_state = state;
	}
	
	public List<Order> getOrders()
	{
		return _orders;
	}
	public void setOrders(Collection<Order> orders)
	{
		_orders.clear();
		_orders.addAll(orders);
	}
}
