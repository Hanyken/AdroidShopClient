package stx.shopclient.entity;

public class AppSettings
{
	private String _serverVersion;
	private String _firstServerUri;
	private String _lastServerUri;
	private long _defaultCatalog;

	public String getServerVersion()
	{
		return _serverVersion;
	}

	public void setServerVersion(String serverVersion)
	{
		_serverVersion = serverVersion;
	}

	public String getFirstServerUri()
	{
		return _firstServerUri;
	}

	public void setFirstServerUri(String firstServerUri)
	{
		_firstServerUri = firstServerUri;
	}

	public String getLastServerUri()
	{
		return _lastServerUri;
	}

	public void setLastServerUri(String lastServerUri)
	{
		_lastServerUri = lastServerUri;
	}

	public long getDefaultCatalog()
	{
		return _defaultCatalog;
	}

	public void setDefaultCatalog(long defaultCatalog)
	{
		_defaultCatalog = defaultCatalog;
	}
}
