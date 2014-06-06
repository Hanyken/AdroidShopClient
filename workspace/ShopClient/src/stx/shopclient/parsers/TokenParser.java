package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.Token;

public class TokenParser extends BaseParser<Token>
{
	private final String ITEM_NAME = "Result";
	private final String TOKEN_NAME = "token";
	
	@Override
	public Token getElement(Element e)
	{
		Token token = new Token();
		token.setToken(super.getValue(e, TOKEN_NAME));
		return token;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
