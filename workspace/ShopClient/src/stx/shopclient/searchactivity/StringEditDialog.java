package stx.shopclient.searchactivity;

import stx.shopclient.entity.searchproperties.EnumPropertyDescriptor;
import stx.shopclient.entity.searchproperties.StringPropertyDescriptor;
import stx.shopclient.entity.searchproperties.EnumPropertyDescriptor.EnumValue;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class StringEditDialog extends DialogFragment {
	DialogResultProcessor _resultProcessor;
	private StringPropertyDescriptor _property;
	private View _itemView;

	EditText _edit;

	public void setResultProcessor(DialogResultProcessor processor){
		_resultProcessor = processor;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(_property.getTitle());

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				_property.setValue(_edit.getText().toString());
				_resultProcessor.onPositiveDialogResult(_itemView);
			}
		});

		builder.setNegativeButton("Отмена",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						_resultProcessor.onNegativeDialogResult(_itemView);
					}
				});

		_edit = new EditText(getActivity());
		_edit.setPadding(20, 20, 20, 20);		

		_edit.setText(_property.getValue());
		
		_edit.post(new Runnable() {

			@Override
			public void run() {
				_edit.requestFocusFromTouch();

				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(_edit,
						InputMethodManager.SHOW_IMPLICIT);
			}
		});

		builder.setView(_edit);

		return builder.create();
	}

	public StringPropertyDescriptor getProperty() {
		return _property;
	}

	public void setProperty(StringPropertyDescriptor property) {
		_property = property;
	}

	public View getItemView() {
		return _itemView;
	}

	public void setItemView(View itemView) {
		_itemView = itemView;
	}
}
