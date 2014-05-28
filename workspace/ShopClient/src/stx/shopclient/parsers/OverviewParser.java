package stx.shopclient.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;


import stx.shopclient.entity.Overview;

public class OverviewParser extends BaseParser
{
	public static String TEST;

	private final String ITEM_NAME = "Overview";
	private final String NUMBER_NAME = "RowNum";
	private final String CUR_USER_NAME = "UserOverview";
	private final String DESCRIPTION_NAME = "Value";
	private final String RATING_NAME = "Rating";
	private final String CREATE_DATE_NAME = "CreateDate";

	public OverviewParser()
	{
		TEST = "<Overviews><Overview><RowNum>1</RowNum><UserOverview>1</UserOverview><Value>����� ����� ��</Value><Rating>1</Rating><CreateDate>2014-05-26T18:02:09.787</CreateDate></Overview></Overviews>";
	}

	public Collection<Overview> getOverview(String xmlString)
	{
		Document doc = super.getDomElement(xmlString);
		NodeList nl = doc.getElementsByTagName(ITEM_NAME);

		return getOverview(nl);
	}
	
	public Collection<Overview> getOverview(NodeList nl)
	{
		ArrayList<Overview> items = new ArrayList<Overview>();
		for (int i = 0; i < nl.getLength(); i++)
		{
			try
			{				
				Element e = (Element) nl.item(i);

				Overview item = new Overview();

				item.setRowNumber(super.getValueInt(e, NUMBER_NAME));
				//item.setItemId(Long.parseLong(super.getValue(e, )));
				item.setIsCurrentUser(super.getValueBool(e, CUR_USER_NAME)); // ���� ����� ���� ���� �� ������� ������������
				item.setDescription(super.getValue(e, DESCRIPTION_NAME));
				item.setRating(super.getValueDouble(e, RATING_NAME));
				item.setCreateDate(super.getValueDate(e, CREATE_DATE_NAME));

				items.add(item);
			}
			catch (Exception ex)
			{
				Log.w("MyException", ex.getMessage());
			}
		}

		return items;
	}

}
