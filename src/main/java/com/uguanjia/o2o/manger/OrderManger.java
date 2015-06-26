package com.uguanjia.o2o.manger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.dao.OrderDao;
import com.uguanjia.o2o.dao.OrderXiaobaituDao;
import com.uguanjia.o2o.meta.OrderProcessActivity;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.OrderStatistics;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.ServiceOrderCreator;
import com.uguanjia.o2o.service.ServiceOrderCreatorResult;
import com.uguanjia.o2o.service.ServiceOrderQuerier;
import com.uguanjia.o2o.service.ServiceOrderQueryResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.ServiceUtils;
import com.uguanjia.o2o.service.xiaobaitu.XbtService;
import com.uguanjia.o2o.service.xiaobaitu.XbtServiceItem;

/*******************************************
 * @CLASS:OrderManger
 * @DESCRIPTION:订单管理	
 * @VERSION:v1.0
 * @DATE:2015年1月26日 下午9:44:45
 *******************************************/
public class OrderManger implements ApplicationContextAware{
	
	private final Logger logger = LogManager.getLogger(getClass());
	
	private ApplicationContext context;
	
	private PlatformTransactionManager txManager;
	
	private SqlSessionFactory sqlSessionFactory;
	
	public void create(Order order, Store store, Contract contract, Operator operator){
		create(order, store, contract == null ? null : contract.getStrategy(), operator);
	}

	public void create(Order order, Store store, String strategy, Operator operator){
		
		logger.info("begin to create a new order");
		
		if( order == null ){
			logger.warn("order is null");
			logger.warn("failure to create a new order");
			return;
		}
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName("CreateNewOrder");
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		
		try(SqlSession session = sqlSessionFactory.openSession())
		{
		
			// 设置order状态为新建
			order.setStatus( OrderStatus.NEW );
		
			// 创建订单
			Service service = Service.getById(order.getType());
			ServiceOrderCreator serviceOrderCreator =
					ServiceUtils.getServiceInstance(service, ServiceOrderCreator.class, context);
		
			if( serviceOrderCreator instanceof ServiceSqlSessionAware )
			{
				((ServiceSqlSessionAware)serviceOrderCreator).setSession(session);
			}
			
			ServiceOrderCreatorResult result = 
					serviceOrderCreator.create(order, strategy);
		
			if( result == null ){
				logger.warn("result is null");
			} 
			else if( !result.isSuccess() ){
				logger.warn(result.getMessage());
			} 
			else{
				// 创建成功后更新订单状态
				OrderDao orderDao = session.getMapper(OrderDao.class);
			
				OrderProcessActivity process = new OrderProcessActivity();
				process.setId(order.getId());
				process.setOperator(operator.getUsername());
				process.setStatus(String.valueOf(order.getStatus()));
				orderDao.insertOrderProcess(process);
			}
		
		} catch(Exception e){
			logger.error(e);
			txManager.rollback(status);
			logger.info("failure to create a new order");
			return;
		}
		
		txManager.commit(status);
		
		logger.info("success to create a new order");
	}	
	
	/**
	 * @description:查询指定门店的所有订单（分页查询）
	 * 按照时间降序排列	
	 * @DATE:2015年1月27日 下午10:49:00
	 * @param storeId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Order> queryOrderPageByStore(QueryCondition condition){
		
		long storeId = condition.getStoreId();
		
		if( logger.isInfoEnabled() ){
			logger.info("begin to query orders for " + storeId 
					+ ", pageNo is " + condition.getPageNo() 
					+ ", pageSize is " + condition.getPageSize());
		}
		
		List<Order> orders = null;
		
		try(SqlSession session = sqlSessionFactory.openSession())
		{
			OrderDao orderDao = session.getMapper(OrderDao.class);
			
			// 首先查询总数
			int total = orderDao.queryOrderCountsByStore(condition);
			condition.setTotal(total);
			if( total > 0 )
			{
				orders = orderDao.queryOrdersByStore(condition);
			}
			else 
			{
				orders = new ArrayList<>();
			}
			
		}
		catch (Exception e)
		{
			logger.error(e);
		}

		if( logger.isInfoEnabled() ){
			logger.info("success to query orders for " + storeId + ", size is " + (orders == null ? 0 : orders.size()));
		}
		
		return orders;
	}	
	
	/**
	 * @description:查询全部订单	
	 * @DATE:2015年3月23日 下午3:42:23
	 * @param codition
	 * @return
	 */
	public List<Order> queryOrders(QueryCondition condition){
		
		List<Order> orders = null;

		try(SqlSession session = sqlSessionFactory.openSession() )
		{
			OrderDao orderDao = session.getMapper(OrderDao.class);
			// 首先查询总数
			int total = orderDao.queryOrderCount(condition);
			condition.setTotal(total);
			if( total > 0 )
			{
				orders = orderDao.queryOrders(condition);
			}
			else 
			{
				orders = new ArrayList<>();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
		
		return orders;
	}
	
	/**
	 * @description:根据订单号查询订单	
	 * @DATE:2015年1月28日 上午12:05:36
	 * @param id
	 * @return
	 */
	public Order queryOrderById(QueryCondition condition){
		
		logger.info("begin to query order of " + condition.getOrderId());
		
		Order order = null;
		
		try(SqlSession session = sqlSessionFactory.openSession() )
		{
			OrderDao orderDao = session.getMapper(OrderDao.class);
			
			order = orderDao.queryOrderById(condition);
			
			order.setProcesses(orderDao.queryOrderProcessesById(condition.getOrderId()));
			
			if( order != null )
			{
				Service service = Service.getById(order.getType());
				
				if( service != null )
				{
					ServiceOrderQuerier querier = 
							ServiceUtils.getServiceInstance(service, ServiceOrderQuerier.class, context);
					
					assert querier != null ;
					
					if( querier instanceof ServiceSqlSessionAware )
					{
						((ServiceSqlSessionAware)querier).setSession(session);
					}
					
					ServiceOrderQueryResult result = querier.query(order);
					
					if( result != null && result.isSuccess() )
					{
						order = result.getOrder();
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
		
		
		return order;
	}
	
	/**
	 * @description:根据订单号更新订单状态	
	 * @DATE:2015年1月28日 下午12:03:49
	 * @param process
	 */
	public void updateOrderStatusByProcess(OrderProcessActivity process){
		
		if( process == null ){
			logger.warn("begin to update status of order but process is null");
			return;
		}
		
		if( logger.isInfoEnabled() ){
			logger.info("begin to update status of order " + process.getId() + " to " + process.getStatus());
		}
		
		SqlSession session = sqlSessionFactory.openSession();
		OrderDao orderDao = session.getMapper(OrderDao.class);

		orderDao.updateOrderStatus(process.getId(), Integer.valueOf(process.getStatus()));
		orderDao.insertOrderProcess(process);
		
		if( logger.isInfoEnabled() ){
			logger.info("success to update status of order " + process.getId() + " to " + process.getStatus());
		}
		
		session.close();
	}
	
	/**
	 * @description:查询月度订单统计	
	 * @DATE:2015年4月21日 下午11:18:36
	 * @param condition
	 * @return
	 */
	public List<OrderStatistics> queryOrderStatistics(QueryCondition condition){
		
		List<OrderStatistics> result = null;
		
		try(SqlSession session = sqlSessionFactory.openSession() )
		{
			OrderDao orderDao = session.getMapper(OrderDao.class);
			
			int total = orderDao.queryOrderMonthStatisticsCount(condition);
			condition.setTotal(total);
			if( total > 0 )
			{
				result = orderDao.queryOrderMonthStatistics(condition);
			}
		}
		catch (Exception e) 
		{
			this.logger.error(e);
		}
		
		return result;
	}
	
	/**
	 * @description:查询小白兔服务	
	 * @DATE:2015年2月12日 上午7:14:54
	 * @return
	 */
	public XbtService queryXbtService(){
		
		logger.info("begin to query xiaobaitu service ");
		
		
		SqlSession session = sqlSessionFactory.openSession();
		
		OrderXiaobaituDao orderXbtDao = session.getMapper(OrderXiaobaituDao.class);
		
		List<XbtServiceItem> allItems = orderXbtDao.queryAllServiceItems();
		
		session.close();
		
		XbtService service = wrapXbtServiceItemList(allItems);
		
		logger.info("success to query xiaobaitu service ");
		return service;
	}
	
	private XbtService wrapXbtServiceItemList(List<XbtServiceItem> allItems){
		XbtService service = new XbtService();
		Map<Integer, XbtServiceItem> categories = service.getItems();
		
		if( allItems == null || allItems.size() == 0 ){
			logger.warn("xiaobaitu service items is empty");
		} else{
			for( XbtServiceItem item : allItems ){
				if( item.getCategory() == 0 ){ // 0是顶层系列
					categories.put(item.getId(), item);
				} else{
					int categoryId = item.getCategory();
					XbtServiceItem category = categories.get(categoryId);
					if( category == null ){
						logger.warn("there is no category for " + categoryId );
					} else{
						category.getItems().put(item.getId(), item);
					}
				}
			}
		}
		
		return service;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public PlatformTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(PlatformTransactionManager tx) {
		this.txManager = tx;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.context = applicationContext;
	}
}

