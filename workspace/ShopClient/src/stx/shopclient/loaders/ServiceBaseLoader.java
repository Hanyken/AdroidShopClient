package stx.shopclient.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class ServiceBaseLoader<T> extends AsyncTask<HttpArgs, Void, Collection<T>>
{
	private String _url;
	private boolean _isGetType;
	
	public ServiceBaseLoader(String url, boolean isGetType)
	{
		_url = url.trim();
		_isGetType = isGetType;
		
		while(_url.endsWith("/") ||_url.endsWith("?"))
			_url = _url.substring(0, _url.length() - 2);
	}
	
	@Override
	protected Collection<T> doInBackground(HttpArgs... arg0)
	{
		Collection<T> result = null;
		HttpArgs args = arg0[0];
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = null;
			if (_isGetType)
				response = httpclient.execute(args.getGet(_url));
			else
				response = httpclient.execute(args.getPost(_url));
			
			String str = convertStreamToString(response.getEntity().getContent());
			
			result = onParse(str);
		}
		catch(Exception ex)
		{
			
		}
		return result;
	}

	protected Collection<T> onParse(String value) { return null; }
	
	
	private static String convertStreamToString(InputStream is) 
	{

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try 
	    {
	        while ((line = reader.readLine()) != null) 
	        {
	            sb.append((line + "\n"));
	        }
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    } 
	    finally 
	    {
	        try 
	        {
	            is.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}
