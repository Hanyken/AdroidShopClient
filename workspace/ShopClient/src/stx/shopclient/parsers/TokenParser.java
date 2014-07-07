package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.Token;

public class TokenParser extends BaseParser<Token>
{
	private final String ITEM_NAME = "Result";
	private final String CODE_NAME = "Code";
	private final String TOKEN_NAME = "token";
	private final String BEG_DATE_NAME = "BegDate";
	private final String INTERVAL_NAME = "Interval";
	private final String AUTHORIZE_NAME = "Authorize";
	private final String LOGIN_NAME = "ULogin";
	private final String PASS_NAME = "UPass";
	
	@Override
	public Token getElement(Element e)
	{
		Token token = new Token();
		token.setCode(super.getValueInt(e, CODE_NAME));
		token.setToken(super.getValue(e, TOKEN_NAME));
		token.setBegDate(super.getValueDate(e, BEG_DATE_NAME));
		token.setInterval(super.getValueInt(e, INTERVAL_NAME));
		token.setAuthorize(super.getValueBool(e, AUTHORIZE_NAME));
		token.setLogin(super.getValue(e, LOGIN_NAME));
		token.setPass(super.getValue(e, PASS_NAME));
		
		return token;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
