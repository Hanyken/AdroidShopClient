package stx.shopclient.loginactivity;

import stx.shopclient.R;
import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.webservice.WebClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	EditText _loginEdit;
	EditText _passwordEdit;

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_activity);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		getActionBar().setTitle("¬ход");

		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				loginButtonClick();
			}
		});

		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				registerButtonClick();
			}
		});

		_loginEdit = (EditText) findViewById(R.id.loginEditText);
		_passwordEdit = (EditText) findViewById(R.id.passwordEditText);
	}

	void loginButtonClick()
	{
		if (!_loginEdit.getText().toString().equals(""))
		{
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(_loginEdit.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(_passwordEdit.getWindowToken(), 0);
			
			dialog = ProgressDialog.show(LoginActivity.this, "¬ход", "", true);

			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

			LoginTask task = new LoginTask(_loginEdit.getText().toString(),
					_passwordEdit.getText().toString(),
					displayMetrics.widthPixels, displayMetrics.heightPixels);
			task.execute();
		}
	}

	void registerButtonClick()
	{
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	class LoginTask extends AsyncTask<Void, Void, Token>
	{
		String login;
		String password;
		int width;
		int height;

		public LoginTask(String login, String password, int width, int height)
		{
			this.login = login;
			this.password = password;
			this.width = width;
			this.height = height;
		}

		@Override
		protected Token doInBackground(Void... params)
		{
			WebClient client = new WebClient(LoginActivity.this);

			return client.login(login, password, width, height);
		}

		@Override
		protected void onPostExecute(Token result)
		{
			super.onPostExecute(result);

			dialog.dismiss();

			if (result == null || result.getToken() == null
					|| result.getToken().equals(""))
			{
				Toast.makeText(LoginActivity.this, "ќшибка входа", Toast.LENGTH_LONG).show();
				return;
			}

			UserAccount.setLogin(login);
			UserAccount.setPassword(password);
			UserAccount.save(LoginActivity.this);

			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}

	}
}
