package stx.shopclient.mainmenu;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuListAdapter extends BaseAdapter {

	List<MainMenuItem> _menuItems = new ArrayList<MainMenuItem>();
	Context _context;

	public MainMenuListAdapter(Context context) {

		_context = context;

		addDefaultMenuItems();
	}

	private void addDefaultMenuItems() {
		MainMenuItem item = new MainMenuItem();
		item.setId(MainMenuItem.HOME_MENU_ITEM_ID);
		item.setName("Главная");
		item.setHasIcon(true);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.BASKET_MENU_ITEM_ID);
		item.setName("Корзина");
		item.setHasIcon(false);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.SETTINGS_MENU_ITEM_ID);
		item.setName("Настройки");
		item.setHasIcon(true);
		_menuItems.add(item);
	}

	@Override
	public int getCount() {
		return _menuItems.size();
	}

	@Override
	public Object getItem(int index) {
		return _menuItems.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View arg1, ViewGroup arg2) {

		MainMenuItem item = _menuItems.get(index);

		LayoutInflater inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.main_menu_item, null);

		ImageView image = (ImageView) itemView
				.findViewById(R.id.mainMenuItemImage);
		TextView nameTextView = (TextView) itemView
				.findViewById(R.id.mainMenuItemTextView);

		if (item.isHasIcon()) {
			if (item.getIconId() != 0)
				image.setBackgroundResource(item.getIconId());
		} else {
			image.setVisibility(View.INVISIBLE);
		}

		nameTextView.setText(item.getName());
		nameTextView.setTextColor(Color.WHITE);
		nameTextView.setTextSize(18);

		return itemView;
	}

}