package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CatalogItem;

public class ItemParser extends BaseParser<CatalogItem>
{
	public String TEST;
	private final String ITEM_NAME = "Item";
	private final String NUMBER_NAME = "RowNum";
	private final String ID_NAME = "Id";
	private final String NAME_NAME = "Name";
	private final String CATALOG_ID_NAME = "CatalogId";
	private final String IS_LEAF_NAME = "IsLeaf";
	private final String CHILD_COUNT_NAME = "ChildCount";
	private final String CAN_BUBBLE_NAME = "CanBubble";
	private final String RATING_NAME = "Rating";
	private final String OVERVIEW_COUNT_NAME = "OverviewsCount";
	private final String DESCRIPTION_NAME = "Description";
	/*
	private final String OVERVIEWS_NAME = "Overviews";
	private final String PROPERTYES_NAME = "Properties";
	private final String ORDER_PROPERTYES_NAME = "OrderProperties";
	*/
	
	public ItemParser()
	{
		TEST = "<Items><ItemsCount>1</ItemsCount><Item><RowNum>1</RowNum><Id>1</Id><Name>IPhone</Name><CatalogId>1</CatalogId><IsLeaf>1</IsLeaf><ChildCount>0</ChildCount>";
		TEST += "<CanBubble>1</CanBubble><Rating>1.00000</Rating><OverviewsCount>1</OverviewsCount>";
		TEST += "<Overviews><Overview><RowNum>1</RowNum><Value>Хуйня какая то</Value><Rating>1</Rating><CreateDate>2014-05-26T18:02:09.787</CreateDate></Overview></Overviews>";
		TEST += "<Properties><Property><Name>Price</Name><Type>Numeric</Type><Title>Цена</Title><Searchable>1</Searchable><QuickSearch>1</QuickSearch><ShortList>0</ShortList><Order>1</Order><Value>30000.00000</Value></Property>";
		TEST += "<Property><Name>String_Key</Name><Type>String</Type><Title>Строка</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>2</Order><Value>Какой то текст</Value></Property>";
		TEST += "<Property><Name>Date_Key</Name><Type>Date</Type><Title>Дата</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>2014-05-26 18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Time_Key</Name><Type>Time</Type><Title>Время</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Number_Key</Name><Type>Integer</Type><Title>Число</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>4</Order><Value>100.00000</Value></Property>";
		TEST += "<Property><Name>Bool_Key</Name><Type>Boolean</Type><Title>Логическое значение</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>5</Order><Value>1</Value></Property></Properties>";
		TEST += "<OrderProperties><Property><Name>OrderDate</Name><Type>Date</Type><Title>Дата для заказа</Title><Order>1</Order><Required>1</Required><Value>";
		TEST += "<Max>2014-01-01T00:00:00</Max><Min>2014-12-31T00:00:00</Min></Value></Property><Property><Name>Count</Name><Type>Numeric</Type><Title>Количество</Title>";
		TEST += "<Order>2</Order><Required>1</Required><Value><Max>1.00000</Max><Min>8.00000</Min></Value></Property><Property><Name>Comment</Name><Type>String</Type>";
		TEST += "<Title>Коментарий</Title><Order>3</Order><Required>0</Required></Property><Property><Name>Color</Name><Type>Enum</Type><Title>Цвет</Title>";
		TEST += "<Order>4</Order><Required>0</Required><Range><Enum><Value>1</Value><Name>Синий</Name></Enum><Enum><Value>2</Value><Name>Красный</Name></Enum>";
		TEST += "<Enum><Value>3</Value><Name>Зеленый</Name></Enum></Range></Property></OrderProperties></Item></Items>";
	}
	
	
	public CatalogItem getElement(Element e)
	{
		CatalogItem item = new CatalogItem();

		item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
		item.setId(Long.parseLong(super.getValue(e, ID_NAME)));
		item.setName(super.getValue(e, NAME_NAME));
		item.setCatalogId(super.getValueLong(e, CATALOG_ID_NAME));
		item.setIsLeaf(super.getValueBool(e, IS_LEAF_NAME));
		item.setChildCount(super.getValueInt(e, CHILD_COUNT_NAME));
		item.setCanBubble(super.getValueBool(e, CAN_BUBBLE_NAME));
		item.setRating(super.getValueDouble(e, RATING_NAME));
		item.setOverviewsCount(super.getValueInt(e, OVERVIEW_COUNT_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
