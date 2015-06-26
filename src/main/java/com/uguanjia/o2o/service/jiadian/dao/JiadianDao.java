package com.uguanjia.o2o.service.jiadian.dao;

import java.util.List;

import com.uguanjia.o2o.service.jiadian.JiadianOrder;
import com.uguanjia.o2o.service.jiadian.JiadianOrderItem;
import com.uguanjia.o2o.service.jiadian.ServiceItem;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月22日
 *******************************************/
public interface JiadianDao
{
    /**
     * @DESCRIPTION: 查询所有家电清洁项目
     * @AUTHOR:sulei
     * @DATE:2015年6月22日
     * @return
     */
    public List<ServiceItem> queryAllServiceItems();
    
    public int insertOrder(JiadianOrder order);
    
    public int insertOrderItems(JiadianOrder order);
    
    public JiadianOrder queryOrderById(long id);
    
    public List<JiadianOrderItem> queryOrderItemsById(long id);

}

