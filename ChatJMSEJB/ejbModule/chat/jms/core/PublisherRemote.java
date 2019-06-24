package chat.jms.core;

import javax.jms.JMSException;

public interface PublisherRemote {
	public void publish(ChatMessage chatMessage) throws Exception;
	public void connect() throws JMSException;
	public void disconnect() throws JMSException;
}
