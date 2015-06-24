package com.uguanjia.o2o.service.jiadian.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.jiadian.JiadianService;
import com.uguanjia.o2o.service.jiadian.ServiceItem;
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
    
    @Inject
    @Named("jiadianService")
    private JiadianService service;

    @Override
    public ModelAndView queryOrderDetail(HttpServletRequest request,
            long orderId)
    {
        
        // TODO Auto-generated method stub
        return null;
        
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
    public ModelAndView createOrder(HttpServletRequest request, Order order)
    {
        
        // TODO Auto-generated method stub
        return null;
        
    }

    @Override
    public void setParent(ServiceOrderController parent)
    {
        
        // TODO Auto-generated method stub
        
    }

}

