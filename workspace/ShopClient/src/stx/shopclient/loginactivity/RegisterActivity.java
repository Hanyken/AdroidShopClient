package stx.shopclient.loginactivity;

import java.util.Date;

import stx.shopclient.R;
import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.ui.common.StxDatePicker;
import stx.shopclient.utils.StringUtils;
import stx.shopclient.webservice.ServiceResponseCode;
import stx.shopclient.webservice.WebClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity
{
	EditText _loginEdit;
	EditText _passwordEditText;
	EditText _password2EditText;
	EditText _phoneNumberEditText;
	EditText _lastNameEditText;
	EditText _firstNameEditText;
	EditText _middleNameEditText;
	StxDatePicker _birthdayPicker;

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.register_activity);

		getActionBar().setTitle("–егистраци€");

		_birthdayPicker = (StxDatePicker) findViewById(R.id.birthdayPicker);
		_birthdayPicker.setAllowReset(false);

		_loginEdit = (EditText) findViewById(R.id.loginEditText);
		_passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		_password2EditText = (EditText) findViewById(R.id.password2EditText);
		_phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
		_lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
		_firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
		_middleNameEditText = (EditText) findViewById(R.id.middleNameEditText);

		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				registerButtonClick();
			}
		});
	}

	void registerButtonClick()
	{
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
				.getWindowToken(), 0);

		if (_loginEdit.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите логин");
			return;
		}
		if (_passwordEditText.getText().toString().equals("")
				|| _password2EditText.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите пароль");
			return;
		}
		if (!_passwordEditText.getText().toString()
				.equals(_password2EditText.getText().toString()))
		{
			showErrorMessage("ѕароль и подтверждение парол€ должны совпадать");
			return;
		}
		if (_firstNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите им€");
			return;
		}
		if (_lastNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите фамилию");
			return;
		}
		if (_middleNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите отчество");
			return;
		}
		if (_phoneNumberEditText.getText().toString().equals(""))
		{
			showErrorMessage("¬ведите номер телефона");
			return;
		}
		if (!_birthdayPicker.isDateDefined())
		{
			showErrorMessage("¬ведите дату рождени€");
			return;
		}

		dialog = ProgressDialog.show(RegisterActivity.this, "–егистраци€", "",
				true);

		RegisterTask task = new RegisterTask();
		task.login = _loginEdit.getText().toString();
		task.password = _passwordEditText.getText().toString();
		task.firstName = _firstNameEditText.getText().toString();
		task.lastName = _lastNameEditText.getText().toString();
		task.middleName = _middleNameEditText.getText().toString();
		task.phoneNumber = _phoneNumberEditText.getText().toString();
		task.birthday = _birthdayPicker.getDate().getTime();
		task.execute();
	}

	void showErrorMessage(String message)
	{
		new AlertDialog.Builder(this)
				.setTitle("ќшибка")
				.setMessage(message)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int which)
							{
							}
						}).show();
	}

	class RegisterTask extends AsyncTask<Void, Void, Token>
	{
		public String login;
		public String password;
		public String phoneNumber;
		public String lastName;
		public String firstName;
		public String middleName;
		public Date birthday;

		@Override
		protected Token doInBackground(Void... params)
		{
			try
			{
				DisplayMetrics displayMetrics = getResources()
						.getDisplayMetrics();

				TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String simNumber = mTelephonyMgr.getSimSerialNumber();
				if (StringUtils.isNullOrEmpty(simNumber))
					simNumber = "nosim";

				WebClient client = new WebClient(RegisterActivity.this);

				Token token = client.register(login, password, firstName,
						middleName, lastName, phoneNumber, simNumber, birthday,
						"userAgent", displayMetrics.widthPixels,
						displayMetrics.heightPixels, null, null, null, null,
						null);
				return token;
			}
			catch (Throwable ex)
			{
				Log.e("register", "error", ex);
				return null;
			}
		}

		@Override
		protected void onPostExecute(Token result)
		{
			super.onPostExecute(result);

			dialog.dismiss();

			if (result == null)
			{
				Toast.makeText(RegisterActivity.this, "ќшибка регистрации",
						Toast.LENGTH_LONG).show();
				return;
			}
			else if (result.getToken() == null || result.getToken().equals(""))
			{
				String error = ServiceResponseCode.getMessage(Integer.parseInt(result
						.getCode()));
				
				Toast.makeText(RegisterActivity.this, error,
						Toast.LENGTH_LONG).show();
				return;
			}

			Token.setCurrent(result);
			UserAccount.setLogin(login);
			UserAccount.setPassword(password);
			UserAccount.save(RegisterActivity.this);

			Intent intent = new Intent(RegisterActivity.this,
					MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
	}
}
