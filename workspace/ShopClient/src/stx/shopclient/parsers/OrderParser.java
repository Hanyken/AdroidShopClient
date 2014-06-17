package stx.shopclient.parsers;

import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.Order;
import stx.shopclient.entity.OrderProperty;

public class OrderParser extends BaseParser<Order>
{
	public static final String ITEM_NAME = "OrderItem";
	private final String ORDER_ID_NAME = "OrderId";
	private final String ITEM_ID_NAME = "ItemId";
	private final String ORDER_DATE_NAME ="OrderDate";
	//private final String COUNT_NAME = "Count";
	//private final String PRICE_NAME = "Price";
	private final String ORDER_ITEM_NAME = "Item";
	private final String PROPERTIES_NAME = "Properties";
	
	
	
	@Override
	public Order getElement(Element e)
	{
		Order item = new Order();
		item.setId(super.getValueLong(e, ORDER_ID_NAME));
		item.setItemId(super.getValueLong(e, ITEM_ID_NAME));
		item.setDate(super.getValueDate(e, ORDER_DATE_NAME));
		
		NodeList iNl = e.getElementsByTagName(ORDER_ITEM_NAME);
		if (iNl.getLength() > 0)
		{ 
			ItemParser parser = new ItemParser();
			Collection<CatalogItem> items = parser.getElements(iNl);
			if (items.size() > 0)
				item.setItem(items.iterator().next());
		}
		
		NodeList pNl = e.getElementsByTagName(PROPERTIES_NAME);
		if (pNl.getLength() > 0)
		{
			NodeList itemsList = ((Element)pNl.item(0)).getElementsByTagName(OrderPropertyParser.ITEM_NAME); 
			OrderPropertyParser parser = new OrderPropertyParser(item.getId());
			Collection<OrderProperty> items = parser.getElements(itemsList);
			item.setProperties(items);
		}
		
		return item;
	}
	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
