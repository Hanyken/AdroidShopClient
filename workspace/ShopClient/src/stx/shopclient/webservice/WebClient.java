package stx.shopclient.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.Overview;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.UpdateResultEntity;
import stx.shopclient.loaders.HttpArgs;
import stx.shopclient.parsers.CatalogParser;
import stx.shopclient.parsers.ItemParser;
import stx.shopclient.parsers.NodeParser;
import stx.shopclient.parsers.OverviewParser;
import stx.shopclient.parsers.TokenParser;
import stx.shopclient.parsers.UpdateResultParser;
import stx.shopclient.settings.ServerSettings;
import stx.shopclient.utils.StringUtils;
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
		{
			Token token = tokens.iterator().next();
			token.setBegDate(new Date());			
			return token;
		}
	}

	public Token register(String login, String password, String firstName,
			String middleName, String lastName, String phone, String simId,
			Date birthday, String userAgent, int screenWidth, int screenHeight,
			String operationSystem, String device, String latitude,
			String longitude, String accuracy)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("login", login);
		args.addParam("password", password);
		if (!StringUtils.isNullOrEmpty(firstName))
			args.addParam("firstName", firstName);
		if (!StringUtils.isNullOrEmpty(middleName))
			args.addParam("middleName", middleName);
		if (!StringUtils.isNullOrEmpty(lastName))
			args.addParam("lastName", lastName);
		if (!StringUtils.isNullOrEmpty(phone))
			args.addParam("phone", phone);
		if (!StringUtils.isNullOrEmpty(simId))
			args.addParam("simId", simId);
		if (birthday != null)
			args.addParam("birthday", birthday);
		if (!StringUtils.isNullOrEmpty(userAgent))
			args.addParam("userAgent", userAgent);
		args.addParam("screen_Width", screenWidth);
		args.addParam("screen_Height", screenHeight);
		if (!StringUtils.isNullOrEmpty(operationSystem))
			args.addParam("OS", operationSystem);
		if (!StringUtils.isNullOrEmpty(device))
			args.addParam("device", device);
		if (!StringUtils.isNullOrEmpty(latitude))
			args.addParam("Latitude", latitude);
		if (!StringUtils.isNullOrEmpty(longitude))
			args.addParam("Longitude", longitude);
		if (!StringUtils.isNullOrEmpty(accuracy))
			args.addParam("Accuracy", accuracy);

		String response = request("account/signin", args, false);
		TokenParser parser = new TokenParser();
		Collection<Token> tokens = parser.parseString(response);

		if (tokens.size() == 0)
			throw new RuntimeException("No tokens returned");
		else
			return tokens.iterator().next();
	}

	public Catalog getCatalog(Token token, long catalogId)
	{
		return getCatalog(token, catalogId, null);
	}

	public Catalog getCatalog(Token token, long catalogId, StringBuilder xml)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("catalog/get", args, true);
		if (xml != null)
			xml.append(response);
		Collection<Catalog> catalogs = new CatalogParser()
				.parseString(response);

		if (catalogs.size() == 0)
			throw new RuntimeException("No catalog returned");
		else
			return catalogs.iterator().next();
	}

	public Collection<CatalogNode> getNodes(Token token, long catalogId,
			int start, int offset)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("start", start);
		args.addParam("offset", offset);

		String response = request("node/get", args, true);
		Collection<CatalogNode> nodes = new NodeParser().parseString(response);
		return nodes;
	}

	// OrderType
	// 0 - �����������
	// 1 - �� ���� �� �����������
	// 2 - �� ���� �� ��������
	// 3 - �� ������������ �� �����������
	// 4 - �� ������������ �� ��������
	public Collection<CatalogItem> getNodeItems(Token token,
			long catalogNodeId, int start, int offset, int orderType)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogNodeId", catalogNodeId);
		args.addParam("start", start);
		args.addParam("offset", offset);
		args.addParam("orderType", offset);
		args.addParam("deep", false);
		args.addParam("filter", "");

		String response = request("item/get", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	public Collection<CatalogItem> quickSearchItems(Token token,
			long catalogId, String name)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("name", name);

		String response = request("item/quick", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	// deep : true - �������� ��������� ������ �� �����������, false - ��
	// ��������
	public Collection<CatalogItem> searchItems(Token token, long catalogNodeId,
			int start, int offset, int orderType, boolean deep, String filter)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogNodeId", catalogNodeId);
		args.addParam("start", start);
		args.addParam("offset", offset);
		args.addParam("orderType", offset);
		args.addParam("deep", deep);
		args.addParam("filter", filter);

		String response = request("item/get", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	public Collection<CatalogItem> getPopular(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/popular", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	public Collection<CatalogItem> getLast(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/last", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	public Collection<CatalogItem> getFavorite(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/favorite", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		return items;
	}

	public void addLast(Token token, String list)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("list", list);

		request("item/lastAdd", args, true);
	}

	public void addFavorite(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);

		request("item/favoriteAdd", args, true);
	}

	public void delFavorite(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);

		request("item/favoriteDel", args, true);
	}

	public Collection<Overview> getOverviews(Token token, long itemId,
			int start, int offset)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);
		args.addParam("start", start);
		args.addParam("offset", offset);

		String response = request("overview/get", args, true);
		Collection<Overview> items = new OverviewParser().parseString(response);
		return items;
	}

	public Overview getUserOverview(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);

		String response = request("overview/get", args, true);
		Collection<Overview> items = new OverviewParser().parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No overviews returned");
		else
			return items.iterator().next();
	}

	public void editOverview(Token token, long itemId, byte rating,
			String description)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);
		args.addParam("rating", rating);
		args.addParam("description", description);

		request("overview/Edit", args, true);
	}

	public void getImage(Token token, String imgKey, boolean isBig)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("imgKey", imgKey);
		args.addParam("isBig", isBig);

		request("image/get", args, true); // ���� ��� �� �������� ��� ��
											// ���������� � ��������
	}
	
	
	public UpdateResultEntity updateCatalogNeeded(Token token, long catalogId, Date lastModification)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("date", lastModification);

		String response = request("valid/catalog", args, true); 
		Collection<UpdateResultEntity> items = new UpdateResultParser().parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No update returned");
		else
			return items.iterator().next();
	}
	
	public UpdateResultEntity updateSettingsNeeded(Token token, Date lastModification)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("date", lastModification);

		String response = request("valid/settings", args, true); 
		Collection<UpdateResultEntity> items = new UpdateResultParser().parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No update returned");
		else
			return items.iterator().next();
	}
}
