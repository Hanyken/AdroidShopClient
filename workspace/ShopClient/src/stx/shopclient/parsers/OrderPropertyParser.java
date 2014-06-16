package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.OrderProperty;

public class OrderPropertyParser extends BaseParser<OrderProperty>
{
	public static final String ITEM_NAME = "Property";
	private final String NAME_NAME = "Name";
	private final String VALUE_NAME = "Value";
	
	private long _orderId;
	
	public OrderPropertyParser(long orderId)
	{
		_orderId = orderId;
	}
	
	@Override
	public OrderProperty getElement(Element e)
	{
		OrderProperty item = new OrderProperty();
		
		item.setName(super.getValue(e, NAME_NAME));
		item.setValue(super.getValue(e, VALUE_NAME));
		item.setOrderId(_orderId);
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	} 

}
