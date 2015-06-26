package com.uguanjia.o2o.service.jiadian.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.jiadian.JiadianOrder;
import com.uguanjia.o2o.service.jiadian.JiadianOrderItem;
import com.uguanjia.o2o.service.jiadian.JiadianService;
import com.uguanjia.o2o.service.jiadian.ServiceItem;
import com.uguanjia.o2o.web.controller.RequestValue;
import com.uguanjia.o2o.web.controller.ServiceOrderController;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月22日
 *******************************************/
@Controller
@RequestMapping("/order/jiadian")
public class AijiejiaServiceOrderController implements ServiceOrderController
{
    private Logger logger = LogManager.getLogger(getClass());
    
    @Inject
    @Named("commonOrderController")
    private ServiceOrderController parent;
    
    @Inject
    @Named("jiadianService")
    private JiadianService service;

    @Override
    @RequestMapping("/detail/{orderId}")
    public ModelAndView queryOrderDetail(HttpServletRequest request,
            long orderId)
    {
        ModelAndView mav = this.parent.queryOrderDetail(request, orderId);
        
        mav.setViewName("/" + Service.JIADIAN.CODE + "/order_detail");
        
        return mav;
    }

    @Override
    @RequestMapping(value="/place")
    public ModelAndView placeOrder(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView("/jiadian/order");
        // 家电清洁项目清单
        List<ServiceItem> serviceItems = service.queryServiceItems();
        mav.addObject("serviceItems", serviceItems);
        return mav;
    }

    @Override
    @RequestMapping(value="", method=RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request, Order order)
    {
        ModelAndView mav = new ModelAndView();
        
        try {
            JiadianOrder serviceOrder = new JiadianOrder();
            
            // 客户姓名
            String customerName = request.getParameter("customerName");
            
            // 客户电话
            String customerPhonenumber = request.getParameter("customerPhone");
            
            // 客户地址
            String customerAddress = request.getParameter("customerAddress");
            
            // 客户地址
            String serviceTime = request.getParameter("serviceDate");
            
            // 订单明细：名称
            String[] itemNames = request.getParameterValues("item_name");
            // 订单明细：数量
            String[] itemCounts = request.getParameterValues("item_count");
            
            serviceOrder.setCustomerName(customerName);
            serviceOrder.setCustomerPhonenumber(customerPhonenumber);
            serviceOrder.setCustomerAddress(customerAddress);
            serviceOrder.setServiceTime(serviceTime);

            if( itemNames != null && itemNames.length > 0 
                    && itemCounts != null && itemCounts.length > 0)
            {
                int len = Math.min(itemNames.length, itemCounts.length);
                List<JiadianOrderItem> items = new ArrayList<>(len);
                for( int i = 0 ; i < len ; i++ )
                {
                    String itemName = itemNames[i];
                    String itemCount = itemCounts[i];
                    JiadianOrderItem item = new JiadianOrderItem();
                    item.setCode(NumberUtils.toInt(itemName, 0));
                    item.setAmount(NumberUtils.toInt(itemCount, 0));
                    items.add(item);
                }
                serviceOrder.setItems(items);
            }
            
            mav = this.parent.createOrder(request, serviceOrder);
            
            mav.setViewName("redirect:/store/order/detail/" + serviceOrder.getId());
        } 
        catch (Exception e) {
            logger.error(e);
            mav.addObject(RequestValue.ERROR_MSG, "数据不合法！");
        }
        
        if( mav.getModel().get(RequestValue.ERROR_MSG) != null )
        {
            mav.setViewName("forward:/order/jiadian/place");
        }
        
        return mav;
    }

    @Override
    public void setParent(ServiceOrderController parent)
    {
        this.parent = parent;
    }

}

