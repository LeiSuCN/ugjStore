package com.uguanjia.o2o.service.xiaobaitu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.service.xiaobaitu.XbtService;
import com.uguanjia.o2o.web.controller.CommonOrderController;
import com.uguanjia.o2o.web.controller.RequestValue;
import com.uguanjia.o2o.web.controller.ServiceOrderController;
import com.uguanjia.o2o.web.dto.XbtOrderDTO;
import com.uguanjia.o2o.web.dto.XbtOrderItemDTO;

/*******************************************
 * @CLASS:XiaobaituServiceOrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月2日 下午10:34:11
 *******************************************/
@Controller
@RequestMapping("/order/xiaobaitu")
public class XiaobaituServiceOrderController implements ServiceOrderController{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	@Inject
	@Named("commonOrderController")
	private ServiceOrderController parent;

	@Override
	public void setParent(ServiceOrderController parent) {
		this.parent = parent;
	}

	@Override
	@RequestMapping("/detail/{orderId}")
	public ModelAndView queryOrderDetail(HttpServletRequest request, @PathVariable long orderId) {
		
		ModelAndView mav = this.parent.queryOrderDetail(request, orderId);
		
		mav.setViewName("/xiaobaitu/order_detail");
		
		return mav;
	}

	@Override
	@RequestMapping(value="/place")
	public ModelAndView placeOrder(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/xiaobaitu/order");
		
		OrderManger orderManger = ((CommonOrderController)this.parent).getOrderManger();
		
		XbtService service = orderManger.queryXbtService();
		
		mav.addObject("service", service);
		
		return mav;
	}

	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	@Override
	public ModelAndView createOrder(HttpServletRequest request
			, Order order) {
		
		ModelAndView mav = new ModelAndView();
		
		try {
			XbtOrderDTO xbtOrderDto = new XbtOrderDTO();
			
			// 客户姓名
			String customerName = request.getParameter("customerName");
			
			// 客户电话
			String customerPhonenumber = request.getParameter("customerPhone");
			
			// 客户地址
			String customerAddress = request.getParameter("customerAddress");
			
			// 订单明细：名称
			String[] itemNames = request.getParameterValues("item_name");
			// 订单明细：数量
			String[] itemCounts = request.getParameterValues("item_count");
			
			// 包装要求
			String pack = request.getParameter("pack");
			
			// 支付方式
			int payment = NumberUtils.toInt(request.getParameter("payment"), -1);
			if( payment <= 0 )
			{
				// TODO error
			}
			
			// 支付凭证
			String voucher = request.getParameter("voucher");
			
			xbtOrderDto.setCustomerName(customerName);
			xbtOrderDto.setCustomerPhonenumber(customerPhonenumber);
			xbtOrderDto.setCustomerAddress(customerAddress);
			xbtOrderDto.setPack(pack);
			xbtOrderDto.setPayment(payment);
			xbtOrderDto.setVoucher(voucher);
			if( itemNames != null && itemNames.length > 0 
					&& itemCounts != null && itemCounts.length > 0)
			{
				int len = Math.min(itemNames.length, itemCounts.length);
				List<XbtOrderItemDTO> items = new ArrayList<>(len);
				for( int i = 0 ; i < len ; i++ )
				{
					String itemName = itemNames[i];
					String itemCount = itemCounts[i];
					XbtOrderItemDTO item = new XbtOrderItemDTO();
					item.setName(itemName);
					item.setCount(NumberUtils.toInt(itemCount, 0));
					items.add(item);
				}
				xbtOrderDto.setItems(items);
			}
			
			Order serviceOrder = xbtOrderDto.toOrder();
			
			mav = this.parent.createOrder(request, serviceOrder);
			
			mav.setViewName("redirect:/store/order/detail/" + serviceOrder.getId());
		} 
		catch (Exception e) {
			logger.error(e);

			mav.addObject(RequestValue.ERROR_MSG, "数据不合法！");
		}
		
		if( mav.getModel().get(RequestValue.ERROR_MSG) != null )
		{
			mav.setViewName("forward:/order/xiaobaitu/place");
		}
		
		return mav;

	}

}

