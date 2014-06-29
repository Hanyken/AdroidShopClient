package stx.shopclient.mainmenu;

public class MainMenuItem {
	public static final int HOME_MENU_ITEM_ID = 1;
	public static final int SEARCH_MENU_ITEM_ID = 2;
	public static final int CART_MENU_ITEM_ID = 3;
	public static final int DISCOUNT_CARDS_MENU_ITEM_ID = 4;
	public static final int MESSAGES_MENU_ITEM = 5;
	public static final int SETTINGS_MENU_ITEM_ID = 6;
	public static final int HISTORY_MENU_ITEM = 7;
	public static final int FAVORITE_MENU_ITEM = 8;
	
	private int _id;
	private long _rowId;
	private String _name;
	private int _iconId;
	private int _count;
	private boolean _hasIcon;
	private boolean _clickable = false;
	
	public int getId() {
		return _id;
	}
	
	public void setId(int id) {
		_id = id;
	}
	
	public long getRowId() {
		return _rowId;
	}
	
	public void setRowId(long rowId) {
		_rowId = rowId;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public int getIconId() {
		return _iconId;
	}
	
	public void setIconId(int iconId) {
		_iconId = iconId;
	}
	
	public int getCount() {
		return _count;
	}
	
	public void setCount(int count) {
		_count = count;
	}
	
	public boolean isHasIcon() {
		return _hasIcon;
	}
	
	public void setHasIcon(boolean hasIcon) {
		_hasIcon = hasIcon;
	}	
	
	public boolean isNotClickable() {
		return _clickable;
	}
	
	public void setNotClickable(boolean clickable) {
		_clickable = clickable;
	}	
}
