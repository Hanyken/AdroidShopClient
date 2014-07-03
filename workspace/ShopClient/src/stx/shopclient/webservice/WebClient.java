package stx.shopclient.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import stx.shopclient.entity.ActionType;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogAddress;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogNode;
import stx.shopclient.entity.City;
import stx.shopclient.entity.CountResultEntity;
import stx.shopclient.entity.Discount;
import stx.shopclient.entity.Message;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.Overview;
import stx.shopclient.entity.Payment;
import stx.shopclient.entity.ResultEntity;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.UpdateResultEntity;
import stx.shopclient.loaders.HttpArgs;
import stx.shopclient.parsers.ActionTypeParser;
import stx.shopclient.parsers.CatalogAddressParser;
import stx.shopclient.parsers.CatalogParser;
import stx.shopclient.parsers.CityParser;
import stx.shopclient.parsers.CountResultParser;
import stx.shopclient.parsers.DiscountParser;
import stx.shopclient.parsers.ItemParser;
import stx.shopclient.parsers.MessageParser;
import stx.shopclient.parsers.NodeParser;
import stx.shopclient.parsers.OrderParser;
import stx.shopclient.parsers.OverviewParser;
import stx.shopclient.parsers.PaymentParser;
import stx.shopclient.parsers.ResultParser;
import stx.shopclient.parsers.TokenParser;
import stx.shopclient.repository.OrdersManager;
import stx.shopclient.repository.Repository;
import stx.shopclient.settings.ServerSettings;
import stx.shopclient.settings.UserAccount;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
			InputStream stream = requestStream(relativeUrl, args, isGet);

			String str = convertStreamToString(stream);

			if (str.startsWith("<Result>"))
			{
				ResultParser parser = new ResultParser();
				Collection<ResultEntity> result = parser.parseString(str);
				if (result.size() > 0
						&& result.iterator().next().getCode() == ServiceResponseCode.ACCESS_DENIED)
				{

					login(UserAccount.getLogin(), UserAccount.getPassword(),
							UserAccount.getWidth(), UserAccount.getHeight());
					args.setToken(Token.getCurrent());

					stream = requestStream(relativeUrl, args, isGet);
					str = convertStreamToString(stream);

				}
			}

			return str;
		}
		catch (Throwable ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private InputStream requestStream(String relativeUrl, HttpArgs args,
			boolean isGet)
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

			return response.getEntity().getContent();
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
			Token.setCurrent(token);
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
		if (!StringUtils.isBlank(firstName))
			args.addParam("firstName", firstName);
		if (!StringUtils.isBlank(middleName))
			args.addParam("middleName", middleName);
		if (!StringUtils.isBlank(lastName))
			args.addParam("lastName", lastName);
		if (!StringUtils.isBlank(phone))
			args.addParam("phone", phone);
		if (!StringUtils.isBlank(simId))
			args.addParam("simId", simId);
		if (birthday != null)
			args.addParam("birthday", birthday);
		if (!StringUtils.isBlank(userAgent))
			args.addParam("userAgent", userAgent);
		args.addParam("screen_Width", screenWidth);
		args.addParam("screen_Height", screenHeight);
		if (!StringUtils.isBlank(operationSystem))
			args.addParam("OS", operationSystem);
		if (!StringUtils.isBlank(device))
			args.addParam("device", device);
		if (!StringUtils.isBlank(latitude))
			args.addParam("Latitude", latitude);
		if (!StringUtils.isBlank(longitude))
			args.addParam("Longitude", longitude);
		if (!StringUtils.isBlank(accuracy))
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
	// actionTypeName - это перечень выбранных видов деятельности формат строки: <Search><Value>Телефоны</Value><Value>Торты</Value><Value>любой текст</Value></Search>
	public Catalog getCatalogs(Token token, String catalogName, String actionTypeName, Long cityId, String address, int start, int offset)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogName", catalogName);
		args.addParam("actionTypeName", actionTypeName);
		args.addParam("cityId", cityId);
		args.addParam("address", address);
		args.addParam("start", start);
		args.addParam("offset", offset);

		String response = request("catalog/search", args, true);
		Collection<Catalog> catalogs = new CatalogParser()
				.parseString(response);

		if (catalogs.size() == 0)
			throw new RuntimeException("No catalog returned");
		else
			return catalogs.iterator().next();
	}
	
	public Collection<CatalogAddress> getCatalogAddresses(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("catalog/address", args, true);
		return new CatalogAddressParser().parseString(response);
	}
	
	public Collection<ActionType> getCatalogActionType(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("catalog/action_type", args, true);
		return new ActionTypeParser().parseString(response);
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
	// 0 - отсутствует
	// 1 - по цене по возрастанию
	// 2 - по цене по убыванию
	// 3 - по популярности по возрастанию
	// 4 - по популярности по убыванию
	public Collection<CatalogItem> getNodeItems(Token token,
			long catalogNodeId, int start, int offset, int orderType)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogNodeId", catalogNodeId);
		args.addParam("start", start);
		args.addParam("offset", offset);
		args.addParam("orderType", orderType);
		args.addParam("deep", false);
		args.addParam("filter", "");

		String response = request("item/get", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getNodeItem(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);

		String response = request("item/get", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> quickSearchItems(Token token,
			long catalogId, String name, int start, int offset)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("name", name);
		args.addParam("start", start);
		args.addParam("offset", offset);

		String response = request("item/quick", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	// deep : true - включать результат товары из подразделов, false - не
	// включать
	public Collection<CatalogItem> searchItems(Token token, long catalogNodeId,
			int start, int offset, int orderType, boolean deep, String filter)
	{
		HttpArgs args = new HttpArgs();
		/*
		 * args.addParam("token", token); args.addParam("catalogNodeId",
		 * catalogNodeId); args.addParam("start", start);
		 * args.addParam("offset", offset); args.addParam("orderType", offset);
		 * args.addParam("deep", deep);
		 */
		args.addParam("filter", filter);

		String response = request(
				"item/get?token=" + token.getToken() + "&catalogNodeId="
						+ Long.toString(catalogNodeId) + "&start="
						+ Integer.toString(start) + "&offset="
						+ Integer.toString(offset) + "&orderType="
						+ Integer.toString(orderType) + "&deep="
						+ Boolean.toString(deep), args, false);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getPopular(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/popular", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getLast(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/last", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getFavorite(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("item/favorite", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getCrosssale(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);

		String response = request("item/crosssale", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	public Collection<CatalogItem> getGroupItems(Token token, long itemId,
			long groupId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);
		args.addParam("groupId", groupId);

		String response = request("item/group", args, true);
		Collection<CatalogItem> items = new ItemParser().parseString(response);
		Repository.get(null).getItemsManager().addAll(items);
		return items;
	}

	// Пример xml описания <ItemList><Id>1</Id><Id>2</Id><Id>3</Id></ItemList>
	public void addLast(Token token, String list)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("list", list);

		request("item/lastAdd", args, true);
	}
	
	public void addLast(Token token, long itemId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("list", "<ItemList><Id>"+Long.toString(itemId)+"</Id></ItemList>");

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
			return null;
		else
			return items.iterator().next();
	}

	public boolean editOverview(Token token, long itemId, double rating,
			String description)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("itemId", itemId);
		args.addParam("rating", (byte) rating);
		args.addParam("description", description);

		String response = request("overview/Edit", args, true);
		ResultParser parser = new ResultParser();
		Collection<ResultEntity> items = parser.parseString(response);

		return items.iterator().next().getCode() == ServiceResponseCode.OK;
	}

	public Bitmap getImage(Token token, String imgKey)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("imgKey", imgKey);
		args.addParam("isBig", true);

		InputStream stream = requestStream("image/get", args, true);
		return BitmapFactory.decodeStream(stream);
	}

	public UpdateResultEntity updateCatalogNeeded(Token token, long catalogId,
			Date lastModification)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("date", lastModification);

		String response = request("valid/catalog", args, true);
		Collection<ResultEntity> items = new ResultParser()
				.parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No update returned");
		else
			return new UpdateResultEntity(items.iterator().next());
	}

	public UpdateResultEntity updateSettingsNeeded(Token token,
			Date lastModification)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("date", lastModification);

		String response = request("valid/settings", args, true);
		Collection<ResultEntity> items = new ResultParser()
				.parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No update returned");
		else
			return new UpdateResultEntity(items.iterator().next());
	}

	// Order
	public Collection<Order> getOrders(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("order/get", args, true);
		Collection<Order> items = new OrderParser().parseString(response);

		OrdersManager ordersManager = Repository.get(null).getOrderManager();
		for (Order order : items)
		{
			ordersManager.addOrder(order);
		}
		return items;
	}

	public long getOrderCount(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("order/count", args, true);
		Collection<CountResultEntity> items = new CountResultParser()
				.parseString(response);

		if (items.size() == 0)
			throw new RuntimeException("No count returned");
		else
			return items.iterator().next().getCount();
	}

	public void addOrder(Token token, long itemId,
			Collection<OrderProperty> properties)
	{
		HttpArgs args = new HttpArgs();
		String count = "0";

		for (OrderProperty prop : properties)
		{
			args.addParam(prop.getName(), prop.getValue());

			if (prop.getName().equals(OrderProperty.COUNT_PROPERTY_NAME))
				count = prop.getValue();
		}

		request("order/add?token=" + token.getToken() + "&itemId="
				+ Long.toString(itemId) + "&count=" + count, args, false);
	}

	public void editOrder(Token token, long orderId,
			Collection<OrderProperty> properties)
	{
		HttpArgs args = new HttpArgs();
		String count = "0";
		
		for (OrderProperty prop : properties)
		{
			args.addParam(prop.getName(), prop.getValue());
			
			if (prop.getName().equals(OrderProperty.COUNT_PROPERTY_NAME))
				count = prop.getValue();
		}

		request("order/edit?token=" + token.getToken() + "&orderId="
				+ Long.toString(orderId) + "&count=" + count,
				args, false);
	}

	public void deleteOrder(Token token, long orderId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("orderId", orderId);

		request("order/delete", args, true);
	}
	
	public Collection<Catalog> getOrderCatalogs(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("order/catalogs", args, true);
		Collection<Catalog> items = new CatalogParser().parseString(response);

		return items;
	}

	public Collection<Payment> getPayments(Token token, long catalogId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);

		String response = request("payment/get", args, true);
		Collection<Payment> items = new PaymentParser().parseString(response);

		return items;
	}
	
	
	public Collection<Order> getPaymentOrders(Token token, long paymentId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("paymentId", paymentId);

		String response = request("payment/orders", args, true);
		Collection<Order> items = new OrderParser().parseString(response);

		OrdersManager ordersManager = Repository.get(null).getOrderManager();
		for (Order order : items)
		{
			ordersManager.addOrder(order);
		}
		return items;
	}

	public void addPayment(Token token, long catalogId, String description)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("catalogId", catalogId);
		args.addParam("description", description);

		request("payment/add", args, true);
	}
	
	public Collection<Catalog> getPaymentCatalogs(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("payment/catalogs", args, true);
		Collection<Catalog> items = new CatalogParser().parseString(response);

		return items;
	}
	
	public Collection<City> getCities(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("directory/city", args, true);
		Collection<City> items = new CityParser().parseString(response);

		return items;
	}

	public Collection<ActionType> getActionTypes(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("directory/action_type", args, true);
		return new ActionTypeParser().parseString(response);
	}
	
	public Collection<Message> getNewMessages(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("message/get", args, true);
		return new MessageParser().parseString(response);
	}
	
	public Collection<Message> getAllMessages(Token token, int start, int offset)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("start", start);
		args.addParam("offset", offset);

		String response = request("message/all", args, true);
		return new MessageParser().parseString(response);
	}
	
	public Collection<Message> getReciveMessages(Token token, long messageId)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);
		args.addParam("messageId", messageId);

		String response = request("message/recive", args, true);
		return new MessageParser().parseString(response);
	}
	
	public long getNewMessagesCount(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("message/count", args, true);
		Collection<CountResultEntity> items = new CountResultParser()
		.parseString(response);
		
		if (items.size() == 0)
			throw new RuntimeException("No count returned");
		else
			return items.iterator().next().getCount();
	}
	
	public long getAllMessagesCount(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("message/allcount", args, true);
		Collection<CountResultEntity> items = new CountResultParser()
		.parseString(response);
		
		if (items.size() == 0)
			throw new RuntimeException("No count returned");
		else
			return items.iterator().next().getCount();
	}
	
	
	
	public Collection<Discount> getDiscounts(Token token)
	{
		HttpArgs args = new HttpArgs();
		args.addParam("token", token);

		String response = request("discount/get", args, true);
		return new DiscountParser().parseString(response);
	}
}
