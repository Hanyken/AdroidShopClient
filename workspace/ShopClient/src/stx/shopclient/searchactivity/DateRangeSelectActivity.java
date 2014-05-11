package stx.shopclient.searchactivity;

import stx.shopclient.R;
import stx.shopclient.entity.searchproperties.DatePropertyDescriptor;
import stx.shopclient.ui.common.StxDatePicker;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DateRangeSelectActivity extends FragmentActivity {
	
	DatePropertyDescriptor _property;
	
	StxDatePicker _fromDatePicker;
	StxDatePicker _toDatePicker;
	
	@Override
	protected void onCreate(Bundle arg0) {		
		super.onCreate(arg0);
		
		setContentView(R.layout.search_activity_date_dialog);
		getActionBar().hide();
		
		_property = DatePropertyDescriptor.getCurrentEditedProperty();
		
		_fromDatePicker = (StxDatePicker)findViewById(R.id.dateFromPicker);
		_toDatePicker = (StxDatePicker)findViewById(R.id.dateToPicker);
		
		if(_property.isCurrentValueDefined()){
			_fromDatePicker.setDate(_property.getCurrentMinValue());
			_toDatePicker.setDate(_property.getCurrentMaxValue());
		}
		
//		Button buttonOk = (Button) findViewById(R.id.buttonOk);
//		Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
//
//		buttonOk.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				_property.setCurrentMinValue(_fromDatePicker.getDate());
//				_property.setCurrentMaxValue(_toDatePicker.getDate());
//				finish();
//			}
//		});
//
//		buttonCancel.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
//
//		TextView titleText = (TextView) findViewById(R.id.titleText);
//		titleText.setText(_property.getTitle());
	}
}
