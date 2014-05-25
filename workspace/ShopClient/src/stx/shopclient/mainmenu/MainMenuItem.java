package stx.shopclient.mainmenu;

public class MainMenuItem {
	public static final int HOME_MENU_ITEM_ID = 1;
	public static final int SETTINGS_MENU_ITEM_ID = 2;
	public static final int CART_MENU_ITEM_ID = 3;
	public static final int SEARCH_MENU_ITEM_ID = 4;
	
	private int _id;
	private String _name;
	private int _iconId;
	private int _count;
	private boolean _hasIcon;
	
	public int getId() {
		return _id;
	}
	
	public void setId(int id) {
		_id = id;
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
}
