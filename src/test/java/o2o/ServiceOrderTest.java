package o2o;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uguanjia.o2o.service.ServiceOrderCreator;
import com.uguanjia.o2o.service.ServiceOrderCreatorResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrder;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrderItem;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituServiceOrderCreator;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrder;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrderItem;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiServiceOrderCreator;

/*******************************************
 * @CLASS:OrderTest
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015��1��26�� ����9:16:28
 *******************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application.xml"})

public class ServiceOrderTest {
	
	@Inject
	@Named("sqlSessionFactoryBean")
	private SqlSessionFactory sqlSessionFactory;
	
	private SqlSession session;
	
	@Before
	public void openSession(){
		this.session = this.sqlSessionFactory.openSession();
	}
	
	@After
	public void closeSession(){
		this.session.close();
	}
	
	@Test
	public void test_serviceorder_create_xbt(){
		
		// 创建订单
		XiaobaituOrder order = new XiaobaituOrder();
		order.setStore(4403010005L);
		order.setPack("袋装 ");
		order.setPayment(1);
		order.setVoucher("1234567890");
		List<XiaobaituOrderItem> items = new ArrayList<>();
		order.setItems( items );
		
		XiaobaituOrderItem item1 = new XiaobaituOrderItem();
		item1.setName("1001");
		item1.setAmount(2);
		item1.setUnitPrice(2);
		items.add( item1 );
		XiaobaituOrderItem item2 = new XiaobaituOrderItem();
		item2.setName("2001");
		item2.setAmount(1);
		item2.setUnitPrice(10);
		items.add( item2 );		
		ServiceOrderCreator orderCreator = new XiaobaituServiceOrderCreator();
		
		((ServiceSqlSessionAware)orderCreator).setSession(this.session);
		
		ServiceOrderCreatorResult result = orderCreator.create(order,"1:0.8;2:0.8;3:0.8");
		
		System.out.println(result.getMessage());
		
		assertTrue( result.isSuccess() );
	}
	
	@Test
	public void test_serviceorder_create_zhaopianchongxi(){
		
		ZhaopianchongxiOrder order = new ZhaopianchongxiOrder();
		order.setStore(4403010005L);
		List<ZhaopianchongxiOrderItem> items = new ArrayList<>();
		order.setItems( items );
		
		ZhaopianchongxiOrderItem item1 = new ZhaopianchongxiOrderItem();
		item1.setName("1001.jpg");
		item1.setSize("4R 6寸");
		item1.setAmount(2);
		item1.setPrice(0.8f);
		items.add( item1 );
		ZhaopianchongxiOrderItem item2 = new ZhaopianchongxiOrderItem();
		item2.setName("2001.jpg");
		item2.setSize("3R 5寸");
		item2.setAmount(1);
		item2.setPrice(1);
		items.add( item2 );		
		ServiceOrderCreator orderCreator = new ZhaopianchongxiServiceOrderCreator();
		
		((ServiceSqlSessionAware)orderCreator).setSession(this.session);
		
		ServiceOrderCreatorResult result = orderCreator.create(order,null);
		
		System.out.println(result.getMessage());
		
		assertTrue( result.isSuccess() );
	}
}

