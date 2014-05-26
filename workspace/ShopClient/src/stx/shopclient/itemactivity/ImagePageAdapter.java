package stx.shopclient.itemactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import stx.shopclient.R;
import stx.shopclient.repository.Repository;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePageAdapter extends PagerAdapter implements OnClickListener
{
	Context _Context;
	private List<String> _Items;

	public ImagePageAdapter(Context context, long itemId)
	{
		_Context = context;
		_Items = new ArrayList<String>();
		_Items.addAll(Repository.getIntent().getImagesManager()
				.getItemImages(itemId));

	}

	@Override
	public int getCount()
	{
		return _Items.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1)
	{
		return arg0.equals(arg1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		ImageView view = new ImageView(_Context);
		try
		{
			//File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "1.png");
			File filePath = new File(_Items.get(position));
			//String path = filePath.getAbsolutePath();
			if (filePath.exists())
			{
			//_Context.openFileInput(filePath.getAbsolutePath());
			//String filePath = _Items.get(position);
			//File file = new File(Environment.getExternalStorageDirectory(), filePath);
			Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
			view.setImageBitmap(bitmap);
			//view.setImageURI(Uri.fromFile(file));
			//view.setImageResource(R.drawable.ic_launcher);
			view.setOnClickListener(this);
			}
		}
		catch (Exception ex)
		{
			if (ex.getMessage() != null)
			{
				
			}
		}

		container.addView(view);

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view)
	{
		container.removeView((View) view);
	}

	@Override
	public void onClick(View v)
	{
		((ItemImageActivity) _Context).onImageClick(v);
	}
}