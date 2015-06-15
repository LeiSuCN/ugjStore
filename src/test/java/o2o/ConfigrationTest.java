package o2o;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uguanjia.o2o.config.PropsConfigration;

/*******************************************
 * @CLASS:ConfigrationTest
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月12日 下午9:45:26
 *******************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application.xml"})
public class ConfigrationTest {
	
	@Inject
	@Named("ugj_cfg")
	PropsConfigration  pc;
	
	@Test
	/**
	 * @description:测试	
	 * @DATE:2015年2月12日 下午9:52:18
	 */
	public void test_props_configration(){
		
		assertEquals("ugj.cfg", pc.getName());
		assertNotNull(pc.getValue("contract.xbt.html"));
		
		System.out.println(pc.getValue("contract.xbt.html"));
	}

}

