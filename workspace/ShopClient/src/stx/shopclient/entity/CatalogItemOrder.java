package stx.shopclient.entity;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.entity.properties.PropertyDescriptor;

public class CatalogItemOrder {
	private long _itemId;
	private long _orderId;
	private List<PropertyDescriptor> _orderProperties = new ArrayList<PropertyDescriptor>();
	public CatalogItem Item;
	
	public long getItemId() {
		return _itemId;
	}
	public void setItemId(long itemId) {
		_itemId = itemId;
	}
	public List<PropertyDescriptor> getOrderProperties() {
		return _orderProperties;
	}
	public void setOrderProperties(List<PropertyDescriptor> orderProperties) {
		_orderProperties = orderProperties;
	}
	public long getOrderId() {
		return _orderId;
	}
	public void setOrderId(long orderId) {
		_orderId = orderId;
	}
}
