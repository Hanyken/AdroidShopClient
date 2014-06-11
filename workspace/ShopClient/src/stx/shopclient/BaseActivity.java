package stx.shopclient;

import java.util.Date;
import java.util.GregorianCalendar;

import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.discountactivity.DiscountListActivity;
import stx.shopclient.entity.Token;
import stx.shopclient.loginactivity.LoginActivity;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.mainmenu.MainMenuListAdapter;
import stx.shopclient.messagesactivity.MessagesListActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.searchactivity.SearchActivity;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.settingsactivity.SettingsActivity;
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
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;

public class BaseActivity extends FragmentActivity
{
	DrawerLayout _drawerLayout;
	LinearLayout _mainViewContainer;
	MainMenuListAdapter _mainMenuListAdapter;
	ProgressDialog _progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (!checkUserAccount())
		{
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			return;
		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Repository
				.getIntent(this).getCatalogManager().getSettings()
				.getBackground()));

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
				_mainMenuListAdapter.notifyDataSetChanged();
			}

			@Override
			public void onDrawerClosed(View arg0)
			{

			}
		});

		ListView _menuList = (ListView) findViewById(R.id.mainMenuList);

		_mainMenuListAdapter = new MainMenuListAdapter(this);
		_menuList.setAdapter(_mainMenuListAdapter);
		_menuList.setOnItemClickListener(new MainMenuOnClickListener());

		_mainViewContainer = (LinearLayout) findViewById(R.id.mainViewContainer);

		if (Repository.getIntent(this).getCatalogManager().getCatalog() == null)
		{
			CatalogLoadTask task = new CatalogLoadTask();
			task.execute();
		}
		else
			loadMainView();
	}

	void loadMainView()
	{
		View mainView = createMainView(_mainViewContainer);

		if (mainView != null)
		{
			_mainViewContainer.addView(mainView);
		}
	}

	protected boolean checkUserAccount()
	{
		UserAccount.load(this);
		if (UserAccount.getLogin() == null || UserAccount.getLogin().equals(""))
			return false;
		else
		{
			boolean needLogin = false;

			if (Token.getCurrent() == null)
				needLogin = true;
			if (Token.getCurrent() != null)
			{
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(Token.getCurrent().getBegDate());
				cal.add(GregorianCalendar.SECOND, Token.getCurrent()
						.getInterval());

				if (cal.after(new GregorianCalendar()))
					needLogin = true;
			}

			if (needLogin)
			{
				WebClient client = new WebClient(this);
				DisplayMetrics metrics = getResources().getDisplayMetrics();
				try
				{
					Token token = client.login(UserAccount.getLogin(),
							UserAccount.getPassword(), metrics.widthPixels,
							metrics.heightPixels);

					if (token != null)
					{
						if (token.getCode() != ServiceResponseCode.OK)
							return false;
					}
					else
						return false;
				}
				catch (Throwable ex)
				{
					return false;
				}
			}

			return true;
		}
	}

	protected void onMainMenuItemClick(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.HOME_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, MainActivity.class);
			NavUtils.navigateUpTo(this, intent);
		}
		else if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, SearchActivity.class);
			intent.putExtra(SearchActivity.TITLE_EXTRA_KEY,
					getSearchActivityTitle());
			startActivity(intent);
		}
		else if (item.getId() == MainMenuItem.CART_MENU_ITEM_ID)
		{
			Intent intent = new Intent(this, CartActivity.class);
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
	}

	protected String getSearchActivityTitle()
	{
		return getActionBar().getTitle().toString();
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

	class CatalogLoadTask extends AsyncTask<Void, Void, Void>
	{
		Throwable exception = null;

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			_progressDialog = ProgressDialog.show(BaseActivity.this,
					"Загрузка каталога", "");
		}

		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				Repository.getIntent(BaseActivity.this).loadCatalog(
						BaseActivity.this);
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

			_progressDialog.dismiss();

			if (exception == null)
			{
				loadMainView();
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
}
