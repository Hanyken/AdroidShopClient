package stx.shopclient.discountactivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.Discount;

public class DiscountActivity extends BaseActivity {
	Discount _discount;

	@Override
	protected View createMainView(ViewGroup parent) {
		_discount = DiscountListActivity.SelectedDiscount;
		
		getActionBar().setTitle(_discount.getCatalogName());

		View view = getLayoutInflater().inflate(R.layout.discount_activity,
				parent, false);

		TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
		TextView descriptionTextView = (TextView) view
				.findViewById(R.id.descriptionTextView);
		TextView sizeTextView = (TextView) view.findViewById(R.id.sizeTextView);
		TextView numberTextView = (TextView) view
				.findViewById(R.id.cardNumberTextView);

		nameTextView.setText(_discount.getName());

		if (_discount.getDescription() == null)
			descriptionTextView.setVisibility(View.GONE);
		else
			descriptionTextView.setText(_discount.getDescription());

		numberTextView.setText(_discount.getCode());

		if (_discount.getUnitType() == Discount.UNIT_TYPE_PERCENT)
			sizeTextView.setText(String.format("%d", (int) _discount.getSize())
					+ "%");
		else if (_discount.getUnitType() == Discount.UNIT_TYPE_RUB)
			sizeTextView.setText(String.format("%.2f", _discount.getSize())
					+ " рублей");

		return view;
	}
}
