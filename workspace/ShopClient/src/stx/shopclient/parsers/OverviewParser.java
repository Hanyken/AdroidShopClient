package stx.shopclient.parsers;

import org.w3c.dom.Element;


import stx.shopclient.entity.Overview;

public class OverviewParser extends BaseParser<Overview>
{
	public String TEST;

	private final String ITEM_NAME = "Overview";
	private final String NUMBER_NAME = "RowNum";
	private final String CUR_USER_NAME = "UserOverview";
	private final String DESCRIPTION_NAME = "Value";
	private final String RATING_NAME = "Rating";
	private final String CREATE_DATE_NAME = "CreateDate";

	public OverviewParser()
	{
		TEST = "<Overviews><Overview><RowNum>1</RowNum><UserOverview>1</UserOverview><Value>’уйн€ кака€ то</Value><Rating>1</Rating><CreateDate>2014-05-26T18:02:09.787</CreateDate></Overview></Overviews>";
	}

	public Overview getElement(Element e)
	{
		Overview item = new Overview();

		item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
		//item.setItemId(Long.parseLong(super.getValue(e, )));
		item.setIsCurrentUser(super.getValueBool(e, CUR_USER_NAME)); // если такой флаг есть то текущий пользователь
		item.setDescription(super.getValue(e, DESCRIPTION_NAME));
		item.setRating(super.getValueDouble(e, RATING_NAME));
		item.setCreateDate(super.getValueDate(e, CREATE_DATE_NAME));
		
		return item;
	}

	@Override
	protected String getElementName()
	{
		return ITEM_NAME;
	}

}
