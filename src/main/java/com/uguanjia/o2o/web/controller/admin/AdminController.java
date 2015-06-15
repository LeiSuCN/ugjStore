package com.uguanjia.o2o.web.controller.admin;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.StoreManger;

/*******************************************
 * @CLASS:AuthController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午9:58:37
 *******************************************/
@Controller
@RequestMapping("/admin")
public class AdminController {
	

	@Inject
	@Named("storeManger")
	private StoreManger storeManger;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("/admin/index");
		return mav;
	}
	
	/**
	 * @description:	
	 * @DATE:2015年4月11日 上午7:34:39
	 * @return
	 */
	@RequestMapping("/user")
	public ModelAndView showUserInfo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/admin/admin_user");
		return mav;
	}
	
	
	@RequestMapping(value="/user/pwd",method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView changeUserPassword(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		Operator operator = OperatorUtils.getCurrentOperator();
		
		int result = 
				this.storeManger.changeUserPassword(operator.getUsername()
						, request.getParameter("old"), request.getParameter("new"));
		
		if( result > 0 )
		{
			mav.setViewName("/admin/info");
			mav.addObject("msg", "重置密码成功");
		}
		else
		{
			mav.setViewName("/admin/admin_user");
			mav.addObject("error_msg", "重置密码失败，请确认密码是否输入正确");
		}
		
		return mav;
	}

	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}
}

