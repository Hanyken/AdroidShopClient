package stx.shopclient.ui.common.properties.dialogs;

import stx.shopclient.entity.properties.EnumPropertyDescriptor;
import stx.shopclient.entity.properties.EnumPropertyDescriptor.EnumValue;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class EnumSelectDialog extends DialogFragment {

	DialogResultProcessor _resultProcessor;
	private EnumPropertyDescriptor _property;
	private View _itemView;
	
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

		String[] items = new String[_property.getValues().size()];
		boolean[] checkedItems = new boolean[_property.getValues().size()];

		for (int i = 0; i < _property.getValues().size(); i++) {
			items[i] = _property.getValues().get(i).getName();

			for (EnumValue enumVal : _property.getCurrentValues()) {
				if (enumVal.getName().equals(items[i])) {
					checkedItems[i] = true;
					break;
				}
			}
		}
		
		builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				EnumValue value = _property.getValues().get(which);
				
				if(isChecked)
					_property.getCurrentValues().add(value);
				else
					_property.getCurrentValues().remove(value);
			}
		});

		return builder.create();
	}

	public EnumPropertyDescriptor getProperty() {
		return _property;
	}

	public void setProperty(EnumPropertyDescriptor property) {
		_property = property;
	}

	public View getItemView() {
		return _itemView;
	}

	public void setItemView(View itemView) {
		_itemView = itemView;
	}
}
