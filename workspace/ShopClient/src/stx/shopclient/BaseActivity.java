package stx.shopclient;

import java.util.Collection;
import java.util.GregorianCalendar;

import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.catalogsactivity.CatalogsActivity;
import stx.shopclient.discountactivity.DiscountListActivity;
import stx.shopclient.entity.Catalog;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Token;
import stx.shopclient.entity.UpdateResultEntity;
import stx.shopclient.favoriteactivity.FavoriteActivity;
import stx.shopclient.historyactivity.PaymentListActivity;
import stx.shopclient.loginactivity.LoginActivity;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.mainmenu.MainMenuListAdapter;
import stx.shopclient.messagesactivity.MessagesListActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.searchactivity.SearchActivity;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.settingsactivity.SettingsActivity;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.utils.ImageDownloadTask;
import stx.shopclient.webservice.ServiceResponseCode;
import stx.shopclient.webservice.WebClient;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity
{
	DrawerLayout _drawerLayout;
	LinearLayout _mainViewContainer;
	MainMenuListAdapter _mainMenuListAdapter;
	ProgressDialog _progressDialog;
	boolean _destroyed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent serviceIntent = new Intent(this, ShopClientService.class);
		startService(serviceIntent);

		UserAccount.load(this);
		if (UserAccount.getLogin() == null || UserAccount.getLogin().equals(""))
		{
			redirectToLoginActivity();
			return;
		}

		LoginTask task = new LoginTask();
		task.execute();
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		_destroyed = false;

		new GetOrderCatalogsTask().execute();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		_destroyed = true;
	}

	void loadUI()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Repository.get(this)
				.getCatalogManager().getSettings().getBackground()));

		ImageDownloadTask.startNew(actionBar, this, Repository.get(this)
				.getCatalogManager().getCatalog().getLogo());

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.base_activity);

		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawerLayout.getRootView().setBackgroundColor(Color.WHITE);
		_drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener()
		{

			@Override
			public void onDrawerStateChanged(int arg0)
			{

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1)
			{

			}

			@Override
			public void onDrawerOpened(View arg0)
			{
				_mainMenuListAdapter.addOrderCatalogs(Repository.get(null)
						.getOrderManager().getOrderCatalogs());
				_mainMenuListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onDrawerClosed(View arg0)
			{

			}
		});

		_mainViewContainer = (LinearLayout) findViewById(R.id.mainViewContainer);

		View mainView = createMainView(_mainViewContainer);

		if (mainView != null)
		{
			_mainViewContainer.addView(mainView);
		}

		ListView _menuList = (ListView) findViewById(R.id.mainMenuList);
		_menuList.setBackgroundDrawable(new ColorDrawable(Repository.get(this)
				.getCatalogManager().getSettings().getBackground()));
		_menuList.setDivider(new ColorDrawable(Repository.get(this)
				.getCatalogManager().getSettings().getForegroundColor()));
		_menuList.setDividerHeight(1);

		_mainMenuListAdapter = new MainMenuListAdapter(this);
		_menuList.setAdapter(_mainMenuListAdapter);
		_menuList.setOnItemClickListener(new MainMenuOnClickListener());
	}

	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else if (item.getId() == MainMenuItem.MESSAGES_MENU_ITEM)
		{
			item.setCount((int) Repository.get(null).getMessagesManager()
					.getUnreadMessageCount());
		}
		return true;
	}

	protected boolean isNeedRelogin()
	{
		boolean needLogin = false;

		if (Token.getCurrent() == null)
			needLogin = true;
		if (Token.getCurrent() != null)
		{
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(Token.getCurrent().getBegDate());
			cal.add(GregorianCalendar.SECOND, Token.getCurrent().getInterval());

			String time1 = cal.toString();
			String time2 = new GregorianCalendar().toString();
			Log.d("asd", time1 + time2);

			if (cal.before(new GregorianCalendar()))
				needLogin = true;
		}

		return needLogin;
	}

	public void redirectToLoginActivity()
	{
		Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	class LoginTask extends AsyncTask<Void, Void, Token>
	{
		Throwable exception;
		boolean needLogin = false;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			_progressDialog = ProgressDialog.show(BaseActivity.this,
					"Загрузка", "Выполняется вход");

			if (isNeedRelogin())
			{
				needLogin = true;
			}
			else
			{
				this.cancel(true);
				this.onPostExecute(null);
			}
		}

		@Override
		protected Token doInBackground(Void... arg0)
		{
			if (needLogin)
			{
				WebClient client = new WebClient(BaseActivity.this);
				DisplayMetrics metrics = getResources().getDisplayMetrics();
				try
				{
					Token token = client.login(UserAccount.getLogin(),
							UserAccount.getPassword(), metrics.widthPixels,
							metrics.heightPixels);

					if (token != null)
					{
						if (token.getCode() != ServiceResponseCode.OK)
							return token;
					}
					else
						return null;
				}
				catch (Throwable ex)
				{
					exception = ex;
					return null;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Token result)
		{
			super.onPostExecute(result);

			if (_progressDialog != null)
				_progressDialog.dismiss();

			if (exception != null)
			{
				Toast.makeText(BaseActivity.this,
						exception.getLocalizedMessage(), Toast.LENGTH_LONG)
						.show();
				redirectToLoginActivity();
			}
			else if (result != null
					&& result.getCode() != ServiceResponseCode.OK)
			{
				Toast.makeText(BaseActivity.this,
						ServiceResponseCode.getMessage(result.getCode()),
						Toast.LENGTH_LONG).show();
				redirectToLoginActivity();
				return;
			}
			else
			{
				if (Repository.get(BaseActivity.this).getCatalogManager()
						.getCatalog() == null)
				{
					CatalogLoadTask task = new CatalogLoadTask();
					task.execute();
				}
				else
				{
					loadUI();
				}
			}
		}
	}

	protected void onMainMenuItemClick(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.HOME_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, MainActivity.class);
			if (NavUtils.shouldUpRecreateTask(this, intent))
			{
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
			}
			else
			{
				NavUtils.navigateUpTo(this, intent);
			}

		}
		else if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra(SearchActivity.TITLE_EXTRA_KEY,
					getSearchActivityTitle());
			intent.putExtra(SearchActivity.NODE_ID_EXTRA_KEY,
					getSearchActivityNodeId());
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.CART_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, CartActivity.class);
			intent.putExtra(CartActivity.CARD_ID_NAME, item.getRowId());
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.DISCOUNT_CARDS_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, DiscountListActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.MESSAGES_MENU_ITEM)
		{
			Intent intent = new Intent(this, MessagesListActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.SETTINGS_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.HISTORY_MENU_ITEM)
		{
			Intent intent = new Intent(this, PaymentListActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.FAVORITE_MENU_ITEM)
		{
			Intent intent = new Intent(this, FavoriteActivity.class);
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.CATALOGS_MENU_ITEM)
		{
			Intent intent = new Intent(this, CatalogsActivity.class);
			startActivity(intent);
		}
	}

	protected String getSearchActivityTitle()
	{
		return getActionBar().getTitle().toString();
	}

	protected long getSearchActivityNodeId()
	{
		return 0;
	}

	protected View createMainView(ViewGroup parent)
	{
		return null;
	}

	protected void onHomeButtonClick()
	{
		onBackPressed();
	}

	protected void showOrHideMenu()
	{
		if (_drawerLayout.isDrawerOpen(Gravity.LEFT))
			_drawerLayout.closeDrawer(Gravity.LEFT);
		else
			_drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			onHomeButtonClick();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	class MainMenuOnClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int index,
				long id)
		{

			MainMenuItem item = (MainMenuItem) adapter.getAdapter().getItem(
					index);

			_drawerLayout.closeDrawer(Gravity.LEFT);
			onMainMenuItemClick(item);
		}
	}

	public static void setRatingBarColor(RatingBar ratingBar, int color)
	{
		LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
		stars.getDrawable(2).setColorFilter(color,
				android.graphics.PorterDuff.Mode.SRC_ATOP);
	}

	public WebClient createWebClient()
	{
		return new WebClient(this);
	}

	public void setImage(ImageView view, String key)
	{
		ImageDownloadTask.startNew(view, this, key);
	}

	public static Drawable getButtonDrawable(CatalogSettings settings)
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[]
		{ android.R.attr.state_pressed }, press);
		drawable.addState(new int[]
		{ -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
	}

	class CatalogLoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception = null;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			_progressDialog = ProgressDialog.show(BaseActivity.this,
					"Загрузка", "Выполняется загрузка каталога");
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				Repository.get(BaseActivity.this)
						.loadCatalog(BaseActivity.this);
			}
			catch (Throwable ex)
			{
				exception = ex;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			if (_destroyed)
				return;

			_progressDialog.dismiss();

			if (exception == null)
			{
				loadUI();
			}
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BaseActivity.this);
				builder.setTitle("Ошибка загрузки каталога");
				builder.setMessage(exception.getLocalizedMessage());
				builder.setCancelable(true);
				builder.setPositiveButton("Повторить загрузку",
						new DialogInterface.OnClickListener()
						{ // Кнопка ОК
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								dialog.dismiss();

								CatalogLoadTask task = new CatalogLoadTask();
								task.execute();
							}
						});
				builder.setNegativeButton("Выход",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								dialog.dismiss();
								finish();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}
	}

	/*
	 * class GetOrderCountTask extends AsyncTask<Void, Void, Void> {
	 * 
	 * @Override protected Void doInBackground(Void... params) { try { WebClient
	 * client = createWebClient(); long count =
	 * client.getOrderCount(Token.getCurrent(), Repository.CatalogId);
	 * Repository.get(null).getOrderManager().setOrderCount(count); } catch
	 * (Throwable ex) { Log.e("GetOrderCountTask", ex.getLocalizedMessage(),
	 * ex); }
	 * 
	 * return null; }
	 * 
	 * }
	 */

	class GetOrderCatalogsTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				WebClient client = createWebClient();
				Collection<Catalog> catalogs = client.getOrderCatalogs(Token
						.getCurrent());
				Repository.get(null).getOrderManager()
						.setOrderCatalogs(catalogs);
			}
			catch (Throwable ex)
			{
				Log.e("GetOrderCountTask", ex.getLocalizedMessage(), ex);
			}

			return null;
		}
	}
}
