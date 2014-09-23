package stx.shopclient.parsers;

import org.w3c.dom.Element;

public class TimestampParser extends BaseParser<Long>
{
	private static final String TIMESTAMP_NAME = "Timestamp";
	
	private String rootElement;
	public TimestampParser(String rootElement)
	{
		this.rootElement = rootElement;
	}
	
	@Override
	public Long getElement(Element e) {
		return super.getValueLong(e, TIMESTAMP_NAME);
	}

	@Override
	protected String getElementName() {
		return rootElement;
	}

}
