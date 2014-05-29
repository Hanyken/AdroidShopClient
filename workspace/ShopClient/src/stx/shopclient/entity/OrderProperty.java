package stx.shopclient.entity;

public class OrderProperty extends KeyValue<String>
{
	private long _orderId;
	
	public long getOrderId() {
		return _orderId;
	}
	public void setOrderId(long orderId) {
		_orderId = orderId;
	}
}
