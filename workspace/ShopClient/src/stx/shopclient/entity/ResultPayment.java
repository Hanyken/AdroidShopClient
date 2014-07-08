package stx.shopclient.entity;

public class ResultPayment extends ResultEntity
{
	private long _paymentNumber;
	
	public long getPaymentNumber()
	{
		return _paymentNumber;
	}
	
	public void setPaymentNumber(long paymentNumber)
	{
		_paymentNumber = paymentNumber;
	}
}
