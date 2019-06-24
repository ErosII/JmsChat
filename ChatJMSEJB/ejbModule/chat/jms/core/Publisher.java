package chat.jms.core;

import javax.naming.InitialContext;

import com.sun.xml.internal.ws.encoding.xml.XMLMessage.MessageDataSource;

import javax.jms.Topic;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TopicSession;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

@Stateless
@LocalBean      
@Remote({PublisherRemote.class})
public class Publisher implements PublisherRemote {
	
	TopicConnection connection;
	TopicSession session;
	MessageProducer producer;
	
	@Resource(lookup = "java:/ConnectionFactory")
	ConnectionFactory connectionFactory;

	@Resource(lookup = "java:jboss/exported/jms/topic/SimpleChatTopic")
	Destination topic;
	
	@Override
    public void publish(ChatMessage chatMessage) throws Exception{
		
		try {
			ObjectMessage objectMessage = session.createObjectMessage();
			
			objectMessage.setObject(chatMessage);
			producer.send(objectMessage);

			// print what we did
			System.out.println(chatMessage.user + " published: " + chatMessage.msg);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
	@Override
	public void connect() throws JMSException {
		connection = (TopicConnection) connectionFactory.createConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(topic);
	}
	
	@Override
	public void disconnect() throws JMSException {
		producer.close();
		session.close();
		connection.close();
	}
  }
