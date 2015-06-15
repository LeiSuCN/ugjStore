package com.uguanjia.o2o.web.controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.manger.StoreManger;
import com.uguanjia.o2o.service.BaseResult;
import com.uguanjia.o2o.web.dto.ContractQueryDTO;

/*******************************************
 * @CLASS:AuthController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午9:58:37
 *******************************************/
@Controller
@RequestMapping("/admin/store")
public class AdminStoreController {

	@Inject
	@Named("storeManger")
	private StoreManger storeManger;

	/******** ******** ******** ******** ******** ******** ******** ********
	 * 
	 * 门店相关
	 * 
	 ******** ******** ******** ******** ******** ******** ******** ********/
	@RequestMapping("/list")
	public ModelAndView showStores(@ModelAttribute ContractQueryDTO contractQueryDTO){
		ModelAndView mav = new ModelAndView("/admin/store_list");
		
		QueryCondition condition = new QueryCondition();
		
		if( contractQueryDTO.getPageNo() < 1 )
			contractQueryDTO.setPageNo(QueryCondition.DEFAULT_PAGE_NO);
		
		if( contractQueryDTO.getPageSize() < 1 )
			contractQueryDTO.setPageSize(QueryCondition.DEFAULT_PAGE_SIZE);
		
		condition.setPageNo(contractQueryDTO.getPageNo());
		condition.setPageSize(contractQueryDTO.getPageSize());
		
		// 清除分页条件
		condition.setPageNo(0);
		
		// 查询条件：门店编号
		condition.setStoreId(NumberUtils.toLong(contractQueryDTO.getStoreId(), 0));
		
		List<Store> stores = this.storeManger.queryStores(condition);
		
		int total = condition.getTotal();
		int pageSize = condition.getPageSize();
		int pages = total/pageSize;
		if( total%pageSize != 0 )pages++;
		
		mav.addObject("condition", condition);
		mav.addObject("stores", stores);
		mav.addObject("pages", pages);
		return mav;
	}
	
	@RequestMapping("/listapply")
	public ModelAndView showApplyStores(){
		ModelAndView mav = new ModelAndView("/admin/store_listapply");
		
		QueryCondition condition = new QueryCondition();
		// 清除分页条件
		condition.setPageNo(0);
		
		List<Store> stores = this.storeManger.queryApplyStores(condition);
		
		mav.addObject("condition", condition);
		mav.addObject("stores", stores);
		return mav;
	}
	
	@RequestMapping("/detail/{storeId}")
	public ModelAndView showStoreById( @PathVariable long storeId ){
		ModelAndView mav = new ModelAndView("/admin/store_detail");
		
		Store store = this.storeManger.queryStoreById(storeId);
		
		List<String> users = null;
		
		if( store != null )
		{
			users = this.storeManger.queryUsersByStore(storeId);
		}
		else
		{
			users = new ArrayList<>();
		}
		
		mav.addObject("store", store);
		mav.addObject("users", users);
		return mav;
	}
	
	@RequestMapping("/detail/apply/{storeId}")
	public ModelAndView showApplyStoreById( @PathVariable long storeId ){
		ModelAndView mav = new ModelAndView("/admin/store_detail_apply");
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(storeId);
		
		Store store = this.storeManger.queryApplyStoreById(condition);
		
		List<String> users = null;
		
		if( store != null )
		{
			users = this.storeManger.queryUsersByStore(storeId);
		}
		else
		{
			users = new ArrayList<>();
		}
		
		mav.addObject("store", store);
		mav.addObject("users", users);
		return mav;
	}
	
	@RequestMapping("/detail/{storeId}/scanning/{type}")
	public void showStoreScanning(HttpServletRequest request,HttpServletResponse resp
			, @PathVariable long storeId, @PathVariable String type){
		
		File image = this.storeManger.queryStoreScanning(storeId, type);
		
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
		
	@RequestMapping("/accept/{storeId}/{approval}")
	public ModelAndView acceptApplyStore( HttpServletRequest request
			,@PathVariable long storeId, @PathVariable int approval){
		
		ModelAndView mav = new ModelAndView();
		
		// 审批人
		Operator operator = OperatorUtils.getCurrentOperator();
		
		// 审批意见
		String comment = request.getParameter("comment");
		if( comment == null )comment = StringUtils.EMPTY;
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(storeId);
		
		Store store = this.storeManger.queryApplyStoreById(condition);
		
		BaseResult result = null;
		if( approval == 1)
		{
			result = this.storeManger.acceptStore(store, operator.getUsername(), comment);
		}
		else
		{
			result = this.storeManger.rejectStore(store, operator.getUsername(), comment);
			if( result.isSuccess() )
			{
				result.setFailureMsg("您已经拒绝了该门店的申请，请及时通知店主修改申请信息！");
			}
			
		}
		
		if( result.isSuccess() ){
			mav.setViewName("redirect:/admin/store/detail/" + result.getMessage());
		}
		else
		{
			mav.addObject("error_msg", result.getMessage());
			mav.setViewName("redirect:/admin/store/detail/apply/" + storeId);
		}
		
		return mav;
	}
	
	@RequestMapping("/servicecount/add")
	public ModelAndView acceptApplyStore(@ModelAttribute StoreServiceAccount serviceAccount){
		
		ModelAndView mav = new ModelAndView("redirect:/admin/store/detail/" + serviceAccount.getStoreId());
		
		this.storeManger.updateServiceAccount(serviceAccount);
		
		return mav;
	}	

	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}
}

