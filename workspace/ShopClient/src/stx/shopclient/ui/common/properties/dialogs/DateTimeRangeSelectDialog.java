package stx.shopclient.ui.common.properties.dialogs;

import java.util.GregorianCalendar;

import stx.shopclient.R;
import stx.shopclient.entity.properties.DatePropertyDescriptor;
import stx.shopclient.ui.common.StxDatePicker;
import stx.shopclient.ui.common.StxTimePicker;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DateTimeRangeSelectDialog extends DialogFragment
{
	DialogResultProcessor _resultProcessor;
	DatePropertyDescriptor _property;
	private View _itemView;

	StxDatePicker _fromDatePicker;
	StxDatePicker _toDatePicker;

	StxTimePicker _fromTimePicker;
	StxTimePicker _toTimePicker;

	public void setResultProcessor(DialogResultProcessor processor)
	{
		_resultProcessor = processor;
	}

	void onpositiveButtonClick()
	{

		if (_property.isRange())
		{
			if (_fromDatePicker.isDateDefined())
			{
				GregorianCalendar dateFrom = _fromDatePicker.getDate();
				GregorianCalendar timeFrom = _fromTimePicker.getDate();

				dateFrom.set(GregorianCalendar.HOUR_OF_DAY,
						timeFrom.get(GregorianCalendar.HOUR_OF_DAY));
				dateFrom.set(GregorianCalendar.MINUTE,
						timeFrom.get(GregorianCalendar.MINUTE));

				_property.setCurrentMinValue(dateFrom);
				_property.setCurrentMinValueDefined(true);
			}
			else
				_property.setCurrentMinValueDefined(false);

			if (_toDatePicker.isDateDefined())
			{
				GregorianCalendar dateFrom = _toDatePicker.getDate();
				GregorianCalendar timeFrom = _toTimePicker.getDate();

				dateFrom.set(GregorianCalendar.HOUR_OF_DAY,
						timeFrom.get(GregorianCalendar.HOUR_OF_DAY));
				dateFrom.set(GregorianCalendar.MINUTE,
						timeFrom.get(GregorianCalendar.MINUTE));

				_property.setCurrentMaxValue(dateFrom);
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
			if (_fromDatePicker.isDateDefined())
			{
				GregorianCalendar dateFrom = _fromDatePicker.getDate();
				GregorianCalendar timeFrom = _fromTimePicker.getDate();

				dateFrom.set(GregorianCalendar.HOUR_OF_DAY,
						timeFrom.get(GregorianCalendar.HOUR_OF_DAY));
				dateFrom.set(GregorianCalendar.MINUTE,
						timeFrom.get(GregorianCalendar.MINUTE));

				_property.setCurrentMinValue(dateFrom);
				_property.setCurrentMinValueDefined(true);
				_property.setCurrentValueDefined(true);
				_resultProcessor.onPositiveDialogResult(_itemView);
			}
			else
			{
				_property.setCurrentMinValueDefined(false);
				_property.setCurrentValueDefined(false);
			}
		}
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
				onpositiveButtonClick();
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
				R.layout.search_activity_date_dialog, null);

		_fromDatePicker = (StxDatePicker) view
				.findViewById(R.id.dateFromPicker);
		_toDatePicker = (StxDatePicker) view.findViewById(R.id.dateToPicker);

		_fromTimePicker = (StxTimePicker) view
				.findViewById(R.id.timeFromPicker);
		_toTimePicker = (StxTimePicker) view.findViewById(R.id.timeToPicker);

		_fromDatePicker.setAllowReset(false);
		_fromTimePicker.setAllowReset(false);
		_toDatePicker.setAllowReset(false);
		_toTimePicker.setAllowReset(false);

		if (!_property.isRange())
		{
			TextView fromTextView = (TextView) view
					.findViewById(R.id.fromTextView);
			fromTextView.setVisibility(View.GONE);

			TextView toTextView = (TextView) view.findViewById(R.id.toTextView);
			toTextView.setVisibility(View.GONE);

			_toDatePicker.setVisibility(View.GONE);
			_toTimePicker.setVisibility(View.GONE);
		}

		if (_property.isCurrentValueDefined())
		{
			if (_property.isCurrentMinValueDefined())
			{
				_fromDatePicker.setDate(_property.getCurrentMinValue());
				_fromTimePicker.setDate(_property.getCurrentMinValue());
			}

			if (_property.isCurrentMaxValueDefined())
			{
				_toDatePicker.setDate(_property.getCurrentMaxValue());
				_toTimePicker.setDate(_property.getCurrentMaxValue());
			}
		}

		return view;
	}

	public DatePropertyDescriptor getProperty()
	{
		return _property;
	}

	public void setProperty(DatePropertyDescriptor property)
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
}
