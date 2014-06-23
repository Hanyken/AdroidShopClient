package stx.shopclient.parsers;

import org.w3c.dom.Element;
import stx.shopclient.entity.Payment;

public class PaymentParser extends BaseParser<Payment>
{
	private final String ITEM_NAME = "Payment";
	private final String ID_NAME = "Id";
	private final String NUMBER_NAME = "Number";
	private final String CREATE_DATE_NAME = "CreateDate";
	private final String SUM_NAME = "Sum";
	private final String PAYMENT_STATE_NAME = "PaymentState";

	@Override
	public Payment getElement(Element e)
	{
		Payment item = new Payment();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setNumber(super.getValueLong(e, NUMBER_NAME));
		item.setDate(super.getValueDate(e, CREATE_DATE_NAME));
		item.setSum(super.getValueDouble(e, SUM_NAME));
		item.setState(super.getValueInt(e, PAYMENT_STATE_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
