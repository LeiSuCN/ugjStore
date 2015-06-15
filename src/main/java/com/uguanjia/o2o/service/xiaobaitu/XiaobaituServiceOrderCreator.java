package com.uguanjia.o2o.service.xiaobaitu;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.dao.OrderDao;
import com.uguanjia.o2o.dao.OrderXiaobaituDao;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.ServiceOrderCreator;
import com.uguanjia.o2o.service.ServiceOrderCreatorResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.ServiceUtils;

/*******************************************
 * @CLASS:XiaobaituServiceOrderCreator
 * @DESCRIPTION:创建小白兔订单	
 * @VERSION:v1.0
 * @DATE:2015年3月1日 下午11:34:34
 *******************************************/
public class XiaobaituServiceOrderCreator implements ServiceOrderCreator, ServiceSqlSessionAware {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private SqlSession session;

	@Override
	public ServiceOrderCreatorResult create(Order order, String strategy) {
		
		logger.info("begin to create a new xiaobaitu order");
		
		ServiceOrderCreatorResult result = new ServiceOrderCreatorResult();
		
		if( order == null ){
			result.setFailureMsg("order is null");
		} 
		else if(this.session == null){
			result.setFailureMsg("session is null");
		}
		else if( order.getType() != Service.XIAOBAITU.ID ){
			result.setFailureMsg("order type is not xiaobaitu");
		} 
		else{
			
			XiaobaituOrder xbtOrder = (XiaobaituOrder)order;
			
			xbtOrder.setStatus(OrderStatus.NEW);
			
			int payment = xbtOrder.getPayment();
			
			float back = 0;
			
			String[] strategies = strategy.split(";");
			for( String s : strategies )
			{
				String[] tv = s.split(":");
				if( NumberUtils.toInt(tv[0], -1) == payment )
				{
					back = NumberUtils.toFloat(tv[1], 0);
					break;
				}
			}
			
			OrderDao orderDao = session.getMapper(OrderDao.class);
			OrderXiaobaituDao orderXbtDao = session.getMapper(OrderXiaobaituDao.class);
			
			// 生成订单号
			long orderNum = ServiceUtils.createOrderNumber(xbtOrder.getStore(), session);
			xbtOrder.setId(orderNum);
			// 小白兔订单要有子项
			if( xbtOrder.getItems() != null 
					&& xbtOrder.getItems().size() > 0){
				
				int total = 0;
				
				List<XbtServiceItem> allItems = orderXbtDao.queryAllServiceItems();
				
				XbtService service = wrapXbtServiceItemList(allItems);
				
				Map<Integer, XbtServiceItem> categories = service.getItems();
				
				for( XiaobaituOrderItem item : xbtOrder.getItems() ){
					int id = NumberUtils.toInt(item.getName(), -1);
					if( id > 1000 ){ // 项目id从1000开始
						int categoryId = id/1000;
						if( categories.containsKey(categoryId) ){
							XbtServiceItem category = categories.get(categoryId);
							if( category.getItems().containsKey(id) ){
								int price = category.getItems().get(id).getPrice();
								item.setUnitPrice(price);
								total += price * item.getAmount();
							}
						} else{
							logger.warn("there is no category " + categoryId);
						}
					}
				}
				
				xbtOrder.setRevenue(total);
				xbtOrder.setProfit(back * total);
				
				orderDao.insertOrder(order);
				orderXbtDao.insertOrderXbt(xbtOrder);
				orderXbtDao.insertOrderXbtItems(xbtOrder);
				
				result.setSuccessMsg(null);
				
			} else{
				result.setFailureMsg("xiaobaitu items are empty");
			}
		}
		
		return result;
	}

	@Override
	public void setSession(SqlSession session) {
		this.session = session;
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

}

