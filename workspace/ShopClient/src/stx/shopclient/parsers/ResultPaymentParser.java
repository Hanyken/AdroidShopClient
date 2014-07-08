package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.ResultPayment;

public class ResultPaymentParser extends BaseParser<ResultPayment>
{
	private final String ITEM_NAME = "Result";
	private final String CODE_NAME = "Code";
	private final String PAYMENT_NUMBER_NAME = "Number";
	
	@Override
	public ResultPayment getElement(Element e)
	{
		ResultPayment item = new ResultPayment();
		item.setCode(super.getValueInt(e, CODE_NAME));
		item.setPaymentNumber(super.getValueLong(e, PAYMENT_NUMBER_NAME));
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
