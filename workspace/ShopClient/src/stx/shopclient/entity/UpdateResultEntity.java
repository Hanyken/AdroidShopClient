package stx.shopclient.entity;

import stx.shopclient.webservice.ServiceResponseCode;

public class UpdateResultEntity extends ResultEntity
{
	public UpdateResultEntity(ResultEntity item)
	{
		this.setCode(item.getCode());
	}
	
	public boolean updateNeeded()
	{
		return super.getCode() == ServiceResponseCode.UPDATE_NEEDED;
	}
}
