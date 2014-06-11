package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.UpdateResultEntity;

public class UpdateResultParser extends BaseParser<UpdateResultEntity>
{
	private final String ITEM_NAME = "Result";
	private final String CODE_NAME = "Code";
	
	@Override
	public UpdateResultEntity getElement(Element e)
	{
		UpdateResultEntity token = new UpdateResultEntity();
		token.setCode(super.getValueInt(e, CODE_NAME));
		return token;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
