package stx.shopclient.settingsactivity;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Token;
import stx.shopclient.loginactivity.LoginActivity;
import stx.shopclient.mainmenu.MainMenuItem;
import stx.shopclient.settings.UserAccount;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends BaseActivity
{
	@Override
	protected View createMainView(ViewGroup parent)
	{
		getActionBar().setTitle("Настройки");

		View view = getLayoutInflater().inflate(R.layout.settings_activity,
				parent, false);

		UserAccount.load(this);

		Button logoutButton = (Button) view.findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				logoutButtonClick();
			}
		});

		TextView loginTextView = (TextView) view
				.findViewById(R.id.loginTextView);

		loginTextView.setText(UserAccount.getLogin());

		return view;
	}
	
	@Override
	public boolean initMainMenuItem(MainMenuItem item)
	{
		if (item.getId() == MainMenuItem.SEARCH_MENU_ITEM_ID)
			return false;
		else
			return super.initMainMenuItem(item);
	}

	void logoutButtonClick()
	{
		Token.setCurrent(null);

		UserAccount.setLogin("");
		UserAccount.setPassword("");
		UserAccount.save(this);

		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}
}
