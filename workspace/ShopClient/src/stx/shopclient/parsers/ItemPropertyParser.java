package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.CatalogItemProperty;

public class ItemPropertyParser extends BaseParser<CatalogItemProperty>
{
	public String TEST;
	
	private final String ITEM_NAME = "Property";
	private final String NAME_NAME = "Name";
	private final String TYPE_NAME = "Type";
	private final String TITLE_NAME = "Title";
	//private final String SEARCHABLE_NAME = "Searchable";
	//private final String QUICK_SEARCHABLE_NAME = "QuickSearch";
	private final String SHORT_LIST_NAME = "ShortList";
	private final String ORDER_NAME = "Order";
	private final String VALUE_NAME = "Value";
	
	
	public ItemPropertyParser()
	{
		TEST = "<Properties><Property><Name>Price</Name><Type>Numeric</Type><Title>����</Title><Searchable>1</Searchable><QuickSearch>1</QuickSearch><ShortList>0</ShortList><Order>1</Order><Value>30000.00000</Value></Property>";
		TEST += "<Property><Name>String_Key</Name><Type>String</Type><Title>������</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>2</Order><Value>����� �� �����</Value></Property>";
		TEST += "<Property><Name>Date_Key</Name><Type>Date</Type><Title>����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>2014-05-26 18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Time_Key</Name><Type>Time</Type><Title>�����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>3</Order><Value>18:02:09.7870000</Value></Property>";
		TEST += "<Property><Name>Number_Key</Name><Type>Integer</Type><Title>�����</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>4</Order><Value>100.00000</Value></Property>";
		TEST += "<Property><Name>Bool_Key</Name><Type>Boolean</Type><Title>���������� ��������</Title><Searchable>1</Searchable><QuickSearch>0</QuickSearch><ShortList>0</ShortList><Order>5</Order><Value>1</Value></Property></Properties>";
	}
	
	public CatalogItemProperty getElement(Element e)
	{
		CatalogItemProperty item = new CatalogItemProperty();
		
		item.setName(super.getValue(e, NAME_NAME));
		item.setType(super.getValue(e, TYPE_NAME));
		item.setTitle(super.getValue(e, TITLE_NAME));
		item.setShortList(super.getValueBool(e, SHORT_LIST_NAME));
		item.setOrder(super.getValueInt(e, ORDER_NAME));
		item.setValue(super.getValue(e, VALUE_NAME));
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
