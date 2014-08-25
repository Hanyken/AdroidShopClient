package stx.shopclient.entity;

import java.util.Date;

public class Payment
{
	public static final int STATE_ACCEPTED = 1;
	
	private long _id;
	private long _number;
	private Date _createDate;
	private double _sum;
	private int _state;
	private Date _payDate;
	private int _orderCount;
	
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
	
	public Date getCreateDate()
	{
		return _createDate;
	}
	public void setCreateDate(Date createDate)
	{
		_createDate = createDate;
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
	
	public Date getPayDate()
	{
		return _payDate;
	}
	public void setPayDate(Date payDate)
	{
		_payDate =  payDate;
	}
	
	public int getOrderCount()
	{
		return _orderCount;
	}
	public void setOrderCount(int orderCount)
	{
		_orderCount = orderCount;
	}
}
