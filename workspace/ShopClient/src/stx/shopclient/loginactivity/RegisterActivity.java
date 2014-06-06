package stx.shopclient.loginactivity;

import stx.shopclient.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_activity);
		
		getActionBar().setTitle("Регистрация");
		
		Button registerButton = (Button)findViewById(R.id.registerButton);
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
		
	}
}
