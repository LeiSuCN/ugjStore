package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.OrderProcessActivity;
import com.uguanjia.o2o.service.OrderStatistics;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public interface OrderDao {

	public int insertOrder(Order order);
	
	public int insertOrderProcess( OrderProcessActivity process);
	
	public int queryOrderCountsByStore(QueryCondition condition);
	
	public List<Order> queryOrdersByStore(QueryCondition condition);
	
	public int queryOrderCount(QueryCondition condition);
	
	public List<Order> queryOrders(QueryCondition condition);
	
	public Order queryOrderById(QueryCondition condition);
	
	public List<OrderProcessActivity> queryOrderProcessesById(long id);
	
	public int queryTodayOrderCountById(QueryCondition condition);
	
	public void updateOrderStatus(long id, int status);
	
	public int queryOrderMonthStatisticsCount(QueryCondition condition);
	
	public List<OrderStatistics> queryOrderMonthStatistics(QueryCondition condition);

}

