package stx.shopclient.ui.common.properties.dialogs;

import org.apache.commons.lang3.StringUtils;

import stx.shopclient.R;
import stx.shopclient.entity.properties.NumberPropertyDescriptor;
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
		View.OnFocusChangeListener
{
	DialogResultProcessor _resultProcessor;
	NumberPropertyDescriptor _property;
	private View _itemView;

	EditText _numberFromEditText;
	EditText _numberToEditText;

	public void setResultProcessor(DialogResultProcessor processor)
	{
		_resultProcessor = processor;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(_property.getTitle());

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (_property.isRange())
				{
					if (_numberFromEditText.getText().length() != 0)
					{
						double val = Double.parseDouble(_numberFromEditText
								.getText().toString());

						if (val < _property.getMinValue())
							val = _property.getMinValue();
						if (val > _property.getMaxValue())
							val = _property.getMaxValue();

						_property.setCurrentMinValue(val);
						_property.setCurrentMinValueDefined(true);
					}
					else
						_property.setCurrentMinValueDefined(false);

					if (_numberToEditText.getText().length() != 0)
					{
						double val = Double.parseDouble(_numberToEditText
								.getText().toString());

						if (val < _property.getMinValue())
							val = _property.getMinValue();
						if (val > _property.getMaxValue())
							val = _property.getMaxValue();

						_property.setCurrentMaxValue(val);
						_property.setCurrentMaxValueDefined(true);
					}
					else
						_property.setCurrentMaxValueDefined(false);

					if (_property.isCurrentMinValueDefined()
							|| _property.isCurrentMaxValueDefined())
					{
						_property.setCurrentValueDefined(true);
						_resultProcessor.onPositiveDialogResult(_itemView);
					}
				}
				else
				{
					if (_numberFromEditText.getText().length() != 0)
					{
						double val = Double.parseDouble(_numberFromEditText
								.getText().toString());

						if (val < _property.getMinValue())
							val = _property.getMinValue();
						if (val > _property.getMaxValue())
							val = _property.getMaxValue();

						_property.setCurrentMinValue(val);
						_property.setCurrentMinValueDefined(true);
						_property.setCurrentValueDefined(true);
						_resultProcessor.onPositiveDialogResult(_itemView);
					}
					else
						_property.setCurrentMinValueDefined(false);
				}
			}
		});

		builder.setNegativeButton("Отмена",
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						_resultProcessor.onNegativeDialogResult(_itemView);
					}
				});

		builder.setView(createContentView());

		return builder.create();
	}

	View createContentView()
	{
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.search_activity_number_dialog, null);

		_numberFromEditText = (EditText) view
				.findViewById(R.id.numberFromEditText);
		_numberToEditText = (EditText) view.findViewById(R.id.numberToEditText);

		_numberFromEditText.setOnFocusChangeListener(this);
		_numberToEditText.setOnFocusChangeListener(this);

		if (!_property.isRange())
		{
			_numberToEditText.setVisibility(View.GONE);
			_numberFromEditText.setHint("");
		}

		if (_property.isCurrentValueDefined())
		{
			if (_property.isCurrentMinValueDefined())
				setEditTextValue(_numberFromEditText,
						_property.getCurrentMinValue());
			if (_property.isCurrentMaxValueDefined())
				setEditTextValue(_numberToEditText,
						_property.getCurrentMaxValue());
		}

		_numberFromEditText.post(new Runnable()
		{

			@Override
			public void run()
			{
				_numberFromEditText.requestFocusFromTouch();

				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(_numberFromEditText,
						InputMethodManager.SHOW_IMPLICIT);
			}
		});

		return view;
	}

	public NumberPropertyDescriptor getProperty()
	{
		return _property;
	}

	public void setProperty(NumberPropertyDescriptor property)
	{
		_property = property;
	}

	public View getItemView()
	{
		return _itemView;
	}

	public void setItemView(View itemView)
	{
		_itemView = itemView;
	}

	void setEditTextValue(EditText edit, double value)
	{
		if (_property.isFloat())
			edit.setText(Double.toString(value));
		else
			edit.setText(Integer.toString((int) value));
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		if (!hasFocus
				&& (v.equals(_numberFromEditText) || v
						.equals(_numberToEditText)))
		{

			EditText edit = (EditText) v;

			if (edit.getText().length() == 0)
				return;

			double val = Double.parseDouble(edit.getText().toString());

			if (val < _property.getMinValue())
			{
				setEditTextValue(edit, _property.getMinValue());
			}
			else if (val > _property.getMaxValue())
			{
				setEditTextValue(edit, _property.getMaxValue());
			}
		}
	}
}
