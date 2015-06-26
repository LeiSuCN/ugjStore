package com.uguanjia.o2o.service.jiadian;

import java.util.List;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.Service;

/**
 * 家电清洁订单
 * @author SuLei
 *
 */
public class JiadianOrder extends Order{
    
    /**
     * 服务时间
     */
    private String serviceTime;
    
    /**
     * 订单清单
     */
    private List<JiadianOrderItem> items;
    
    public JiadianOrder()
    {
        this.type = Service.JIADIAN.ID;
    }

    public String getServiceTime()
    {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public List<JiadianOrderItem> getItems()
    {
        return items;
    }

    public void setItems(List<JiadianOrderItem> items)
    {
        this.items = items;
    }
}
