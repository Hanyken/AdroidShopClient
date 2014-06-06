package stx.shopclient.loginactivity;

import stx.shopclient.R;
import stx.shopclient.mainactivity.MainActivity;
import stx.shopclient.settings.UserAccount;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity
{
	EditText _loginEdit;
	EditText _passwordEdit;

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
		
		Button registerButton = (Button)findViewById(R.id.registerButton);
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
			UserAccount.setLogin(_loginEdit.getText().toString());
			UserAccount.setPassword(_passwordEdit.getText().toString());
			UserAccount.save(this);
			
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);	
			startActivity(intent);			
			finish();
		}
	}
	
	void registerButtonClick()
	{
		Intent intent = new Intent(this, RegisterActivity.class);		
		startActivity(intent);
	}
}
