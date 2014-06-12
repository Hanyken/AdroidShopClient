package stx.shopclient.loaders;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import stx.shopclient.entity.Token;
import stx.shopclient.parsers.BaseParser;

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
	
	public void addParam(String name, Token value)
	{
		_params.add(new BasicNameValuePair(name, value.getToken()));
	}
	
	public void addParam(String name, long value)
	{
		_params.add(new BasicNameValuePair(name, Long.toString(value)));
	}
	
	public void addParam(String name, int value)
	{
		_params.add(new BasicNameValuePair(name, Integer.toString(value)));
	}
	
	public void addParam(String name, boolean value)
	{
		_params.add(new BasicNameValuePair(name, Boolean.toString(value)));
	}
	
	public void addParam(String name, byte value)
	{
		_params.add(new BasicNameValuePair(name, Byte.toString(value)));
	}
	
	public void addParam(String name, double value)
	{
		_params.add(new BasicNameValuePair(name, Double.toString(value)));
	}
	
	public void addParam(String name, Date value)
	{
		_params.add(new BasicNameValuePair(name, BaseParser.dateParser.format(value)));
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
			String value = el.getValue();
			if (value == null || value.equals(null))
				value = "";
			sb.append(el.getName()+"="+value+"&");
		}
		
		String value = sb.toString();
		if (value.endsWith("&"))
			value = value.substring(0, value.length()-2);
		
		return new HttpGet(sb.toString());
	}
}
