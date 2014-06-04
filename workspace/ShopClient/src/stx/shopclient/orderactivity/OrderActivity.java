package stx.shopclient.orderactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.entity.CatalogItem;
import stx.shopclient.entity.CatalogSettings;
import stx.shopclient.entity.KeyValue;
import stx.shopclient.entity.OrderProperty;
import stx.shopclient.entity.properties.PropertyDescriptor;
import stx.shopclient.itemactivity.ItemActivity;
import stx.shopclient.repository.Repository;
import stx.shopclient.styles.ColorButtonDrawable;
import stx.shopclient.ui.common.properties.PropertiesList;

public class OrderActivity extends BaseActivity implements OnClickListener
{
	private long ItemId;
	private TextView lblItemName;
	private ImageView imgIco;
	private PropertiesList plProperies;
	private LinearLayout llCountable;
	private Button btnOrder;
	List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.order_activity,
				parent, false);

		Intent intent = getIntent();
		ItemId = intent.getLongExtra(ItemActivity.ITEM_ID_EXTRA_KEY, 0);
		
		CatalogSettings settings = Repository.getIntent().getCatalogManager().getSettings();
		CatalogItem item = Repository.getIntent().getItemsManager().getItem(ItemId);
		
		lblItemName = (TextView) view.findViewById(R.id.lblItemName);
		plProperies = (PropertiesList) view.findViewById(R.id.plProperties);
		llCountable = (LinearLayout) view.findViewById(R.id.llCountable);
		btnOrder = (Button)view.findViewById(R.id.btnOrder);
		imgIco = (ImageView)view.findViewById(R.id.imgIco);
		
		llCountable.setVisibility(View.GONE);

		lblItemName.setText(item.getName());

		btnOrder.setOnClickListener(this);
		btnOrder.setBackground(getBueButtonDrawable(settings));
		
		plProperies.setAllowClear(false);
		
		imgIco.setImageBitmap(Repository.getIntent().getImagesManager().getImage(item.getIco()));
		
		properties.addAll(item.getOrderProperties());
		plProperies.setProperties(properties);
		
		return view;
	}

	@Override
	public void onClick(View v)
	{
		ArrayList<OrderProperty> items = new ArrayList<OrderProperty>();
		for(PropertyDescriptor el : properties)
		{
			OrderProperty item = new OrderProperty();
			item.setName(el.getName());
			item.setValue(el.getStringValue());
			items.add(item);
		}
		
		Repository.getIntent().getOrderManager().addOrderItem(ItemId, items);
		Toast.makeText(this, "Товар добавлен в карзину",Toast.LENGTH_SHORT).show();
		finish();
	}
	
	
	private Drawable getBueButtonDrawable(CatalogSettings settings)
	{
		StateListDrawable drawable = new StateListDrawable();
		Drawable normal = new ColorButtonDrawable(settings.getBackground());
		Drawable press = new ColorButtonDrawable(settings.getPressedColor());
		Drawable disable = new ColorButtonDrawable(settings.getDisableColor());

		drawable.addState(new int[] { android.R.attr.state_pressed }, press);
		drawable.addState(new int[] { -android.R.attr.state_enabled }, disable);
		drawable.addState(new int[0], normal);
		return drawable;
	}
}
