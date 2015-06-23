package com.uguanjia.o2o.service.jiadian.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
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

