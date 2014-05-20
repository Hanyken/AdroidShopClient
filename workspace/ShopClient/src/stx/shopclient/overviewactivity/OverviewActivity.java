package stx.shopclient.overviewactivity;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import android.os.Bundle;
import android.widget.ListView;

public class OverviewActivity extends BaseActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_activity);
		
		ListView lstMain = (ListView)findViewById(R.id.lstMain);
		
		OverviewAdapter adapter = new OverviewAdapter(this, lstMain);
		
		lstMain.setAdapter(adapter);
		lstMain.setOnScrollListener(adapter);
	}
}
