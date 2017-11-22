import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aia.mapper.TbItemMapper;
import com.aia.pojo.TbItem;
import com.aia.pojo.TbItemExample;
import com.aia.portal.service.impl.ContentCatServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class PagerHelperTest {
	
	private ApplicationContext ac;
	@Before
	public void setUp() {
		ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
	}
	
	@Test
	public void TestU() {
		Object bean = ac.getBean("contentCatServiceImpl");
//		List contentCatList = con.getContentCatList();
		System.out.println("contentCatList");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRedis() {
		JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
		Jedis Jedis = jedisPool.getResource();
		Jedis.set("a","123");
		String string = Jedis.get("a");
		System.out.println(string);
	}
	
	@Test
	public void testI() {
		System.out.println("--->>");
	}
}
