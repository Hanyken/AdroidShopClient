package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.MessageCountResult;

public class MessageCountParser extends BaseParser<MessageCountResult>
{
	private final String COUNT_NAME = "Count";
	private final String LAST_ID_NAME = "LastId";

	@Override
	public MessageCountResult getElement(Element e)
	{
		MessageCountResult item = new MessageCountResult();
		
		item.setCount(super.getValueLong(e, COUNT_NAME));
		item.setLastId(super.getValueLong(e, LAST_ID_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ResultParser.ITEM_NAME;
	}

}
