package stx.shopclient.ui.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import stx.shopclient.R;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class StxDatePicker extends FrameLayout implements
		DatePickerDialog.OnDateSetListener {

	boolean _isDateDefined = false;
	private GregorianCalendar _date = new GregorianCalendar();
	private GregorianCalendar _minDate = new GregorianCalendar();
	private GregorianCalendar _maxDate = new GregorianCalendar();

	ImageView _resetImage;
	EditText _dateEditText;

	public StxDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.addView(createView());
	}

	View createView() {
		View view = inflate(getContext(), R.layout.stx_datepicker, null);

		_resetImage = (ImageView) view.findViewById(R.id.resetImageView);
		_dateEditText = (EditText) view.findViewById(R.id.editText);
		
		_resetImage.setVisibility(View.GONE);
		
		_resetImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				resetDate();
			}
		});

		_dateEditText.setHint("����");
		_dateEditText.setKeyListener(null);
		_dateEditText.setFocusable(false);
		_dateEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});

		return view;
	}

	void showDialog() {
		DatePickerDialog dialog = new DatePickerDialog(getContext(), this,
				_date.get(GregorianCalendar.YEAR),
				_date.get(GregorianCalendar.MONTH),
				_date.get(GregorianCalendar.DAY_OF_MONTH));
		
		dialog.show();
	}

	public GregorianCalendar getDate() {
		return _date;
	}

	public void setDate(GregorianCalendar date) {
		_date = date;
		_resetImage.setVisibility(View.VISIBLE);
		_dateEditText.setText(new SimpleDateFormat("dd.MM.yyyy").format(_date.getTime()));
		_isDateDefined = true;
	}
	
	public void resetDate(){
		_resetImage.setVisibility(View.GONE);
		_dateEditText.setText("");
		_isDateDefined = false;
	}

	@Override
	public void onDateSet(DatePicker picker, int y, int m, int d) {
		GregorianCalendar date = new GregorianCalendar(y, m, d);
		
		setDate(date);
	}

	public GregorianCalendar getMinDate() {
		return _minDate;
	}

	public void setMinDate(GregorianCalendar minDate) {
		_minDate = minDate;
	}

	public GregorianCalendar getMaxDate() {
		return _maxDate;
	}

	public void setMaxDate(GregorianCalendar maxDate) {
		_maxDate = maxDate;
	}

}
