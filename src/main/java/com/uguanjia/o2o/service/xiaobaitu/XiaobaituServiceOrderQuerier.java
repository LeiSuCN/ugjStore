package com.uguanjia.o2o.service.xiaobaitu;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.dao.OrderXiaobaituDao;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.ServiceOrderQuerier;
import com.uguanjia.o2o.service.ServiceOrderQueryResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;

/*******************************************
 * @CLASS:ZhaopianchongxiServiceOrderQuerier
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月28日 下午1:51:30
 *******************************************/
public class XiaobaituServiceOrderQuerier implements ServiceOrderQuerier, ServiceSqlSessionAware{

	
	private SqlSession session;
	
	@Override
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public ServiceOrderQueryResult query(Order order) {
		
		ServiceOrderQueryResult result = new ServiceOrderQueryResult();
		
		if( order == null )
		{
			result.setFailureMsg("订单为空！");
		}
		else if( Service.XIAOBAITU.ID != order.getType() )
		{
			result.setFailureMsg("订单类型不是小白兔干洗！");
		}
		else
		{

			OrderXiaobaituDao serviceDao = session.getMapper(OrderXiaobaituDao.class);

			XiaobaituOrder serviceOrder = serviceDao.queryOrderById(order.getId());
			
			if( serviceOrder != null ){
				serviceOrder.copyOrderInfoFrom(order);
				List<XiaobaituOrderItem> items = serviceDao.queryItemsById(order.getId());
				serviceOrder.setItems(items);
				order = serviceOrder;
			} 
		}
		
		result.setOrder(order);
		return result;
		
	}

}

