import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aia.portal.redis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisTest {
	
	private ApplicationContext ac;
	
	@Before
	public void setUp() {
		ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
	}
	
	
	@Test
	public void testRedis() {
		JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
		Jedis Jedis = jedisPool.getResource();
		Jedis.set("a","123");
		String string = Jedis.get("a");
		System.out.println(string);
		Jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void testRedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.128", 7001));
		nodes.add(new HostAndPort("192.168.25.128", 7002));
		nodes.add(new HostAndPort("192.168.25.128", 7003));
		nodes.add(new HostAndPort("192.168.25.128", 7004));
		nodes.add(new HostAndPort("192.168.25.128", 7005));
		nodes.add(new HostAndPort("192.168.25.128", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("status", "redisCluster");
		String value = jedisCluster.get("status");
		System.err.println(value);
	}
	
	/**
	 * 测试redis集群接口
	 */
	@Test
	public void testRedisClusterInterface() {
		JedisClient jedisClient =  ac.getBean(JedisClient.class);
		jedisClient.set("interface", "redisCluster");
		System.out.println(jedisClient.get("interface"));
	}
}
