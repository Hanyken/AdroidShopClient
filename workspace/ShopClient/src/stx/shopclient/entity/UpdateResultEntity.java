package stx.shopclient.entity;

import stx.shopclient.webservice.ServiceResponseCode;

public class UpdateResultEntity extends ResultEntity
{
	public boolean updateNeeded()
	{
		return super.getCode() == ServiceResponseCode.UPDATE_NEEDED;
	}
}
