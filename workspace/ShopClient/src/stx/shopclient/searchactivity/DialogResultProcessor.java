package stx.shopclient.searchactivity;

import android.view.View;

public interface DialogResultProcessor {
	public void onPositiveDialogResult(View view);

	public void onNegativeDialogResult(View view);
}
