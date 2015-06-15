package com.uguanjia.o2o.web.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.manger.OperatorUtils;

/*******************************************
 * @CLASS:IndexController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月30日 上午1:18:44
 *******************************************/
@Controller
@RequestMapping("/store")
public class IndexController {
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("/store/index");
		
		Operator operator = OperatorUtils.getCurrentOperator();
		
		mav.addObject("operator", operator);
		return mav;
	}
	
	@RequestMapping("/mall")
	public ModelAndView mall(){
		ModelAndView mav = new ModelAndView("/store/mall");
		
		Operator operator = OperatorUtils.getCurrentOperator();
		
		mav.addObject("operator", operator);
		return mav;
	}
}

