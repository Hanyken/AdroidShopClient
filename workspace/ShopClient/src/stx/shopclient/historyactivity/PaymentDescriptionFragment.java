package stx.shopclient.historyactivity;

import stx.shopclient.R;
import stx.shopclient.cartactivity.CartActivity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PaymentDescriptionFragment extends DialogFragment implements android.view.View.OnClickListener
{
	EditText _txtMain;
	
	
	public static PaymentDescriptionFragment get()
	{
		PaymentDescriptionFragment value = new PaymentDescriptionFragment();
		return value;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{		
		View v = inflater.inflate(stx.shopclient.R.layout.pyment_description_fragment, container, false);
		_txtMain = (EditText)v.findViewById(stx.shopclient.R.id.txtComment);
		
		((Button)v.findViewById(R.id.btnOk)).setOnClickListener(this);
		((Button)v.findViewById(R.id.btnCancel)).setOnClickListener(this);
		
		return v;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dlg = super.onCreateDialog(savedInstanceState);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setTitle(R.string.comment);
		this.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
		
		//final Drawable d = new ColorDrawable(Color.BLACK);
        //d.setAlpha(0);
        //dlg.getWindow().setBackgroundDrawable(d);
		
		return dlg;
	}
	
	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.btnOk)
		{
			((CartActivity)getActivity()).SetComment(_txtMain.getText().toString());
		}
		dismiss();
	}
	
}