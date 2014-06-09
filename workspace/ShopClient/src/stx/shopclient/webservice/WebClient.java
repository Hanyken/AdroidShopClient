package stx.shopclient.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import stx.shopclient.entity.Token;
import stx.shopclient.loaders.HttpArgs;
import stx.shopclient.parsers.TokenParser;
import stx.shopclient.settings.ServerSettings;
import android.content.Context;

public class WebClient
{
	Context _context;

	public WebClient(Context context)
	{
		_context = context;
		ServerSettings.load(_context);
	}

	private String getUrl(String relativeUrl)
	{
		String baseUrl = ServerSettings.getUrl();

		return baseUrl + "/" + relativeUrl;
	}

	private String request(String relativeUrl, HttpArgs args, boolean isGet)
	{
		try
		{
			String url = getUrl(relativeUrl);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = null;
			if (isGet)
				response = httpclient.execute(args.getGet(url));
			else
				response = httpclient.execute(args.getPost(url));

			String str = convertStreamToString(response.getEntity()
					.getContent());

			return str;
		}
		catch (Throwable ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private static String convertStreamToString(InputStream is)
	{

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append((line + "\n"));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public Token login(String login, String password, int screenWidth,
			int screenHeight)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("login", login);
		args.addParam("password", password);
		args.addParam("screen_Width", Integer.toString(screenWidth));
		args.addParam("screen_Height", Integer.toString(screenHeight));

		String response = request("account/login", args, false);
		TokenParser parser = new TokenParser();
		Collection<Token> tokens = parser.parseString(response);
		
		if (tokens.size() == 0)
			throw new RuntimeException("No tokens returned");
		else
			return tokens.iterator().next();
	}
}
