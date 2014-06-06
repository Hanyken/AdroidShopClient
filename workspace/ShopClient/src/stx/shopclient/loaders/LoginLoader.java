package stx.shopclient.loaders;

import java.util.Collection;

import stx.shopclient.entity.Token;
import stx.shopclient.parsers.TokenParser;

public class LoginLoader extends ServiceBaseLoader<Token>
{
	private OnLoadChange<Token> _call;
	
	public LoginLoader(OnLoadChange<Token> call)
	{
		super("http://5.19.235.173:8080/service/account/login", false);
		_call = call;
	}

	
	public void Login()
	{
		HttpArgs args = new HttpArgs();
		args.addParam("login", "Admin");
		args.addParam("password", "123");
		args.addParam("screen_Width", "300");
		args.addParam("screen_Height", "300");
		super.execute(args);
	}
	
	@Override
	protected Collection<Token> onParse(String value)
	{
		TokenParser parser = new TokenParser();
		return parser.parseString(value);
	}
	
	@Override
	protected void onPostExecute(Collection<Token> result)
	{
		_call.onChange(result);
	}
}
