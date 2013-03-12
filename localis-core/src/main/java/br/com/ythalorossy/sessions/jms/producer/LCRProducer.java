package br.com.ythalorossy.sessions.jms.producer;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

public class LCRProducer implements LCRRequestProducer {
	
	@Resource(lookup="jms/lcrconnectionfactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(lookup = "jms/lcr")
	private Queue queue;
	
	public LCRProducer() {
	}

	public void execute(String url) {
		
		Connection connection = null;
		
		try {
			
			connection = connectionFactory.createConnection();
		
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer producer = session.createProducer(queue);

			ObjectMessage message = session.createObjectMessage(new String(url));
		
			producer.send(message);		
			
		} catch (JMSException e) {

			e.printStackTrace();
			
		} finally {
			
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
