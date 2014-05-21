package stx.shopclient.itemactivity;

import java.util.Random;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.orderactivity.OrderActivity;
import stx.shopclient.overviewactivity.OverviewActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemActivity extends BaseActivity
{
	private TextView lblItemName;
	private String ItemID;

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";

	LinearLayout myGallery;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.item_activity, parent,
				false);

		Intent intent = getIntent();

		// Получаю параметры
		String itemTitle = intent.getStringExtra("ItemTitle");
		ItemID = intent.getStringExtra(ITEM_ID_EXTRA_KEY);

		ItemImageFragment imageFragment = (ItemImageFragment) getFragmentManager()
				.findFragmentById(R.id.frgImages);
		ItemButtonBarFragment buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		lblItemName = (TextView) view.findViewById(R.id.lblItemName);
		TextView txtProperty = (TextView) view.findViewById(R.id.txtProperty);

		imageFragment.setImages(ItemID);

		Random rand = new Random();
		buttonBar.setPrice(rand.nextFloat() * 30000);
		buttonBar.setRating(rand.nextFloat() * 5);
		buttonBar.setOverviewCount(rand.nextInt(256));
		buttonBar.setRepostCount(rand.nextInt(512));

		lblItemName.setText(itemTitle);

		setProperty(txtProperty);

		return view;
	}

	public void setProperty(TextView txtProperty)
	{
		String description = "Это самый лучший тавар на планете\n\n\n";
		String text = "\t-Клевость - самый клевый\n\t-Привлекательность - привлекательнее товара не существует\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nНу и тут какие то еще вкусности про товар";

		SpannableStringBuilder str = new SpannableStringBuilder("Общее\n"
				+ description + "Характеристики\n" + text);
		str.setSpan(new StyleSpan(Typeface.BOLD), 0, 5,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		str.setSpan(new StyleSpan(Typeface.BOLD), description.length() + 5,
				description.length() + 20,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

		// Spanned str =
		// Html.fromHtml("<font color=\"red\">Привет.</font> <font color=\"yellow\">как </font> <font color=\"blue\">дела?</font>");
		txtProperty.setText(str);
	}

	View insertPhoto(String path)
	{
		Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);

		layout.addView(imageView);
		return layout;
	}

	public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
			int reqHeight)
	{
		Bitmap bm = null;

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeFile(path, options);

		return bm;
	}

	public int calculateInSampleSize(

	BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{
			if (width > height)
			{
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
			else
			{
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	public void onBarButtonClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btnOverview:
			Intent intent = new Intent(this, OverviewActivity.class);
			intent.putExtra(ITEM_ID_EXTRA_KEY, ItemID);
			startActivity(intent);
			break;

		case R.id.btnShare:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.setType("text/plain");
			sendIntent.putExtra(Intent.EXTRA_TEXT, lblItemName.getText());
			// TODO сделать передачу картинки
			// sendIntent.setType("image/jpeg");
			// sendIntent.putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("file://"+file.getAbsolutePath()));
			startActivity(sendIntent);

			break;

		case R.id.btnOrder:
			Intent orderIntent = new Intent(this, OrderActivity.class);
			orderIntent.putExtra(ITEM_ID_EXTRA_KEY, ItemID);
			orderIntent.putExtra("ItemTitle", lblItemName.getText());
			startActivity(orderIntent);
			break;
		}
	}
}
