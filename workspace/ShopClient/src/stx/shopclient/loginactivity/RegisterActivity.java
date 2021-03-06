package stx.shopclient.loginactivity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import stx.shopclient.R;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.Token;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.settings.UserAccount;
import stx.shopclient.ui.common.StxDatePicker;
import stx.shopclient.utils.DisplayUtil;
import stx.shopclient.utils.ProgressDlgUtil;
import stx.shopclient.webservice.ServiceResponseCode;
import stx.shopclient.webservice.WebClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
	RadioGroup _genderRadioGroup;

	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Repository.get(this)
				.getCatalogManager().getSettings().getBackground()));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.register_activity);

		getActionBar().setTitle("�����������");

		_birthdayPicker = (StxDatePicker) findViewById(R.id.birthdayPicker);
		_birthdayPicker.setAllowReset(false);

		_loginEdit = (EditText) findViewById(R.id.loginEditText);
		_passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		_password2EditText = (EditText) findViewById(R.id.password2EditText);
		_phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
		_lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
		_firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
		_middleNameEditText = (EditText) findViewById(R.id.middleNameEditText);
		_genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
		
		_genderRadioGroup.check(R.id.genderMaleRadioButton);

		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				registerButtonClick();
			}
		});
		CatalogSettings settings = Repository.get(this).getCatalogManager().getSettings();
		registerButton.setBackground(DisplayUtil.getButtonDrawable(settings));
		registerButton.setTextColor(settings.getForegroundColor());
	}

	void registerButtonClick()
	{
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
				.getWindowToken(), 0);

		if (_loginEdit.getText().toString().equals(""))
		{
			showErrorMessage("������� �����");
			return;
		}
		if (_passwordEditText.getText().toString().equals("")
				|| _password2EditText.getText().toString().equals(""))
		{
			showErrorMessage("������� ������");
			return;
		}
		if (!_passwordEditText.getText().toString()
				.equals(_password2EditText.getText().toString()))
		{
			showErrorMessage("������ � ������������� ������ ������ ���������");
			return;
		}
		if (_firstNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("������� ���");
			return;
		}
		if (_lastNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("������� �������");
			return;
		}
		if (_middleNameEditText.getText().toString().equals(""))
		{
			showErrorMessage("������� ��������");
			return;
		}
		if (_phoneNumberEditText.getText().toString().equals(""))
		{
			showErrorMessage("������� ����� ��������");
			return;
		}
		if (!_birthdayPicker.isDateDefined())
		{
			showErrorMessage("������� ���� ��������");
			return;
		}

		dialog = ProgressDialog.show(RegisterActivity.this, "��������",
				"����������� �����������", true);
		ProgressDlgUtil.setCancellable(dialog, this);

		RegisterTask task = new RegisterTask();
		task.login = _loginEdit.getText().toString();
		task.password = _passwordEditText.getText().toString();
		task.firstName = _firstNameEditText.getText().toString();
		task.lastName = _lastNameEditText.getText().toString();
		task.middleName = _middleNameEditText.getText().toString();
		task.phoneNumber = _phoneNumberEditText.getText().toString();
		task.birthday = _birthdayPicker.getDate().getTime();
		task.gender = _genderRadioGroup.getCheckedRadioButtonId() == R.id.genderMaleRadioButton ? "M"
				: "W";
		task.execute();
	}

	void showErrorMessage(String message)
	{
		new AlertDialog.Builder(this)
				.setTitle("������")
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
		public String gender;

		@Override
		protected Token doInBackground(Void... params)
		{
			try
			{
				DisplayMetrics displayMetrics = getResources()
						.getDisplayMetrics();

				TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
				String simNumber = mTelephonyMgr.getSimSerialNumber();
				if (StringUtils.isBlank(simNumber))
					simNumber = "nosim";

				WebClient client = new WebClient(RegisterActivity.this);

				Token token = client.register(login, password, firstName,
						middleName, lastName, phoneNumber, simNumber, birthday,
						"userAgent", displayMetrics.widthPixels,
						displayMetrics.heightPixels, null, null, null, null,
						null, gender);
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
			if (isDestroyed())
				return;
			
			dialog.dismiss();

			if (result == null)
			{
				Toast.makeText(RegisterActivity.this, "������ �����������",
						Toast.LENGTH_LONG).show();
				return;
			}
			else if (result.getToken() == null || result.getToken().equals(""))
			{
				String error = ServiceResponseCode.getMessage(result.getCode());

				Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG)
						.show();
				return;
			}

			DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

			Token.setCurrent(result);
			UserAccount.setLogin(login);
			UserAccount.setPassword(password);
			UserAccount.setWidth(displayMetrics.widthPixels);
			UserAccount.setHeight(displayMetrics.heightPixels);
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
