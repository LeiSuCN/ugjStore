package com.uguanjia.o2o.service.shunfeng.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.web.controller.ServiceOrderController;

/*******************************************
 * @CLASS:ShunfengServiceOrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月15日 下午10:16:27
 *******************************************/
@Controller
@RequestMapping("/order/shunfeng")
public class ShunfengServiceOrderController implements ServiceOrderController {
	
	private final Service service = Service.SHUNFENG;
	
	@Inject
	@Named("ugj_cfg")
	private Configration ugjCfg;

	@Override
	public ModelAndView queryOrderDetail(HttpServletRequest request,
			long orderId) {
		
		throw new NotImplementedException("未实现的外部系统订单详情查询服务");
		
	}

	@Override
	@RequestMapping(value="/place")
	public ModelAndView placeOrder(HttpServletRequest request) {
		
		String path = this.ugjCfg.getValue("service.third." + service.CODE).toString();
		
		ModelAndView redirect = new ModelAndView("redirect:" + path);
		
		return redirect;
	}

	@Override
	public ModelAndView createOrder(HttpServletRequest request,
			Order order) {
		
		throw new NotImplementedException("未实现的外部系统订单创建服务");
		
	}

	@Override
	public void setParent(ServiceOrderController parent) {
	}

	public Configration getUgjCfg() {
		return ugjCfg;
	}

	public void setUgjCfg(Configration ugjCfg) {
		this.ugjCfg = ugjCfg;
	}
}

