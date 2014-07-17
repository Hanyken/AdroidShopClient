package stx.shopclient.parsers;

import java.util.GregorianCalendar;

import org.w3c.dom.Element;

import stx.shopclient.entity.Message;

public class MessageParser extends BaseParser<Message>
{
	private static final String ITEM_NAME = "Message";
	private static final String ROW_NUM_NAME = "RowNumber";
	private static final String ID_NAME = "Id";
	private static final String MESSAGE_TYPE_NAME = "MessageType";
	private static final String TITLE_NAME = "Title";
	private static final String TEXT_NAME = "Text";
	private static final String CREATE_DATE_NAME = "CreateDate";
	private static final String IS_RECIVE_NAME = "IsRecive";
	private static final String IMAGE_NAME = "Image";
	
	@Override
	public Message getElement(Element e)
	{
		Message item = new Message();
		
		item.setId(super.getValueLong(e, ID_NAME));
		item.setTitle(super.getValue(e, TITLE_NAME));
		item.setText(super.getValue(e, TEXT_NAME));
		item.setType(super.getValueInt(e, MESSAGE_TYPE_NAME));
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(super.getValueDateTime(e, CREATE_DATE_NAME));
		item.setCreateDate(calendar);
		item.setRead(super.getExistsValue(e, IS_RECIVE_NAME));
		item.setRowNum(super.getValueInt(e, ROW_NUM_NAME));
		item.setImage(super.getValue(e, IMAGE_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
