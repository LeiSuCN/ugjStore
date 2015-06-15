package com.uguanjia.o2o.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:OrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年1月28日 下午4:49:47
 *******************************************/
public class CommonOrderController implements ServiceOrderController{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private OrderManger orderManger;

	@Override
	public ModelAndView queryOrderDetail(HttpServletRequest request, long orderId) {
		
		ModelAndView mav = new ModelAndView();
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		
		Order order = orderManger.queryOrderById(condition);
		
		mav.addObject("order", order);
		
		return mav;
	}

	@Override
	public ModelAndView placeOrder(HttpServletRequest request) {
		return null;
	}

	@Override
	public ModelAndView createOrder(HttpServletRequest request
			, Order order) {
		
		ModelAndView response = new ModelAndView();
		
		// 当前操作人员
		Operator operator = OperatorUtils.getCurrentOperator();
		
		// 当前人员所属门店
		Store store = OperatorUtils.getCurrentStore(request);
		
		if( order == null ){
			logger.warn("order is null");
			response.addObject(RequestValue.ERROR_MSG, "订单为空，创建失败！");
			return response;
		}
		else
		{
			order.setStore(store.getId());
		}
		
		// 从合同中获取收益率
		Service service = Service.getById(order.getType());
		Object contract = request.getSession().getAttribute("contract_" + service.CODE);
		
		orderManger.create(order, store, (Contract)contract, operator);
		
		response.addObject(RequestValue.ORDER, order);
		
		return response;
	}

	@Override
	public void setParent(ServiceOrderController parent) {
	}

	public void setOrderManger(OrderManger orderManger) {
		this.orderManger = orderManger;
	}

	public OrderManger getOrderManger() {
		return orderManger;
	}
}

