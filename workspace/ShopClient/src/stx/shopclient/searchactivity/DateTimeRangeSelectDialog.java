package stx.shopclient.searchactivity;

import java.util.GregorianCalendar;

import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.DatePropertyDescriptor;
import stx.shopclient.ui.common.StxDatePicker;
import stx.shopclient.ui.common.StxTimePicker;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class DateTimeRangeSelectDialog extends DialogFragment {
	DialogResultProcessor _resultProcessor;
	DatePropertyDescriptor _property;
	private View _itemView;

	StxDatePicker _fromDatePicker;
	StxDatePicker _toDatePicker;
	
	StxTimePicker _fromTimePicker;
	StxTimePicker _toTimePicker;

	public void setResultProcessor(DialogResultProcessor processor){
		_resultProcessor = processor;
	}

	void onpositiveButtonClick() {
		
		if (_fromDatePicker.isDateDefined() && _toDatePicker.isDateDefined()) {
			
			GregorianCalendar dateFrom = _fromDatePicker.getDate();
			GregorianCalendar dateTo = _toDatePicker.getDate();
			
			GregorianCalendar timeFrom = _fromTimePicker.getDate();
			GregorianCalendar timeTo = _toTimePicker.getDate();
			
			dateFrom.set(GregorianCalendar.HOUR_OF_DAY, timeFrom.get(GregorianCalendar.HOUR_OF_DAY));
			dateFrom.set(GregorianCalendar.MINUTE, timeFrom.get(GregorianCalendar.MINUTE));
			
			dateTo.set(GregorianCalendar.HOUR_OF_DAY, timeTo.get(GregorianCalendar.HOUR_OF_DAY));
			dateTo.set(GregorianCalendar.MINUTE, timeTo.get(GregorianCalendar.MINUTE));
			
			_property.setCurrentMinValue(dateFrom);
			_property.setCurrentMaxValue(dateTo);
			_property.setCurrentValueDefined(true);
			
			_resultProcessor.onPositiveDialogResult(_itemView);
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(_property.getTitle());

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				onpositiveButtonClick();
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
				R.layout.search_activity_date_dialog, null);

		_fromDatePicker = (StxDatePicker) view
				.findViewById(R.id.dateFromPicker);
		_toDatePicker = (StxDatePicker) view.findViewById(R.id.dateToPicker);
		
		_fromTimePicker = (StxTimePicker)view.findViewById(R.id.timeFromPicker);
		_toTimePicker = (StxTimePicker)view.findViewById(R.id.timeToPicker);
		
		_fromDatePicker.setAllowReset(false);
		_fromTimePicker.setAllowReset(false);
		_toDatePicker.setAllowReset(false);
		_toTimePicker.setAllowReset(false);

		if (_property.isCurrentValueDefined()) {
			_fromDatePicker.setDate(_property.getCurrentMinValue());
			_toDatePicker.setDate(_property.getCurrentMaxValue());
			
			_fromTimePicker.setDate(_property.getCurrentMinValue());
			_toTimePicker.setDate(_property.getCurrentMaxValue());			
		}

		return view;
	}

	public DatePropertyDescriptor getProperty() {
		return _property;
	}

	public void setProperty(DatePropertyDescriptor property) {
		_property = property;
	}

	public View getItemView() {
		return _itemView;
	}

	public void setItemView(View itemView) {
		_itemView = itemView;
	}
}
