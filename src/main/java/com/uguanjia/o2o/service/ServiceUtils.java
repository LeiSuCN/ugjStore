package com.uguanjia.o2o.service;

import java.util.Calendar;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;

import com.uguanjia.o2o.dao.OrderDao;
import com.uguanjia.o2o.manger.QueryCondition;

/*******************************************
 * @CLASS:ServiceUtils
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月2日 上午12:00:57
 *******************************************/
public class ServiceUtils {
	
	/**
	 * @description:获取相应服务的实例	
	 * @DATE:2015年3月2日 上午12:01:58
	 * @param service
	 * @param clz
	 * @return
	 */
	public static <T> T getServiceInstance(Service service, Class<T> clz
			, ApplicationContext context){
		
		return getServiceInstance(service,clz.getSimpleName(), clz, context);
	}
	
	/**
	 * @description:获取相应服务的实例	
	 * @DATE:2015年3月2日 上午12:01:58
	 * @param service
	 * @param clz
	 * @return
	 */
	public static <T> T getServiceInstance(Service service, String name
			, Class<T> clz, ApplicationContext context){
		
		String beanName = service.CODE + name;
		
		return context.getBean(beanName, clz);
	}
	
	/**
	 * @description:生成订单号	
	 * @DATE:2015年3月25日 上午12:23:35
	 * @return
	 */
	public static long createOrderNumber(long storeId, SqlSession session){
		Calendar calendar = Calendar.getInstance();
		
		long orderNumber = calendar.get(Calendar.YEAR)%100 * 10000 
					+ ( calendar.get(Calendar.MONTH) + 1 ) * 100
					+ calendar.get(Calendar.DAY_OF_MONTH);
		
		orderNumber = storeId*1000000000 + orderNumber*1000;
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(storeId);
		
		OrderDao orderDao = session.getMapper(OrderDao.class);
		
		int todayOrderCount = orderDao.queryTodayOrderCountById(condition);
				
	    return orderNumber + todayOrderCount + 1;
	}

}

