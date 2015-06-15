package com.uguanjia.o2o.service.zhaopianchongxi;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.dao.OrderZhaopianchongxiDao;
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
public class ZhaopianchongxiServiceOrderQuerier implements ServiceOrderQuerier, ServiceSqlSessionAware{

	
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
		else if( Service.ZHAOPIANCHONGXI.ID != order.getType() )
		{
			result.setFailureMsg("订单类型不是照片冲洗！");
		}
		else
		{
			OrderZhaopianchongxiDao serviceDao = session.getMapper(OrderZhaopianchongxiDao.class);
			//ZhaopianchongxiOrder serviceOrder = serviceDao.selectZhaopianchongxiOrderById(order.getId());
			ZhaopianchongxiOrder serviceOrder = new ZhaopianchongxiOrder();
			serviceOrder.copyOrderInfoFrom(order);
			List<ZhaopianchongxiOrderItem> items = serviceDao.queryOrderItemsById(serviceOrder.getId());
			serviceOrder.setItems(items);
			result.setMessage("查询成功");
			order = serviceOrder;
		}
		
		result.setOrder(order);
		return result;
		
	}

}

