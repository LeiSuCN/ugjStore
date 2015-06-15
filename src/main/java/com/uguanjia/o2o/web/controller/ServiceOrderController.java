package com.uguanjia.o2o.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;

/*******************************************
 * @CLASS:ServiceOrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月2日 下午10:12:41
 *******************************************/
public interface ServiceOrderController {
	
	/**
	 * @description:查询订单详细信息	
	 * @DATE:2015年3月2日 下午10:21:15
	 * @param request
	 * @param orderId
	 * @return
	 */
	public ModelAndView queryOrderDetail(HttpServletRequest request,long orderId);
	
	/**
	 * @description:订单页	
	 * @DATE:2015年3月2日 下午10:21:48
	 * @param request
	 * @return
	 */
	public ModelAndView placeOrder(HttpServletRequest request);
	
	/**
	 * @description:创建订单	
	 * @DATE:2015年3月2日 下午10:22:12
	 * @param request
	 * @param order
	 * @return
	 */
	public ModelAndView createOrder(HttpServletRequest request, Order order);
	
	public void setParent(ServiceOrderController parent);

}

