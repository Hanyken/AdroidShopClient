package stx.shopclient.loaders;

import java.util.Collection;


public interface OnLoadChange<T>
{
	public void onChange(Collection<T> args);
}
