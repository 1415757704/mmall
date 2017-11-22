import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMQ {

	
	@Test
	public void sendMQMessage() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-active-mq.xml");
		System.out.println("applicationContext");
		JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean(JmsTemplate.class);
		System.out.println("jmsTemplate");
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		System.out.println("send before");
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				System.out.println("send in");
				return session.createTextMessage("send ActiveMQ Message");
			}
		});
		System.out.println("send finish");
	}
}
