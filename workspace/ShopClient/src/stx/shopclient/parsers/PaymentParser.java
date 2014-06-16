package stx.shopclient.parsers;

import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.Order;
import stx.shopclient.entity.Payment;

public class PaymentParser extends BaseParser<Payment>
{
	private final String ITEM_NAME = "Payment";
	private final String ID_NAME = "Id";
	private final String NUMBER_NAME = "Number";
	private final String CREATE_DATE_NAME = "CreateDate";
	private final String SUM_NAME = "Sum";
	private final String PAYMENT_STATE_NAME = "PaymentState";
	private final String ORDERS_NAME = "Orders";

	@Override
	public Payment getElement(Element e)
	{
		Payment item = new Payment();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setNumber(super.getValueLong(e, NUMBER_NAME));
		item.setDate(super.getValueDate(e, CREATE_DATE_NAME));
		item.setSum(super.getValueDouble(e, SUM_NAME));
		item.setState(super.getValueInt(e, PAYMENT_STATE_NAME));
		
		NodeList pNl = e.getElementsByTagName(ORDERS_NAME);
		if (pNl.getLength() > 0)
		{
			NodeList itemsList = ((Element)pNl.item(0)).getElementsByTagName(OrderParser.ITEM_NAME);
			OrderParser parser = new OrderParser();
			Collection<Order> items = parser.getElements(itemsList);
			item.setOrders(items);
		}
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
