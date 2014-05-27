package stx.shopclient.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import stx.shopclient.entity.Message;

public class MessagesManager
{
	List<Message> _messages = new ArrayList<Message>();

	public MessagesManager()
	{
		for (int i = 0; i < 10; i++)
		{
			Message m = new Message();
			m.setTitle("Сообщение " + Integer.toString(i));
			m.setId(_messages.size() + 1);
			m.setText("<html><body>HTML message body</body></html>");
			if (i > 2)
				m.setRead(true);
			_messages.add(m);
		}
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
}
