package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.ResultEntity;

public class ResultParser extends BaseParser<ResultEntity>
{
	public static final String ITEM_NAME = "Result";
	public static String CODE_NAME = "Code";
	
	@Override
	public ResultEntity getElement(Element e)
	{
		ResultEntity item = new ResultEntity();
		
		item.setCode(super.getValueInt(e, CODE_NAME));
		
		return item;
	}
	

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
