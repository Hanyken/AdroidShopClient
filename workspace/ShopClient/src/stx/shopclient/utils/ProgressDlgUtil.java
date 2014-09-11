package stx.shopclient.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public class ProgressDlgUtil
{
	public static void setCancellable(ProgressDialog dialog, Activity activity_)
	{
		final Activity activity = activity_;
		dialog.setCancelable(true);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			
			@Override
			public void onCancel(DialogInterface dialog)
			{
				activity.finish();
			}
		});
	}
}
