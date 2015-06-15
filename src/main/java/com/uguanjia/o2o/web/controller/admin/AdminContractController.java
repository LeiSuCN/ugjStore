package com.uguanjia.o2o.web.controller.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.manger.ContractManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.ContractStatus;
import com.uguanjia.o2o.service.BaseResult;
import com.uguanjia.o2o.web.dto.ContractQueryDTO;

/*******************************************
 * @CLASS:AuthController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午9:58:37
 *******************************************/
@Controller
@RequestMapping("/admin/contract")
public class AdminContractController {
	
	@Inject
	@Named("contractManger")
	private ContractManger contractManger;
	
	/******** ******** ******** ******** ******** ******** ******** ********
	 * 
	 * 合同相关
	 * 
	 ******** ******** ******** ******** ******** ******** ******** ********/
	@RequestMapping("/list")
	public ModelAndView queryAllContracts(HttpServletRequest request
			, @ModelAttribute ContractQueryDTO contractQueryDTO){
		ModelAndView mav = new ModelAndView("/admin/contract_list");
		
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
		// 查询条件：业务类型
		if( contractQueryDTO.getType() > 0 )
		{
			condition.setType(contractQueryDTO.getType());
		}
		// 查询条件：订单状态
		if( contractQueryDTO.getStatus() > 0 )
		{
			condition.setStatus(contractQueryDTO.getStatus());
		}

		
		List<Contract> contracts = 
				this.contractManger.queryContracts(condition);
		
		int total = condition.getTotal();
		int pageSize = condition.getPageSize();
		int pages = total/pageSize;
		if( total%pageSize != 0 )pages++;
		
		mav.addObject("condition", condition);
		mav.addObject("contracts", contracts);
		mav.addObject("pages", pages);
		return mav;
	}
	
	@RequestMapping("/{contractId}/accept/{result}")
	@ResponseBody
	public ModelAndView approveContract(HttpServletRequest request
			, @PathVariable int contractId, @PathVariable int result){
		
		ModelAndView mav = new ModelAndView();
		
		String comment = request.getParameter("comment");
		
		if( result == 1){
			result = ContractStatus.ACCEPT; 
		} else{
			result = ContractStatus.REJECT;
		}
		
		BaseResult changeResult = 
				this.contractManger.changeContractStatus(contractId, result, comment);
		
		if( changeResult.isSuccess() ){
			mav.setViewName("redirect:/admin/contract/list");
		} else{
			mav.addObject("error_msg", "更新协议状态失败！");
			mav.setViewName("/admin/error");
		}
		
		return mav;
	}
	
	public void setContractManger(ContractManger contractManger) {
		this.contractManger = contractManger;
	}
}

