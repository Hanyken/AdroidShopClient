package stx.shopclient.itemactivity;

import java.io.File;

import stx.shopclient.BaseActivity;
import stx.shopclient.R;
import stx.shopclient.R.id;
import stx.shopclient.R.layout;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends BaseActivity {

	public static final String ITEM_ID_EXTRA_KEY = "ItemID";
	
	LinearLayout myGallery;
	@Override
	public View createMainView(ViewGroup parent) {
		
		 Intent intent = getIntent();
		 
		 //������� ���������
		 String itemTitle = intent.getStringExtra("ItemTitle");
		 String ItemID = intent.getStringExtra(ITEM_ID_EXTRA_KEY);
		
		View view = getLayoutInflater().inflate(R.layout.item_activity, parent, false);
		
		TextView textView = (TextView)view.findViewById(R.id.textViewTitle);
		 textView.setText(itemTitle);
		return view;
	}
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.screen_item);
        
        myGallery = (LinearLayout)findViewById(R.id.imageGallery);
        
        String ExternalStorageDirectoryPath = Environment
          .getExternalStorageDirectory()
          .getAbsolutePath();
        
        String targetPath = ExternalStorageDirectoryPath + "/DCIM/Camera";
        
        Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
        File targetDirector = new File(targetPath);
           
        if(targetDirector.exists())
        {
        	File[] files = targetDirector.listFiles();
        		for (File file : files)
        		{
        			myGallery.addView(insertPhoto(file.getAbsolutePath()));
        		}
        }    
    }

    View insertPhoto(String path){
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
    
    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
     Bitmap bm = null;
     
     // First decode with inJustDecodeBounds=true to check dimensions
     final BitmapFactory.Options options = new BitmapFactory.Options();
     options.inJustDecodeBounds = true;
     BitmapFactory.decodeFile(path, options);
     
     // Calculate inSampleSize
     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
     
     // Decode bitmap with inSampleSize set
     options.inJustDecodeBounds = false;
     bm = BitmapFactory.decodeFile(path, options); 
     
     return bm;  
    }
    
    public int calculateInSampleSize(
      
     BitmapFactory.Options options, int reqWidth, int reqHeight) {
     // Raw height and width of image
     final int height = options.outHeight;
     final int width = options.outWidth;
     int inSampleSize = 1;
        
     if (height > reqHeight || width > reqWidth) {
      if (width > height) {
       inSampleSize = Math.round((float)height / (float)reqHeight);   
      } else {
       inSampleSize = Math.round((float)width / (float)reqWidth);   
      }   
     }
     
     return inSampleSize;   
    }

}
