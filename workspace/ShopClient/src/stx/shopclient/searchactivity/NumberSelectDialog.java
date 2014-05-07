package stx.shopclient.searchactivity;

import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.NumberPropertyDescriptor;
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

public class NumberSelectDialog extends DialogFragment implements
		View.OnFocusChangeListener {
	DialogResultProcessor _resultProcessor;
	NumberPropertyDescriptor _property;
	private View _itemView;

	EditText _numberFromEditText;
	EditText _numberToEditText;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			_resultProcessor = (DialogResultProcessor) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement DialogResultProcessor");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(_property.getTitle());

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (_numberFromEditText.getText().length() != 0
						&& _numberToEditText.getText().length() != 0) {

					double val1 = Double.parseDouble(_numberFromEditText
							.getText().toString());
					double val2 = Double.parseDouble(_numberToEditText
							.getText().toString());
					
					if(val1 < _property.getMinValue())
						val1 = _property.getMinValue();
					if(val2 < _property.getMinValue())
						val2 = _property.getMinValue();
					if(val1 > _property.getMaxValue())
						val1 = _property.getMaxValue();
					if(val2 > _property.getMaxValue())
						val2 = _property.getMaxValue();

					_property.setCurrentMinValue(val1 < val2 ? val1 : val2);
					_property.setCurrentMaxValue(val1 > val2 ? val1 : val2);
					_property.setCurrentValueDefined(true);

					_resultProcessor.onPositiveDialogResult(_itemView);
				}
			}
		});

		builder.setNegativeButton("Отмена",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						_resultProcessor.onNegativeDialogResult(_itemView);
					}
				});

		builder.setView(createContentView());

		return builder.create();
	}

	View createContentView() {
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.search_activity_number_dialog, null);

		_numberFromEditText = (EditText) view
				.findViewById(R.id.numberFromEditText);
		_numberToEditText = (EditText) view.findViewById(R.id.numberToEditText);

		_numberFromEditText.setOnFocusChangeListener(this);
		_numberToEditText.setOnFocusChangeListener(this);

		if (_property.isCurrentValueDefined()) {
			_numberFromEditText.setText(Double.toString(_property
					.getCurrentMinValue()));
			_numberToEditText.setText(Double.toString(_property
					.getCurrentMaxValue()));
		}

		_numberFromEditText.post(new Runnable() {

			@Override
			public void run() {
				_numberFromEditText.requestFocusFromTouch();

				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(_numberFromEditText,
						InputMethodManager.SHOW_IMPLICIT);
			}
		});

		return view;
	}

	public NumberPropertyDescriptor getProperty() {
		return _property;
	}

	public void setProperty(NumberPropertyDescriptor property) {
		_property = property;
	}

	public View getItemView() {
		return _itemView;
	}

	public void setItemView(View itemView) {
		_itemView = itemView;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus
				&& (v.equals(_numberFromEditText) || v
						.equals(_numberToEditText))) {
			
			EditText edit = (EditText) v;
			
			if(edit.getText().length() == 0)
				return;

			double val = Double.parseDouble(edit.getText().toString());

			if (val < _property.getMinValue()) {
				edit.setText(Double.toString(_property.getMinValue()));
			} else if (val > _property.getMaxValue()) {
				edit.setText(Double.toString(_property.getMaxValue()));
			}
		}
	}
}
