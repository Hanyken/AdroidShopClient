package stx.shopclient.ui.common;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class StxTimePicker extends StxDatePicker implements OnTimeSetListener {

	public StxTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);

		_dateEditText.setHint("Время");
	}

	@Override
	void showDialog() {
		TimePickerDialog dialog = new TimePickerDialog(getContext(), this,
				_date.get(GregorianCalendar.HOUR_OF_DAY),
				_date.get(GregorianCalendar.MINUTE), true);

		dialog.show();
	}

	public void setDate(GregorianCalendar date) {
		_date = date;

		if (_allowReset)
			_resetImage.setVisibility(View.VISIBLE);
		
		_dateEditText.setText(new SimpleDateFormat("HH:mm").format(_date
				.getTime()));
		setDateDefined(true);
	}

	@Override
	public void onTimeSet(TimePicker arg0, int h, int m) {

		_date.set(GregorianCalendar.HOUR_OF_DAY, h);
		_date.set(GregorianCalendar.MINUTE, m);

		setDate(_date);
	}
}
