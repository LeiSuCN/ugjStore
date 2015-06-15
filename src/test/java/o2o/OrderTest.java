package o2o;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.OrderProcessActivity;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.OrderStatistics;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.cainiao.CainiaoOrder;
import com.uguanjia.o2o.service.cainiao.CainiaoOrderManger;
import com.uguanjia.o2o.service.xiaobaitu.XbtService;
import com.uguanjia.o2o.service.xiaobaitu.XbtServiceItem;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrder;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrderItem;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrder;

/*******************************************
 * @CLASS:OrderTest
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年1月26日 下午9:16:28
 *******************************************/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/application.xml"})

public class OrderTest {
	
	@Inject
	private OrderManger orderManger;
	
	@Inject
	private CainiaoOrderManger cainiaoOrderManger;
	
	@Test
	/**
	 * @description:创建一个小白兔订单	
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void create_a_new_order_xbt() {
		
		
		// 创建一个小白兔订单
		XiaobaituOrder order = new XiaobaituOrder();
		order.setStore( 4403010005L);
		order.setPack("袋装");
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
		
		Operator operator = new Operator();
		operator.setUsername("user1");
		
		orderManger.create(order, new Store(),null, operator);
		
		assertTrue(order.getId() > 0);

		assertThat(order.getStatus(), is(OrderStatus.NEW));
		System.out.println(order.getId());
	}
	
	@Test
	/**
	 * @description:创建一个照片冲洗的订单	
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void create_a_new_order_zhaopianchongxi() {
	}
	
	@Test
	/**
	 * @description:查询门店的所有订单
	 * 1）按照时间降序排序
	 * 2）带有分页条件	
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void query_all_orders_by_page_time() {
		
		long storeId = 4403010005L;
		
//		Operator operator = new Operator();
//		operator.setUsername("user1");

		
		// 创建测试小白兔订单
//		XbtOrder order = new XbtOrder();
//		order.setStore( storeId);
//		order.setCustomerName("A");
//		order.setPack("袋装1");
//		orderManger.create(order, new Store(), operator);
//		order.setCustomerName("B");
//		order.setPack("袋装2");
//		orderManger.create(order,  new Store(), operator);
//		order.setCustomerName("C");
//		order.setPack("袋装3");
//		orderManger.create(order,  new Store(), operator);
//		order.setStore( 10011);
//		order.setCustomerName("D");
//		order.setPack("袋装4");
//		orderManger.create(order,  new Store(), operator);
//		order.setStore( 10010);
//		order.setCustomerName("E");
//		order.setPack("袋装5");
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//		}
//		orderManger.create(order,  new Store(), operator);
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(storeId);
		condition.setStatus(2);
		condition.setPageNo(1);
		condition.setPageSize(10);
		Calendar calendar = Calendar.getInstance();
		condition.setDateTo(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		condition.setDateFrom(calendar.getTime());
		
		
		List<Order> orders =  orderManger.queryOrderPageByStore(condition);
		
		assertNotNull( orders );
		System.out.println(condition.getTotal());
		assertTrue (condition.getTotal() > 0 );
		assertTrue (orders.size() >= 3 );
		System.out.println(orders.size());
	}
	
	@Test
	/**
	 * @description:根据订单号查询订单
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void query_order_by_id_xbt() {
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(4403010005150325011L);
		Order result = orderManger.queryOrderById(condition);
		assertNotNull(result);
		assertTrue(result instanceof XiaobaituOrder);
		assertTrue( result.getStore() == 4403010005L);
		assertNotNull( ((XiaobaituOrder)result).getItems() );
		assertThat(((XiaobaituOrder)result).getItems().size(), is(2));

	}
	
	
	@Test
	/**
	 * @description:根据订单号查询订单
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void query_order_by_id_zhaopianchongxi() {
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(4403010005150328001L);
		Order result = orderManger.queryOrderById(condition);
		assertNotNull(result);
		assertTrue(result instanceof ZhaopianchongxiOrder);
		assertTrue( result.getStore() == 4403010005L);
		assertNotNull( ((ZhaopianchongxiOrder)result).getItems() );
		assertThat(((ZhaopianchongxiOrder)result).getItems().size(), is(2));
		
	}

	
	@Test
	/**
	 * @description:根据订单号查询订单
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void query_order_by_condition() {
		
		QueryCondition condition = new QueryCondition();
		
		condition.setPageNo(1);
		
		condition.setPageSize(10);
		
		Calendar calendar = Calendar.getInstance();
		condition.setDateTo(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);
		condition.setDateFrom(calendar.getTime());

		
		List<Order> orders = orderManger.queryOrders(condition);
		
		assertNotNull(orders);
		
		assertThat(orders.size(), is(10));
		
		assertTrue(condition.getTotal() > 0 );
	}
	
	/**
	 * @description:菜鸟订单统计	
	 * @DATE:2015年4月21日 下午10:27:02
	 */
	@Test
	public void query_order_statistics_cainiao(){
		
		QueryCondition condition = new QueryCondition();
		
		//condition.setStoreId(9);
		condition.setTime(201503);
		condition.setType(Service.CAINIAO.ID);
		condition.setPageNo(1);
		condition.setPageSize(5);
		
		List<OrderStatistics> statistics = orderManger.queryOrderStatistics(condition);
		
		assertNotNull(statistics);
		assertTrue(statistics.size() > 0 );
		
		System.out.println(condition.getTotal());
		
		for( OrderStatistics s : statistics )
		{
			System.out.println(s.getStoreId() + ": " + s.getAmount());
		}
	}
	
	/**
	 * @description:菜鸟订单统计	
	 * @DATE:2015年4月21日 下午10:27:02
	 */
	@Test
	public void query_order_statistics_detail_cainiao(){
		
		QueryCondition condition = new QueryCondition();
		
		condition.setStoreId(9);
		condition.setTime(201503);
		
		List<CainiaoOrder> list = cainiaoOrderManger.queryOrders(condition);
		
		assertNotNull(list);
		assertTrue(list.size() > 0 );
		
		for( CainiaoOrder order : list )
		{
			System.out.println(order.getStoreName() + ": " + order.getOrderCreateTime());
		}
	}
	
	@Test
	/**
	 * @description:根据订单号更新订单状态
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void update_order_status_by_id() {
		// 创建一个小白兔订单
		XiaobaituOrder order = new XiaobaituOrder();
		order.setStore( 10011);
		order.setPack("袋装");
		List<XiaobaituOrderItem> items = new ArrayList<>();
		order.setItems( items );
		
		XiaobaituOrderItem item1 = new XiaobaituOrderItem();
		item1.setName("西装/外套");
		item1.setAmount(2);
		item1.setUnitPrice(20);
		items.add( item1 );
		XiaobaituOrderItem item2 = new XiaobaituOrderItem();
		item2.setName("皮鞋");
		item2.setAmount(1);
		item2.setUnitPrice(10);
		items.add( item2 );
		
		Operator operator = new Operator();
		operator.setUsername("user1");

		
		orderManger.create(order, new Store(),null, operator);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		OrderProcessActivity process = new OrderProcessActivity();
		
		process.setId( order.getId() );
		process.setOperator( "张三" );
		process.setStatus(String.valueOf( OrderStatus.ACCEPT ));
		process.setComment( "可以接受该单" );
		
		orderManger.updateOrderStatusByProcess(process);
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(order.getId());
		Order result = orderManger.queryOrderById(condition);
		
		assertThat( result.getStatus(), is(OrderStatus.ACCEPT) );
		
		assertNotNull( result.getProcesses() );
		
		process =  result.getProcesses().get(0);
		
		assertNotNull( process.getStatus(), is("接受") );
		assertThat( process.getComment(), is("可以接受该单") );
		assertThat( process.getOperator(), is("张三") );
		assertThat( process.getId(), is(result.getId()) );
		assertNotNull(process.getTime());
		System.out.println(process.getTime());
	}
	
	@Test
	/**
	 * @description:查询全部小白兔价格表
	 * @DATE:2015年1月26日 下午9:35:33
	 */
	public void query_xbt_prices() {
		
		XbtService service = orderManger.queryXbtService();
		
		assertNotNull( service );
		
		Map<Integer, XbtServiceItem> categories = service.getItems();
		
		assertNotNull( categories );
		
		assertTrue(categories.size() > 0 );
		
		for( XbtServiceItem category : categories.values() ){
			
			System.out.println("category : " + category.getName());
			
			for( XbtServiceItem item : category.getItems().values() ){
				System.out.println("item : " + item.getName());
			}
		}
	}

}

