package stx.shopclient.historyactivity;

import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import stx.shopclient.orderactivity.OrderActivity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentDescriptionFragment extends DialogFragment implements
		android.view.View.OnClickListener
{
	private static final String ANANIM_NAME = "IsAnanim";
	EditText _txtMain;
	EditText _txtPhone;
	TextView _lblPhone;

	public static PaymentDescriptionFragment get(boolean isAnanim)
	{
		PaymentDescriptionFragment value = new PaymentDescriptionFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean(ANANIM_NAME, isAnanim);
		value.setArguments(bundle);
		return value;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View v = inflater.inflate(
				stx.shopclient.R.layout.pyment_description_fragment, container,
				false);
		_txtMain = (EditText) v.findViewById(stx.shopclient.R.id.txtComment);
		_txtPhone = (EditText) v.findViewById(stx.shopclient.R.id.txtPhone);
		_lblPhone = (TextView) v.findViewById(R.id.lblPhone);

		_txtPhone
				.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		if (!getArguments().getBoolean(ANANIM_NAME, true))
		{
			_txtPhone.setVisibility(View.GONE);
			_txtPhone = null;
			_lblPhone.setVisibility(View.GONE);
		}

		((Button) v.findViewById(R.id.btnOk)).setOnClickListener(this);
		((Button) v.findViewById(R.id.btnCancel)).setOnClickListener(this);

		return v;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dlg = super.onCreateDialog(savedInstanceState);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setTitle(R.string.comment);
		this.setStyle(DialogFragment.STYLE_NORMAL,
				android.R.style.Theme_Holo_Light_Dialog);

		// final Drawable d = new ColorDrawable(Color.BLACK);
		// d.setAlpha(0);
		// dlg.getWindow().setBackgroundDrawable(d);

		return dlg;
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.btnOk)
		{
			String comment = "";
			if (_txtPhone != null)
			{
				if (_txtPhone.getText().length() < 15)
				{
					Toast.makeText(getActivity(), R.string.phone_error_message,
							Toast.LENGTH_SHORT).show();
					return;
				}
				else
				{
					comment = "Телефон: " + _txtPhone.getText().toString()
							+ "\n";
				}
			}
			comment += _txtMain.getText().toString();
			if (getActivity() instanceof CartActivity)
				((CartActivity) getActivity()).SetComment(comment);
			else if(getActivity() instanceof OrderActivity)
				((OrderActivity) getActivity()).SetComment(comment);
		}
		dismiss();
	}

}