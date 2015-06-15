package com.uguanjia.o2o.web.controller.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.StoreManger;

/*******************************************
 * @CLASS:StoreController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月15日 下午11:01:13
 *******************************************/
@Controller("StoreStoreController")
@RequestMapping("/store")
public class StoreController {
	
	@Inject
	@Named("storeManger")
	private StoreManger storeManger;
	
	@RequestMapping("/detail")
	public ModelAndView showStoreById(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/store/store_detail");
		
		Store store = OperatorUtils.getCurrentStore(request);
		
		mav.addObject("store", store);
		return mav;
	}

	@RequestMapping("/detail/scanning/{type}")
	public void showStoreScanning(HttpServletRequest request,HttpServletResponse resp
			, @PathVariable String type){
		
		Store store = OperatorUtils.getCurrentStore(request);
		
		File image = this.storeManger.queryStoreScanning(store.getId(), type);
		
		if( !image.exists() || image.isDirectory() ){
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		} 
		else{
			resp.setContentType("image/jpeg");
		}
		
		try (FileInputStream fis = new FileInputStream(image)){
			int len = 0;
	        
			byte buffer[]=new byte[1024];

			OutputStream out = resp.getOutputStream();
			
			while((len = fis.read(buffer))> 0 ){
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @description:	
	 * @DATE:2015年4月11日 上午7:34:39
	 * @return
	 */
	@RequestMapping("/user")
	public ModelAndView showUserInfo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/store/store_user");
		
		Store store = OperatorUtils.getCurrentStore(request);
		
		mav.addObject("store", store);
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
			mav.setViewName("/store/info");
			mav.addObject("msg", "重置密码成功");
		}
		else
		{
			mav.setViewName("/store/store_user");
			mav.addObject("error_msg", "重置密码失败，请确认密码是否输入正确");
		}
		
		return mav;
	}
	
	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}
}

