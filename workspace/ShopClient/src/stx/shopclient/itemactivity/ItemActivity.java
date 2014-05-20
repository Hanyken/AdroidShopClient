package stx.shopclient.itemactivity;

import java.util.Random;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.overviewactivity.OverviewActivity;
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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemActivity extends BaseActivity
{
	private TextView txtTitle;
	private String ItemID;

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";

	LinearLayout myGallery;

	@Override
	protected View createMainView(ViewGroup parent)
	{
		View view = getLayoutInflater().inflate(R.layout.item_activity, parent, false);
	
		Intent intent = getIntent();

		ItemButtonBarFragment buttonBar = (ItemButtonBarFragment) getFragmentManager().findFragmentById(R.id.frgButtonBar);
		txtTitle = (TextView)view.findViewById(R.id.textViewTitle);
		TextView txtProperty = (TextView)view.findViewById(R.id.txtProperty);

		Random rand = new Random();
		buttonBar.setPrice(rand.nextFloat() * 30000);
		buttonBar.setRating(rand.nextFloat() * 5);
		buttonBar.setOverviewCount(rand.nextInt(256));
		buttonBar.setRepostCount(rand.nextInt(512));

		// ������� ���������
		String itemTitle = intent.getStringExtra("ItemTitle");
		ItemID = intent.getStringExtra(ITEM_ID_EXTRA_KEY);

		txtTitle.setText(itemTitle);

		setProperty(txtProperty);
		
		return view;
	}

	public void setProperty(TextView txtProperty)
	{
		String description = "��� ����� ������ ����� �� �������\n\n\n";
		String text = "\t-�������� - ����� ������\n\t-����������������� - ��������������� ������ �� ����������\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n�� � ��� ����� �� ��� ��������� ��� �����";

		SpannableStringBuilder str = new SpannableStringBuilder("�����\n"
				+ description + "��������������\n" + text);
		str.setSpan(new StyleSpan(Typeface.BOLD), 0, 5,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
		str.setSpan(new StyleSpan(Typeface.BOLD), description.length() + 5,
				description.length() + 20,
				SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

		// Spanned str =
		// Html.fromHtml("<font color=\"red\">������.</font> <font color=\"yellow\">��� </font> <font color=\"blue\">����?</font>");
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
			sendIntent.putExtra(Intent.EXTRA_TEXT, txtTitle.getText());
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			break;

		default:
			break;
		}
	}
}
