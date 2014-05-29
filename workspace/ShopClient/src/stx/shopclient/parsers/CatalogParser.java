package stx.shopclient.parsers;

import org.w3c.dom.Element;

import stx.shopclient.entity.Catalog;

public class CatalogParser extends BaseParser<Catalog>
{
	public static String TEST;
	
	private final String ITEM_NAME = "Catalog";
	private final String ID_NAME = "Id";
	private final String NAME_NAME = "Name";
	public final String DESCRIPTION_NAME = "Description";
	public final String LOGO_NAME = "Logo";
	public final String LAST_MODIFICATION_NAME = "LastModification";
	public final String NODE_COUNT_NAME = "NodeCount";
	
	
	
	public CatalogParser()
	{
		TEST = "<Catalog><Id>1</Id><Name>Мобишоп</Name><Logo>image.png</Logo><Description>Какое то описание самого лучшего магазина на свете</Description><LastModification>2014-05-26T18:02:09.783</LastModification><NodeCount>3</NodeCount></Catalog>";
	}
	
	
	public Catalog getElement(Element e)
	{
		Catalog item = new Catalog();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		item.setLastModification(super.getValueDate(e, LAST_MODIFICATION_NAME));
		item.setNodeCount(super.getValueInt(e, NODE_COUNT_NAME));
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
