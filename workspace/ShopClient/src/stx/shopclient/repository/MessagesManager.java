package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.entity.Message;

public class MessagesManager
{
	List<Message> _messages = new ArrayList<Message>();
	private long _unreadMessageCount = 0;

	public MessagesManager()
	{		
	}

	public Collection<Message> getMessages()
	{
		return _messages;
	}

	public Message getMessage(long id)
	{
		for (Message m : _messages)
		{
			if (m.getId() == id)
				return m;
		}

		return null;
	}

	public long getUnreadMessageCount()
	{
		return _unreadMessageCount;
	}

	public void setUnreadMessageCount(long unreadMessageCount)
	{
		_unreadMessageCount = unreadMessageCount;
	}
}
