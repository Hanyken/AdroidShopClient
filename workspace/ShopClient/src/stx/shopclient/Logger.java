package stx.shopclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class Logger
{
	private static String DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/ShopClient/";
	private static String FILE_NAME = "log.txt";
	
	static {
		File file = new File(DATA_PATH);
		file.mkdir();
	}
	
	public static void write(String title, String message){
		/*try{
            FileOutputStream stream = new FileOutputStream(new File (DATA_PATH, FILE_NAME), true);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write("\n----------"+title+"----------");
            writer.write(message);
            writer.close();
            stream.close();
        } catch(Throwable ex){}*/
	}
}
