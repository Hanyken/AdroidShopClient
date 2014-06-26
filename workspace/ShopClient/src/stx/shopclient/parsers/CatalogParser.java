package stx.shopclient.parsers;

import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogNode;

public class CatalogParser extends BaseParser<Catalog>
{
	private final String ITEM_NAME = "Catalog";
	private final String ID_NAME = "Id";
	private final String NAME_NAME = "Name";
	public final String DESCRIPTION_NAME = "Description";
	public final String LOGO_NAME = "Logo";
	public final String LAST_MODIFICATION_NAME = "LastModification";
	public final String NODE_COUNT_NAME = "NodeCount";
	private final String NODES_NAME= "Nodes";
	private final String NODE_NAME= "Node";
	private final String ORDER_COUNT_NAME= "OrderCount";
	
	
	public CatalogParser()
	{
	}
	
	
	public Catalog getElement(Element e)
	{
		Catalog item = new Catalog();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setName(super.getValue(e, NAME_NAME));
		item.setLogo(super.getValue(e, LOGO_NAME));
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		item.setLastModification(super.getValueDate(e, LAST_MODIFICATION_NAME));
		item.setNodeCount(super.getValueInt(e, NODE_COUNT_NAME));
		item.setOrderCount(super.getValueInt(e, ORDER_COUNT_NAME));
		
		NodeList nl = e.getElementsByTagName(NODES_NAME);
		if (nl.getLength() > 0)
		{
			NodeList nodesList = ((Element)nl.item(0)).getElementsByTagName(NODE_NAME); 
			NodeParser parser = new NodeParser();
			Collection<CatalogNode> nodes = parser.getElements(nodesList);
			item.setNodes(nodes);
		}
		
		return item;
	}
	protected String getElementName()
	{
		return ITEM_NAME;
	}
}
