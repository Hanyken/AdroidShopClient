package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.ActionType;

public class ActionTypeParser extends BaseParser<ActionType>
{
	private static final String ITEM_NAME = "ActionType";
	private static final String ID_NAME = "Id";
	private static final String NAME_NAME = "Name";
	
	@Override
	public ActionType getElement(Element e)
	{
		ActionType item = new ActionType();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
