package com.uguanjia.o2o.web.controller.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.manger.ContractManger;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.meta.ContractStatus;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.web.dto.ContractQueryDTO;

/*******************************************
 * @CLASS:AuthController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午9:58:37
 *******************************************/
@Controller("StoreContractController")
@RequestMapping("/store/contract")
public class ContractController {
	
	private Logger logger = LogManager.getLogger(getClass());
	
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
		ModelAndView mav = new ModelAndView("/store/contract_list");
		
		Store store = OperatorUtils.getCurrentStore(request);
		long storeId = store.getId();
		
		QueryCondition condition = new QueryCondition();
		
		condition.setStoreId(storeId);
		
		condition.setPageNo(0);
		
		List<Contract> contracts = 
				this.contractManger.queryContracts(condition);
		
		mav.addObject("contracts", contracts);
		return mav;
	}
	
	
	/**
	 * @description:提示签订协议
	 * @return
	 */
	@RequestMapping("/alert/{serviceId}")
	public ModelAndView showAlertContract(HttpServletRequest request
			, HttpServletResponse response, @PathVariable int serviceId){
		
		Service service = Service.getById(serviceId);
		
		// 404
		if( service == null )
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		ModelAndView mav = new ModelAndView("/store/contract_alert");
		
		mav.addObject("service",service);
		return mav;
	}
	
	
	/**
	 * @description:协议预览页面(包含html和同意按钮)
	 * @return
	 */
	@RequestMapping("/preview/{serviceId}")
	public ModelAndView showPreviewContract(HttpServletRequest request
			, HttpServletResponse response, @PathVariable int serviceId){
		
		Service service = Service.getById(serviceId);
		
		// 404
		if( service == null )
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		ModelAndView mav = new ModelAndView("/store/contract_preview");
		mav.addObject("service",service);
		return mav;
	}
	
	/**
	 * @description: html	
	 * @DATE:2015年4月10日 下午11:56:14
	 * @param resp
	 */
	@RequestMapping("/{serviceCode}/html")
	public void showHtmlTemplate(HttpServletResponse resp,
			@PathVariable String serviceCode){
		
		File html = contractManger.queryContractHtmlTemplate(serviceCode);
		
		if( html != null && html.exists() && html.isFile() )
		{
			try (FileInputStream fis = new FileInputStream(html)){
				int len = 0;
		        
				byte buffer[]=new byte[1024 * 4];

				OutputStream out = resp.getOutputStream();
				
				while((len = fis.read(buffer))> 0 ){
					out.write(buffer, 0, len);
				}
			} catch (Exception e) {
				logger.error(e);
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			
		}
		else
		{
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	/**
	 * @description: 申请协议	
	 * @DATE:2015年4月10日 下午6:56:11
	 * @param request
	 * @param serviceId
	 * @return
	 */
	@RequestMapping("/apply/{serviceId}")
	public ModelAndView applyContract(HttpServletRequest request
			, @PathVariable int serviceId){
		
		ModelAndView mav = new ModelAndView();
		
		Service service = Service.getById(serviceId);
		
		if( service == null )
		{
			mav.addObject("error_msg", "找不到编码为" + serviceId + "的服务！");
			mav.setViewName("/store/error");
		}
		else
		{
			Store store = OperatorUtils.getCurrentStore(request);
			long storeId = store.getId();

			Contract contract = new Contract();
			contract.setStoreId(storeId);
			contract.setType(service.ID);
			
			if( Service.XIAOBAITU.ID == service.ID 
					||  Service.ZHAOPIANCHONGXI.ID == service.ID
					||  Service.YIWANGTONG.ID == service.ID
					||  Service.JIADIAN.ID == service.ID)
			{
				contract.setStatus(ContractStatus.ACCEPT);
			}
			else
			{
				contract.setStatus(ContractStatus.APPLY);
			}
			
			this.contractManger.createContract(contract);
			mav.setViewName("redirect:/store/contract/list");
			
		}

		return mav;
	}
	
	public void setContractManger(ContractManger contractManger) {
		this.contractManger = contractManger;
	}
}

