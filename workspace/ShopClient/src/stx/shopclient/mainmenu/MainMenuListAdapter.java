package stx.shopclient.mainmenu;

import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import stx.shopclient.entity.Message;
import stx.shopclient.repository.Repository;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuListAdapter extends BaseAdapter
{

	List<MainMenuItem> _menuItems = new ArrayList<MainMenuItem>();
	Context _context;

	MainMenuItem _cartMenuItem;
	MainMenuItem _messagesMenuItem;

	public MainMenuListAdapter(Context context)
	{

		_context = context;

		addDefaultMenuItems();
	}

	private void addDefaultMenuItems()
	{
		MainMenuItem item = new MainMenuItem();
		item.setId(MainMenuItem.HOME_MENU_ITEM_ID);
		item.setName("Главная");
		item.setHasIcon(true);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.SEARCH_MENU_ITEM_ID);
		item.setName("Поиск");
		item.setHasIcon(true);
		item.setIconId(R.drawable.img_search_menu_icon);
		_menuItems.add(item);

		_cartMenuItem = new MainMenuItem();
		_cartMenuItem.setId(MainMenuItem.CART_MENU_ITEM_ID);
		_cartMenuItem.setName("Корзина");
		_cartMenuItem.setHasIcon(true);
		_cartMenuItem.setIconId(R.drawable.img_shopping_cart);
		_cartMenuItem.setCount(Repository.get(_context).getOrderManager()
				.getOrderItemsCount());
		_menuItems.add(_cartMenuItem);

		item = new MainMenuItem();
		item.setId(MainMenuItem.DISCOUNT_CARDS_MENU_ITEM_ID);
		item.setName("Скидочные карты");
		item.setHasIcon(true);
		item.setIconId(R.drawable.img_discount);
		_menuItems.add(item);

		_messagesMenuItem = new MainMenuItem();
		_messagesMenuItem.setId(MainMenuItem.MESSAGES_MENU_ITEM);
		_messagesMenuItem.setName("Сообщения");
		_messagesMenuItem.setHasIcon(true);
		_menuItems.add(_messagesMenuItem);

		item = new MainMenuItem();
		item.setId(MainMenuItem.SETTINGS_MENU_ITEM_ID);
		item.setName("Настройки");
		item.setHasIcon(true);
		_menuItems.add(item);
	}

	void updateMenuItems()
	{
		_cartMenuItem.setCount(Repository.get(_context).getOrderManager()
				.getOrderItemsCount());

		int unreadMessages = 0;
		for (Message m : Repository.get(_context).getMessagesManager()
				.getMessages())
		{
			if(!m.isRead())
				unreadMessages++;
		}
		_messagesMenuItem.setCount(unreadMessages);		
	}

	@Override
	public int getCount()
	{
		updateMenuItems();
		return _menuItems.size();
	}

	@Override
	public Object getItem(int index)
	{
		return _menuItems.get(index);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View arg1, ViewGroup arg2)
	{

		MainMenuItem item = _menuItems.get(index);

		LayoutInflater inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.base_activity_main_menu_item,
				null);

		ImageView image = (ImageView) itemView
				.findViewById(R.id.mainMenuItemImage);
		TextView nameTextView = (TextView) itemView
				.findViewById(R.id.mainMenuItemTextView);

		if (item.isHasIcon())
		{
			if (item.getIconId() != 0)
				image.setImageResource(item.getIconId());
		} else
		{
			image.setVisibility(View.INVISIBLE);
		}

		nameTextView.setText(item.getName());
		nameTextView.setTextColor(Color.WHITE);
		nameTextView.setTextSize(18);

		TextView counterTextView = (TextView) itemView
				.findViewById(R.id.counterTextView);

		if (item.getCount() == 0)
			counterTextView.setVisibility(View.GONE);
		else
		{
			counterTextView.setVisibility(View.VISIBLE);
			counterTextView.setText("(" + item.getCount() + ")");

			counterTextView.setTextColor(Color.WHITE);
			counterTextView.setTextSize(19);
		}

		return itemView;
	}

}