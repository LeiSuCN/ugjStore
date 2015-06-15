package com.uguanjia.o2o.web.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.OrderProcessActivity;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.web.dto.OrderQueryDTO;
import com.uguanjia.o2o.web.dto.ProcessDTO;
import com.uguanjia.o2o.web.dto.StandardAjaxResponse;

/*******************************************
 * @CLASS:AuthController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午9:58:37
 *******************************************/
@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	
	@Inject
	@Named("orderManger")
	private OrderManger orderManger;

	public void setOrderManger(OrderManger orderManger) {
		this.orderManger = orderManger;
	}
	
	/******** ******** ******** ******** ******** ******** ******** ********
	 * 
	 * 订单相关
	 * 
	 ******** ******** ******** ******** ******** ******** ******** ********/
	@RequestMapping("/list")
	public ModelAndView queryOrders(@ModelAttribute OrderQueryDTO orderQueryDTO){
		ModelAndView mav = new ModelAndView("/admin/order_list");
		
		// 设置查询条件
		QueryCondition condition = new QueryCondition();
		// 查询条件：分页
		if( orderQueryDTO.getPageSize() <= 0 ){
			condition.setPageSize(OrderQueryDTO.DEFAULT_PAGE_SIZE);
		} else{
			condition.setPageSize(orderQueryDTO.getPageSize());
		}
		
		if( orderQueryDTO.getPageNo() <= 0 ){
			condition.setPageNo(OrderQueryDTO.DEFAULT_PAGE_NO);
		} else{
			condition.setPageNo(orderQueryDTO.getPageNo());
		}
		// 清除分页条件
		condition.setPageNo(0);
		// 查询条件：日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if( StringUtils.isEmpty(orderQueryDTO.getDateFrom())
				|| StringUtils.isEmpty(orderQueryDTO.getDateTo()) ){
			Calendar calendar = Calendar.getInstance();
			condition.setDateTo(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			condition.setDateFrom(calendar.getTime());
		} else{
			try {
				condition.setDateTo(sdf.parse(orderQueryDTO.getDateTo()));
				condition.setDateFrom(sdf.parse(orderQueryDTO.getDateFrom()));
			} catch (ParseException e) {
				Calendar calendar = Calendar.getInstance();
				condition.setDateTo(calendar.getTime());
				calendar.add(Calendar.MONTH, -1);
				condition.setDateFrom(calendar.getTime());
			}
		}
		// 查询条件：门店编号
		condition.setStoreId(NumberUtils.toLong(orderQueryDTO.getStoreId(), 0));
		// 查询条件：业务类型
		if( orderQueryDTO.getType() > 0 )
		{
			condition.setType(orderQueryDTO.getType());
		}
		// 查询条件：订单状态
		if( orderQueryDTO.getStatus() > 0 )
		{
			condition.setStatus(orderQueryDTO.getStatus());
		}
		
		List<Order> orders = this.orderManger.queryOrders(condition);

		// 更新总页数
		int total = condition.getTotal();
		int pageSize = condition.getPageSize();
		int pages = total/pageSize;
		if( total%pageSize != 0 )pages++;

		mav.addObject("condition", condition);
		mav.addObject("pages", pages);
		mav.addObject("orders", orders);
		return mav;
	}

	@RequestMapping("/accept/{orderId}")
	@ResponseBody
	public StandardAjaxResponse<String> acceptOrder(@PathVariable long orderId,@RequestBody ProcessDTO processDto ){
		
		StandardAjaxResponse<String> response = new StandardAjaxResponse<>();
		
		// 只有当前订单状态为新建时才能接受
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		Order order = orderManger.queryOrderById(condition);
		if( order == null ){
			response.setCode(1);
			response.setMessage("订单不存在！");
			return response;
		}
		else if( order.getStatus() != OrderStatus.NEW ){
			response.setCode(1);
			response.setMessage("只有新建状态的订单才能被审核！");
			return response;
		}
		
		// 
		Operator operator = OperatorUtils.getCurrentOperator();
		
		// 
		OrderProcessActivity process = new OrderProcessActivity();
		process.setId(orderId);
		process.setOperator( operator.getUsername() );
		process.setStatus(processDto.getResult());
		process.setComment(processDto.getComment());
		
		orderManger.updateOrderStatusByProcess(process);
		
		response.setCodeAndMessage(0, "审核成功！");
		
		return response;
	}
	

	@RequestMapping("/detail/{orderId}")
	public ModelAndView queryOrderDetail(HttpServletRequest request, @PathVariable long orderId) {
		
		ModelAndView mav = new ModelAndView("/admin/order_detail");
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		Order order = this.orderManger.queryOrderById(condition);
		
		Service service = Service.getById(order.getType());
		
		mav.addObject("serviceCode", service.CODE);
		mav.addObject("order", order);
		return mav;
	}

	
}

