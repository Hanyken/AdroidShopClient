package stx.shopclient.entity;

import java.util.Date;
import java.util.GregorianCalendar;

public class Discount {
	public static final int UNIT_TYPE_PERCENT = 1;
	public static final int UNIT_TYPE_RUB = 2;
	
	private long _discountId;
	private String _name;
	private String _description;
	private String _code;
	private double _size;
	private long _catalogId;
	private String _catalogName;
	private String _catalogLogo;
	private String _image;
	private Date _createDate;
	private int _unitType;
	private String _unit;
	
	public long getDiscountId() {
		return _discountId;
	}
	public void setDiscountId(long discountId) {
		_discountId = discountId;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		_description = description;
	}
	public String getCode() {
		return _code;
	}
	public void setCode(String code) {
		_code = code;
	}
	public double getSize() {
		return _size;
	}
	public void setSize(double size) {
		_size = size;
	}
	public long getCatalogId() {
		return _catalogId;
	}
	public void setCatalogId(long catalogId) {
		_catalogId = catalogId;
	}
	public String getCatalogName() {
		return _catalogName;
	}
	public void setCatalogName(String catalogName) {
		_catalogName = catalogName;
	}
	public String getCatalogLogo() {
		return _catalogLogo;
	}
	public void setCatalogLogo(String catalogLogo) {
		_catalogLogo = catalogLogo;
	}
	public String getImage() {
		return _image;
	}
	public void setImage(String image) {
		_image = image;
	}
	public Date getCreateDate() {
		return _createDate;
	}
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}
	public int getUnitType() {
		return _unitType;
	}
	public void setUnitType(int unitType) {
		_unitType = unitType;
	}
	public String getUnit()
	{
		return _unit;
	}
	public void setUnit(String unit)
	{
		_unit = unit;
	}
}
