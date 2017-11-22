import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class E3SeachUtils {
	
	private ApplicationContext ac;
	@Before
	public void setUp() {
		ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
	}
	
	@Test
	public void TestU() {
		Object bean = ac.getBean("seachItemServiceImpl");
//		List contentCatList = con.getContentCatList();
		System.out.println(bean);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
