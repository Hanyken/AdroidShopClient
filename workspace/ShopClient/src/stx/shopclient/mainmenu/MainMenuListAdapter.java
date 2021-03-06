package stx.shopclient.mainmenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Catalog;
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
	List<MainMenuItem> _menuItemsFiltered = new ArrayList<MainMenuItem>();
	BaseActivity _context;

	MainMenuItem _cartMenuItem;
	MainMenuItem _messagesMenuItem;

	public MainMenuListAdapter(BaseActivity context)
	{

		_context = context;

		addDefaultMenuItems();
	}

	@Override
	public void notifyDataSetChanged()
	{
		_menuItemsFiltered.clear();

		for (MainMenuItem item : _menuItems)
		{
			if (_context.initMainMenuItem(item))
				_menuItemsFiltered.add(item);
		}

		super.notifyDataSetChanged();
	}

	private void addDefaultMenuItems()
	{
		MainMenuItem item = new MainMenuItem();
		item.setId(MainMenuItem.HOME_MENU_ITEM_ID);
		item.setName("�������");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_home_page);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.SEARCH_MENU_ITEM_ID);
		item.setName("�����");
		item.setHasIcon(true);
		item.setIconId(android.R.drawable.ic_menu_search);
		_menuItems.add(item);

		_cartMenuItem = new MainMenuItem();
		_cartMenuItem.setId(MainMenuItem.CART_MENU_ITEM_ID);
		_cartMenuItem.setName("�������");
		_cartMenuItem.setHasIcon(true);
		_cartMenuItem.setIconId(R.drawable.dark_bascket);
		_cartMenuItem.setNotClickable(true);
		_menuItems.add(_cartMenuItem);

		item = new MainMenuItem();
		item.setId(MainMenuItem.DISCOUNT_CARDS_MENU_ITEM_ID);
		item.setName("��������� �����");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_card);
		_menuItems.add(item);

		_messagesMenuItem = new MainMenuItem();
		_messagesMenuItem.setId(MainMenuItem.MESSAGES_MENU_ITEM);
		_messagesMenuItem.setName("���������");
		_messagesMenuItem.setHasIcon(true);
		_messagesMenuItem.setIconId(R.drawable.dark_comment);
		_menuItems.add(_messagesMenuItem);

		item = new MainMenuItem();
		item.setId(MainMenuItem.SETTINGS_MENU_ITEM_ID);
		item.setName("���������");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_options);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.HISTORY_MENU_ITEM);
		item.setName("�������");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_history);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.FAVORITE_MENU_ITEM);
		item.setName("���������");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_favorite);
		_menuItems.add(item);

		item = new MainMenuItem();
		item.setId(MainMenuItem.CATALOGS_MENU_ITEM);
		item.setName("��������");
		item.setHasIcon(true);
		item.setIconId(R.drawable.dark_catalogs);
		_menuItems.add(item);
	}

	public void addOrderCatalogs(Collection<Catalog> catalogs)
	{
		List<MainMenuItem> additems = new ArrayList<MainMenuItem>();
		List<MainMenuItem> delitems = new ArrayList<MainMenuItem>();

		for (MainMenuItem el : _menuItems)
		{
			if (el.getId() >= MainMenuItem.CART_MENU_ITEM_ID)
			{
				if (el.getId() > MainMenuItem.CART_MENU_ITEM_ID)
				{
					additems.add(el);
				}
				else if (el.getId() == MainMenuItem.CART_MENU_ITEM_ID
						&& el.getRowId() == 0)
				{
					continue;
				}
				delitems.add(el);
			}
		}
		_menuItems.removeAll(delitems);
		for (Catalog el : catalogs)
		{
			MainMenuItem item = new MainMenuItem();
			item.setId(MainMenuItem.CART_MENU_ITEM_ID);
			item.setRowId(el.getId());
			item.setName(el.getName());
			item.setCount(el.getOrderCount());
			_menuItems.add(item);
		}
		_menuItems.addAll(additems);
	}

	@Override
	public int getCount()
	{
		return _menuItemsFiltered.size();
	}

	@Override
	public Object getItem(int index)
	{
		return _menuItemsFiltered.get(index);
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

		MainMenuItem item = _menuItemsFiltered.get(index);

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
		}
		else
		{
			image.setVisibility(View.INVISIBLE);
		}

		nameTextView.setText(item.getName());
		nameTextView.setTextColor(_context.getResources().getColor(
				R.color.menuText));
		nameTextView.setTextSize(16);

		TextView counterTextView = (TextView) itemView
				.findViewById(R.id.counterTextView);

		itemView.setClickable(item.isNotClickable());

		if (item.getCount() == 0)
			counterTextView.setVisibility(View.GONE);
		else
		{
			counterTextView.setVisibility(View.VISIBLE);
			counterTextView.setText(Integer.toString(item.getCount()));

			counterTextView.setTextColor(_context.getResources().getColor(
					R.color.menuCounter));
			counterTextView.setTextSize(16);
		}

		return itemView;
	}

}