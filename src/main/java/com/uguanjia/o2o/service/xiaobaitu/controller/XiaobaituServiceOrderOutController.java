package com.uguanjia.o2o.service.xiaobaitu.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.manger.StoreManger;
import com.uguanjia.o2o.meta.OrderProcess;
import com.uguanjia.o2o.meta.OrderProcessActivity;
import com.uguanjia.o2o.meta.OrderStatus;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.web.controller.DatatableAjaxResult;

/*******************************************
 * @CLASS:XiaobaituServiceOrderOutController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月26日 上午12:51:11
 *******************************************/
@Controller
@RequestMapping("/out/order/xiaobaitu")
public class XiaobaituServiceOrderOutController {
	
	@Inject
	@Named("orderManger")
	private OrderManger orderManger;
	
	@Inject
	@Named("storeManger")
	private StoreManger storeManger;
	
	/**
	 * @description: 查询所有小白兔订单	
	 * @DATE:2015年3月26日 上午1:04:37
	 * @param request
	 * @param orderQueryDTO
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/xiaobaitu/out/out_order_list");
		
		QueryCondition condition = buildQueryConditionByRequest(request);
		
		StringBuilder params = new StringBuilder();
		
		// 查询条件：日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		appendReqParams(params, "dateFrom", sdf.format(condition.getDateFrom()));
		appendReqParams(params, "dateTo", sdf.format(condition.getDateTo()));
		// 查询条件：订单状态
		appendReqParams(params, "status", String.valueOf(condition.getStatus()));
		
		mav.addObject("condition", condition);
		mav.addObject("urlParams", params.toString());

		return mav;
	}
	
	@RequestMapping("/list/a")
	@ResponseBody
	public DatatableAjaxResult queryOrderAjax(HttpServletRequest request){
		
		DatatableAjaxResult ajaxResult = new DatatableAjaxResult();
		
		// page no.
		int pageNo = NumberUtils.toInt(request.getParameter("sEcho"), 1);
		
		int pageSize = NumberUtils.toInt(request.getParameter("iDisplayLength"), 10);
		
		// 设置查询条件
		QueryCondition condition = new QueryCondition();
		// 查询条件：分页
		condition.setPageNo(pageNo);
		condition.setPageSize(pageSize);
		
		// 查询条件：日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		if( StringUtils.isEmpty(dateFrom)
				|| StringUtils.isEmpty(dateTo) ){
			Calendar calendar = Calendar.getInstance();
			condition.setDateTo(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			condition.setDateFrom(calendar.getTime());
		} else{
			try {
				condition.setDateTo(sdf.parse(dateTo));
				condition.setDateFrom(sdf.parse(dateFrom));
			} catch (ParseException e) {
				Calendar calendar = Calendar.getInstance();
				condition.setDateTo(calendar.getTime());
				calendar.add(Calendar.MONTH, -1);
				condition.setDateFrom(calendar.getTime());
			}
		}
		
		// 查询条件：业务类型：小白兔
		condition.setType(Service.XIAOBAITU.ID);
		
		// 查询条件：订单状态
		int status =  NumberUtils.toInt(request.getParameter("status"), -1);
		if( status != OrderProcess.Status.ACCEPT.id &&  
				status != OrderProcess.Status.COMPLETE.id)
		{
			status =  OrderProcess.Status.ACCEPT.id;
		}
		condition.setStatus(status);
		
		// result
		List<Order> orders =  orderManger.queryOrders(condition);
		
		ajaxResult.setiTotalRecords(condition.getTotal());
		ajaxResult.setiTotalDisplayRecords(condition.getTotal());
		
		
		String[][] data = null;
		
		if( orders != null && orders.size() > 0 )
		{
			int size = orders.size();
			data = new String[size][7];
			
			String root = request.getContextPath();
			
			for( int i = 0 ; i < size ; i++ )
			{
				Order o = orders.get(i);
				
				// 订单号
				data[i][0] = String.valueOf(o.getId());
				// 门店编号
				data[i][1] = String.valueOf(o.getStore());
				// 创建时间
				data[i][2] = sdf.format(o.getCreateTime());
				// 订单状态
				data[i][3] = OrderProcess.Status.get(o.getStatus()).name;
				// 客户姓名
				data[i][4] = o.getCustomerName();
				// 金额
				data[i][5] = String.valueOf(o.getRevenue());
				// 明细按钮
				StringBuilder a = new StringBuilder("<div class='hidden-sm hidden-xs action-buttons'><a class='blue' href='") ;
				a.append(root).append("/out/order/xiaobaitu/detail/").append(o.getId());
				a.append("' title='查看明细'><i class='ace-icon fa fa-search-plus bigger-130'></i></a></div>");
				data[i][6] = a.toString();
			}
		}
		else
		{
			data = new String[0][5];
		}

		ajaxResult.setsEcho(String.valueOf(pageNo));
		ajaxResult.setAaData(data);
		
		return ajaxResult;
	}
	
	/**
	 * @description: 查看单个小白兔订单	
	 * @DATE:2015年3月26日 上午1:04:56
	 * @param request
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/detail/{orderId}")
	public ModelAndView detail(HttpServletRequest request, @PathVariable long orderId){
		
		ModelAndView mav = new ModelAndView("/xiaobaitu/out/out_order_detail");
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		
		Order order = this.orderManger.queryOrderById(condition);
		
		if( order != null )
		{
			Store store = storeManger.queryStoreById(order.getStore());
			mav.addObject("store", store);
		}
		
		mav.addObject("order", order);
		
		return mav;
	}
	
	/**
	 * @description:操作订单状态	
	 * @DATE:2015年3月26日 上午1:05:22
	 * @param orderId
	 * @param processDto
	 * @return
	 */
	@RequestMapping("/close/{orderId}")
	public ModelAndView close(HttpServletRequest request, @PathVariable long orderId ){
		
		ModelAndView mav = new ModelAndView("redirect:/out/order/xiaobaitu/detail/" + orderId);
		
		String errMsg = null;
		
		QueryCondition condition = new QueryCondition();
		condition.setOrderId(orderId);
		
		// 只有当前订单状态为新建时才能接受
		Order order = orderManger.queryOrderById(condition);
		if( order == null ){
			errMsg = "订单不存在！";
		}
		else if( order.getStatus() != OrderStatus.ACCEPT ){
			errMsg = "只有接受状态的订单才能被关闭！";
		}
		else
		{
			Operator operator = OperatorUtils.getCurrentOperator();
			OrderProcessActivity process = new OrderProcessActivity();
			process.setId(orderId);
			process.setOperator( operator.getUsername() );
			process.setStatus(String.valueOf(OrderStatus.FINISH));
			
			String comment = request.getParameter("comment");
			if( comment == null ) comment = StringUtils.EMPTY;
			process.setComment(comment);
			orderManger.updateOrderStatusByProcess(process);
		}

		mav.addObject("error_message", errMsg);
		return mav;
	}
	
	/**
	 * @description:根据HttpServletRequest构建查询条件	
	 * @DATE:2015年5月3日 下午10:21:29
	 * @param request
	 * @return
	 */
	private QueryCondition buildQueryConditionByRequest(HttpServletRequest request){
		
		// 设置查询条件
		QueryCondition condition = new QueryCondition();
		
		// 查询条件：业务类型：小白兔
		condition.setType(Service.XIAOBAITU.ID);

		// 查询条件：分页
		int pageNo = NumberUtils.toInt(request.getParameter("sEcho"), 1);
		int pageSize = NumberUtils.toInt(request.getParameter("iDisplayLength"), 10);
		condition.setPageNo(pageNo);
		condition.setPageSize(pageSize);
		
		// 查询条件：日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		if( StringUtils.isEmpty(dateFrom)
				|| StringUtils.isEmpty(dateTo) ){
			Calendar calendar = Calendar.getInstance();
			condition.setDateTo(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			condition.setDateFrom(calendar.getTime());
		} else{
			try {
				condition.setDateTo(sdf.parse(dateTo));
				condition.setDateFrom(sdf.parse(dateFrom));
			} catch (ParseException e) {
				Calendar calendar = Calendar.getInstance();
				condition.setDateTo(calendar.getTime());
				calendar.add(Calendar.MONTH, -1);
				condition.setDateFrom(calendar.getTime());
			}
		}
		
		// 查询条件：订单状态
		int status =  NumberUtils.toInt(request.getParameter("status"), -1);
		if( status != OrderProcess.Status.ACCEPT.id &&  
				status != OrderProcess.Status.COMPLETE.id)
		{
			status =  OrderProcess.Status.ACCEPT.id;
		}
		condition.setStatus(status);
		
		return condition;
	}
	
	private void appendReqParams(StringBuilder reqParams, String name, String value){
		
		if( !StringUtils.isEmpty(value) && !StringUtils.isEmpty(name) )
		{
			if( reqParams.length() > 0 )reqParams.append("&");
			
			reqParams.append(name).append("=").append(value);
		}
	}
	

	public void setOrderManger(OrderManger orderManger) {
		this.orderManger = orderManger;
	}
	
	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}
}

