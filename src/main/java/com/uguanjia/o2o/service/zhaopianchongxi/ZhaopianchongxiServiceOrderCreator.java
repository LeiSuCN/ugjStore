package com.uguanjia.o2o.service.zhaopianchongxi;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.dao.OrderDao;
import com.uguanjia.o2o.dao.OrderZhaopianchongxiDao;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.ServiceOrderCreator;
import com.uguanjia.o2o.service.ServiceOrderCreatorResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.ServiceUtils;

/*******************************************
 * @CLASS:ZhaopianchongxiServiceOrderCreator
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月27日 上午11:33:26
 *******************************************/
public class ZhaopianchongxiServiceOrderCreator implements ServiceOrderCreator, ServiceSqlSessionAware{
	
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private SqlSession session;

	@Override
	public ServiceOrderCreatorResult create(Order order, String strategy) {
		logger.info("begin to create a new zhaopianchongxi order");
		
		ServiceOrderCreatorResult result = new ServiceOrderCreatorResult();
		
		if( order == null ){
			result.setFailureMsg("订单为空!");
		} 
		else if(this.session == null){
			result.setFailureMsg("数据连接为空！");
		}
		else if( order.getType() != Service.ZHAOPIANCHONGXI.ID ){
			result.setFailureMsg("订单类型不是照片冲洗！");
		} 
		else{
			
			ZhaopianchongxiOrder zpcxOrder = (ZhaopianchongxiOrder)order;
			// 要有子项
			List<ZhaopianchongxiOrderItem> items = zpcxOrder.getItems();
			if( items != null && items.size() > 0 )
			{
				float back = NumberUtils.toFloat(strategy, 0);// 收益率
			
				zpcxOrder.setStatus(OrderStatus.NEW);
				
				OrderDao orderDao = session.getMapper(OrderDao.class);
				OrderZhaopianchongxiDao orderZhaopianchongxiDao = 
						session.getMapper(OrderZhaopianchongxiDao.class);
				
				float total = 0;
				
				// 生成订单号
				long orderNum = ServiceUtils.createOrderNumber(order.getStore(), session);
				zpcxOrder.setId(orderNum);
				
				List<ZhaopianchongxiServiceItem> serviceItems = 
						orderZhaopianchongxiDao.queryServiceItems();
				
				
				for( ZhaopianchongxiOrderItem orderItem : items)
				{
					if( orderItem.getAmount() <= 0 )continue;
					
					int serviceItemId = NumberUtils.toInt(orderItem.getSize(), -1);
					
					if( serviceItemId <= 0 ) continue;
					
					for( ZhaopianchongxiServiceItem serviceItem : serviceItems)
					{
						if( serviceItem.getId() == serviceItemId )
						{
							float price = serviceItem.getPrice();
							String size = serviceItem.getName();
							
							orderItem.setPrice(price);
							orderItem.setSize(size);
							
							total += price*orderItem.getAmount();
						}
					}
				}
				
				// 设置价格和收益
				zpcxOrder.setRevenue(total);
				zpcxOrder.setProfit(back * total);
				
				orderDao.insertOrder(order);
				//orderZhaopianchongxiDao.insertZhaopianchongxiOrder(zpcxOrder);
				orderZhaopianchongxiDao.createOrderItems(zpcxOrder);
				result.setSuccessMsg(null);
			}
			else
			{
				result.setFailureMsg("订单项为空！");
			}
		}
		
		return result;

	}
	
	@Override
	public void setSession(SqlSession session) {
		this.session = session;
	}
}

