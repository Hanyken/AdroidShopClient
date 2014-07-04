package stx.shopclient.utils;

import org.apache.commons.lang3.StringUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ProgressDialogAsyncTask<TResult> extends
		AsyncTask<Void, Void, Void>
{
	protected Context context;
	protected String dialogMessage;
	protected ProgressDialog dialog;
	protected Throwable exception;
	protected TResult result;
	public boolean showProgressDialog = true;

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
		if (showProgressDialog)
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
		if (showProgressDialog)
			dialog.dismiss();

		if (exception != null)
			Toast.makeText(
					context,
					StringUtils.isBlank(exception.getLocalizedMessage()) ? exception
							.toString() : exception.getLocalizedMessage(),
					Toast.LENGTH_LONG).show();
		else
		{
			onPostExecuteNoError(this.result);
		}
	}

}
