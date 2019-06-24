package application;

import javax.jms.*;
import javax.naming.*;

import chat.jms.core.ChatMessage;
import chat.jms.core.PublisherRemote;
import windows.MainPageController;

import java.util.Hashtable;
import java.util.UUID;

public class JmsChat implements MessageListener{
	
	final PublisherRemote publisher;
	
	MainPageController callBack;
	
    public static final String TOPIC = "/jms/topic/SimpleChatTopic";
    
	final static Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
	static {
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "user");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "password");
	}
	
	private static PublisherRemote lookupRemoteStatelessPublisher() throws NamingException {
		final Context context = new InitialContext(jndiProperties);
		return (PublisherRemote) context.lookup("ejb:ChatJMSEAR/ChatJMSEJB/Publisher!chat.jms.core.PublisherRemote");
	}
		
	public JmsChat (MainPageController callBack) throws NamingException, JMSException {
		
		this.callBack = callBack;
		publisher = lookupRemoteStatelessPublisher();
		publisher.connect();
		
		Context context = new InitialContext(jndiProperties);
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		TopicConnection topicConnection = (TopicConnection) topicConnectionFactory.createTopicConnection("user", "password");
		Topic topic = (Topic) context.lookup(JmsChat.TOPIC);
		subscribe(topicConnection, topic, this);
		
	}
	
	public void publish (UUID userId, String user, String message) throws Exception {
		
		ChatMessage chatMessage = new ChatMessage(userId, user, message);
		publisher.publish(chatMessage);
	
	}
	
	public void disconnect() throws JMSException {
		publisher.disconnect();
	}
	
	public void subscribe(TopicConnection topicConnection, Topic topic, JmsChat jmsChat) throws JMSException {
		TopicSession subscriberSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSubscriber topicSubscriber = subscriberSession.createSubscriber(topic);
		topicConnection.start();
		topicSubscriber.setMessageListener(jmsChat);
	}
	
	@Override
	public void onMessage(Message msg) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) msg;
			ChatMessage chatMessage = (ChatMessage) objectMessage.getObject();
			callBack.consumeMessage(chatMessage);

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
