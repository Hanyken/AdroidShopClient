package stx.shopclient.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ProgressDialogAsyncTask<TResult> extends AsyncTask<Void, Void, Void>
{
	protected Context context;
	protected String dialogMessage;
	protected ProgressDialog dialog;
	protected Throwable exception;
	protected TResult result;
	
	public ProgressDialogAsyncTask(Context context, String message)
	{
		super();
		
		this.context = context;
		this.dialogMessage = message;
	}

	protected TResult backgroundTask() throws Throwable
	{
		return null;
	}

	protected void onPostExecuteNoError(TResult result)
	{
		
	}

	@Override
	protected void onPreExecute()
	{
		dialog = ProgressDialog.show(context, "Загрузка", dialogMessage);
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			result = backgroundTask();
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
		dialog.dismiss();

		if (exception != null)
			Toast.makeText(context, exception.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		else
		{
			onPostExecuteNoError(this.result);
		}
	}

}
