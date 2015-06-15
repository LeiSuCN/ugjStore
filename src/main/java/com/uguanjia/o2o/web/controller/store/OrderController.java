package com.uguanjia.o2o.web.controller.store;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.document.ExcelReportCreator;
import com.uguanjia.o2o.document.OrderSheetHandler;
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
 * @CLASS:OrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月30日 上午2:08:02
 *******************************************/
@Controller("StoreOrderController")
@RequestMapping("/store/order")
public class OrderController {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	@Inject
	@Named("orderManger")
	private OrderManger orderManger;
	
	@RequestMapping("/list")
	public ModelAndView queryOrdersForStore(HttpServletRequest request
			, @ModelAttribute OrderQueryDTO orderQueryDTO){
		
		Store store = OperatorUtils.getCurrentStore(request);
		long storeId = store.getId();
		
		ModelAndView mav = new ModelAndView("/store/order_list");
		
		// query condition
		if( orderQueryDTO == null ){
			orderQueryDTO = new OrderQueryDTO();
			orderQueryDTO.restore();
		}
		
		QueryCondition condition = parseOrderQueryDTO2QueryCondition(orderQueryDTO);
		// 查询条件：无分页
		condition.setPageNo(OrderQueryDTO.NONE_PAGE);
		// 只能查询当前门店的订单
		condition.setStoreId(storeId);
		
		// result
		List<Order> orders =  orderManger.queryOrderPageByStore(condition);
		
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
	

	@RequestMapping("/detail/{orderId}")
	public ModelAndView queryOrderDetail(HttpServletRequest request, @PathVariable long orderId) {
		
		ModelAndView mav = new ModelAndView("/store/order_detail");
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		Order order = this.orderManger.queryOrderById(condition);
		
		Service service = Service.getById(order.getType());
		
		mav.addObject("serviceCode", service.CODE);
		mav.addObject("order", order);
		return mav;
	}
	
	@RequestMapping("/cancel/{orderId}")
	@ResponseBody
	public StandardAjaxResponse<String> cancelOrder(HttpServletRequest request,
			@PathVariable long orderId,@RequestBody ProcessDTO processDto ){
		
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
			response.setMessage("只有新建状态的订单才能被取消！");
			return response;
		}
		
		// 当前人员所属门店
		Store store = OperatorUtils.getCurrentStore(request);
		if( store.getId() != order.getStore() )// 该订单要属于当前用户才可以
		{
			response.setCodeAndMessage(-1, "该订单不存在！");
		}
		else
		{
			Operator operator = OperatorUtils.getCurrentOperator();
			OrderProcessActivity process = new OrderProcessActivity();
			process.setId(orderId);
			process.setOperator( operator.getUsername() );
			process.setStatus(String.valueOf(OrderStatus.CANCEL));
			process.setComment(processDto.getComment());
			orderManger.updateOrderStatusByProcess(process);
			response.setCodeAndMessage(0, "取消成功！");
		}
		
		return response;
	}

	
	@RequestMapping("/download")
	public ModelAndView downloadOrdersForStore(HttpServletRequest request
			,	HttpServletResponse response
			, @ModelAttribute OrderQueryDTO orderQueryDTO){
		
		Store store = OperatorUtils.getCurrentStore(request);
		long storeId = store.getId();
		
		// query condition
		if( orderQueryDTO == null ){
			orderQueryDTO = new OrderQueryDTO();
			orderQueryDTO.restore();
		}
		
		QueryCondition condition = parseOrderQueryDTO2QueryCondition(orderQueryDTO);
		// 查询条件：无分页
		condition.setPageNo(OrderQueryDTO.NONE_PAGE);
		// 只能查询当前门店的订单
		condition.setStoreId(storeId);
		
		// result
		List<Order> orders =  orderManger.queryOrderPageByStore(condition);
		
		OrderSheetHandler osh = new OrderSheetHandler();
		osh.setOrders(orders);
		
		ExcelReportCreator rc = new ExcelReportCreator();
		rc.setSheetHandler(osh);
		
		try(OutputStream os = response.getOutputStream()){
			
			
			
			response.setContentType("application/msexcel");
			
			response.setHeader("Content-Disposition","attachment;filename=" 
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
					+ ".xls");
			
			rc.create(os);
			
		} catch (IOException e) {
			logger.error(e);
		}
		
		return null;
	}
	
	private QueryCondition parseOrderQueryDTO2QueryCondition(OrderQueryDTO orderQueryDTO){
		QueryCondition condition = new QueryCondition();
		
		// 查询条件：分页条件
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
		
		return condition;
	}
}

