package stx.shopclient.itemactivity;

import java.util.Random;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemActivity extends BaseActivity
{
	private TextView txtTitle;
	
	public static final String ITEM_ID_EXTRA_KEY = "ItemID";

	LinearLayout myGallery;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_activity);

		Intent intent = getIntent();

		ItemButtonBarFragment buttonBar = (ItemButtonBarFragment) getFragmentManager()
				.findFragmentById(R.id.frgButtonBar);
		txtTitle = (TextView) findViewById(R.id.textViewTitle);
		TextView txtProperty = (TextView) findViewById(R.id.txtProperty);

		Random rand = new Random();
		buttonBar.setPrice(rand.nextFloat() * 30000);
		buttonBar.setRating(rand.nextFloat() * 5);
		buttonBar.setCommentCount(rand.nextInt(256));
		buttonBar.setRepostCount(rand.nextInt(512));

		// Получаю параметры
		String itemTitle = intent.getStringExtra("ItemTitle");
		String ItemID = intent.getStringExtra(ITEM_ID_EXTRA_KEY);

		txtTitle.setText(itemTitle);

		setProperty(txtProperty);
	}

	public void setProperty(TextView txtProperty)
	{
		String description  = "Это самый лучший тавар на планете\n\n\n";
		String text = "\t-Клевость - самый клевый\n\t-Привлекательность - привлекательнее товара не существует\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nНу и тут какие то еще вкусности про товар";
		
		
		SpannableStringBuilder str = new SpannableStringBuilder("Общее\n"+description+"Характеристики\n"+text);
		str.setSpan(
		    new StyleSpan(Typeface.BOLD), 
		    0, 
		    5, 
		    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
		);
		str.setSpan(
			    new StyleSpan(Typeface.BOLD), 
			    description.length()+5, 
			    description.length()+20, 
			    SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
			);
		
		
		//Spanned str = Html.fromHtml("<font color=\"red\">Привет.</font> <font color=\"yellow\">как </font> <font color=\"blue\">дела?</font>");
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
		case R.id.btnComment:
			
			break;

		case R.id.btnShare:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, txtTitle.getText());
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			break;

		default:
			break;
		}
	}
}
