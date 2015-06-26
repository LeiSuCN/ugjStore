package com.uguanjia.o2o.service.jiadian;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.dao.OrderDao;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.ServiceOrderCreator;
import com.uguanjia.o2o.service.ServiceOrderCreatorResult;
import com.uguanjia.o2o.service.ServiceSqlSessionAware;
import com.uguanjia.o2o.service.ServiceUtils;
import com.uguanjia.o2o.service.jiadian.dao.JiadianDao;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月25日
 *******************************************/
public class JiadianServiceOrderCreator implements ServiceOrderCreator, ServiceSqlSessionAware
{
    private final Logger logger = LogManager.getLogger(this.getClass());
    
    private SqlSession session;
    
    @Override
    public ServiceOrderCreatorResult create(Order order, String strategy)
    {
        logger.info("begin to create a new jiadian order");
        ServiceOrderCreatorResult result = new ServiceOrderCreatorResult();
        
        if( order == null ){
            result.setFailureMsg("order is null");
        } 
        else if(this.session == null){
            result.setFailureMsg("session is null");
        }
        else if( order.getType() != Service.JIADIAN.ID ){
            result.setFailureMsg("order type is not jiadian");
        }else if( ! (order instanceof JiadianOrder) ){
            result.setFailureMsg("order class is not JiadianOrder");
        }else{
            createServiceOrder((JiadianOrder)order, strategy, result);
        }
        return result;
    }
    
    private void createServiceOrder(JiadianOrder order, String strategy, ServiceOrderCreatorResult result){
        
        List<JiadianOrderItem> items = order.getItems();
        
        // 
        if( items == null || items.size() == 0 ){
            result.setFailureMsg("jiadian items are empty");
            return;
        }
        
        // 计算总额
        JiadianDao serviceOrderDao = session.getMapper(JiadianDao.class);
        int total = 0;
        List<ServiceItem> serviceItems = serviceOrderDao.queryAllServiceItems();
        // TODO 【重构】 算法优化
        for( JiadianOrderItem item : items ){
            int amount = item.getAmount();
            if( amount <= 0 ){
                amount = 0;
                item.setAmount(amount);
            }
            for( ServiceItem serviceItem : serviceItems ){
                if( item.getCode() == serviceItem.getId() ){
                    int price = serviceItem.getPrice();
                    item.setPrice(price);
                    total += amount * price;
                    break;
                }
            }
        }
        
        order.setProfit(total);
        order.setRevenue(total * NumberUtils.toFloat(strategy, 0));
        
        // 生成订单号
        long orderNum = ServiceUtils.createOrderNumber(order.getStore(), session);
        order.setId(orderNum);
        
        // 生成主订单
        OrderDao orderDao = session.getMapper(OrderDao.class);
        orderDao.insertOrder(order);
        
        // 生成订单信息
        serviceOrderDao.insertOrder(order);
        
        // 生成详细订单
        serviceOrderDao.insertOrderItems(order);
    }

    @Override
    public void setSession(SqlSession session)
    {
        this.session = session;
    }
}

