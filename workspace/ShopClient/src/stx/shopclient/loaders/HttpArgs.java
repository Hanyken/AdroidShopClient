package stx.shopclient.loaders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class HttpArgs
{
	
	private List<BasicNameValuePair> _params;
	
	public HttpArgs()
	{
		_params = new ArrayList<BasicNameValuePair>();
	}

	public void addParam(String name, String value)
	{
		_params.add(new BasicNameValuePair(name, value));
	}
	
	public HttpPost getPost(String url) throws UnsupportedEncodingException
	{
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(_params));
		return post;
	}
	public HttpGet getGet(String url)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(url+"?");
		for(BasicNameValuePair el : _params)
		{
			sb.append(el.getName()+"="+el.getValue()+"&");
		}
		
		String value = sb.toString();
		if (value.endsWith("&"))
			value = value.substring(0, value.length()-2);
		
		return new HttpGet(sb.toString());
	}
}
