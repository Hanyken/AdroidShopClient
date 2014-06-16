package stx.shopclient.entity;

public class OrderProperty extends KeyValue<String>
{
	public static final String COUNT_PROPERTY_NAME = "Count";

	private long _orderId;

	public long getOrderId()
	{
		return _orderId;
	}

	public void setOrderId(long orderId)
	{
		_orderId = orderId;
	}
}
