import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Connection;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.aia.mapper.TbItemMapper;
import com.aia.pojo.TbItem;
import com.aia.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PagerHelperTest {

	public void testPageHelp() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
		PageHelper.startPage(1, 10);
		TbItemExample example = new TbItemExample();
		List<TbItem> selectByExample = mapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(selectByExample);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(selectByExample.size());
	}
	
	private ApplicationContext ac;
	@Before
	public void setUp() {
		ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
	}
	
	public void TestU() {
//		ContentCatServiceImpl con = ac.getBean("contentCatServiceImpl");
	}
	
	@Test
	public void sendMQMessage() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-active-mq.xml");
		System.out.println("applicationContext");
		JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean(JmsTemplate.class);
		System.out.println("jmsTemplate");
		Destination destination = (Destination) applicationContext.getBean("topicDestination");
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
