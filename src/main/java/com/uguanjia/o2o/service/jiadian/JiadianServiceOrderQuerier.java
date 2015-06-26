package com.uguanjia.o2o.service.jiadian;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.ServiceOrderQuerier;
import com.uguanjia.o2o.service.ServiceOrderQueryResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.jiadian.dao.JiadianDao;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月25日
 *******************************************/
public class JiadianServiceOrderQuerier implements ServiceOrderQuerier, ServiceSqlSessionAware
{
    private SqlSession session;

    @Override
    public void setSession(SqlSession session)
    {
        this.session = session;
    }

    @Override
    public ServiceOrderQueryResult query(Order order)
    {
        ServiceOrderQueryResult result = new ServiceOrderQueryResult();
        
        if( order == null )
        {
            result.setFailureMsg("订单为空！");
        } else{
            long orderId = order.getId();
            JiadianDao serviceDao = session.getMapper(JiadianDao.class);
            JiadianOrder serviceOrder = serviceDao.queryOrderById(orderId);
            if( serviceOrder != null ){
                serviceOrder.copyOrderInfoFrom(order);
                List<JiadianOrderItem> items = serviceDao.queryOrderItemsById(orderId);
                serviceOrder.setItems(items);
                order = serviceOrder;
            } 
        }
        
        result.setOrder(order);
        return result;
    }

}

