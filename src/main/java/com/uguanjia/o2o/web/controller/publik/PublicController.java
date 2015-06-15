package com.uguanjia.o2o.web.controller.publik;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Operator;
import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreAccount;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.manger.StoreManger;
import com.uguanjia.o2o.meta.Roles;
import com.uguanjia.o2o.meta.StoreStatus;
import com.uguanjia.o2o.service.BaseResult;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.web.dto.StandardAjaxResponse;

/*******************************************
 * @CLASS:PublicController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月11日 上午7:16:50
 *******************************************/
@Controller
@RequestMapping("/public")
public class PublicController {
	
	private final Logger logger = LogManager.getLogger(getClass());
	
	@Inject
	@Named("storeManger")
	private StoreManger storeManger;
	
	@Inject
	@Named("ugj_cfg")
	Configration ugjCfg;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		
		ModelAndView mav = new ModelAndView();
		
		// 当前操作人员
		Operator operator = OperatorUtils.getCurrentOperator();

		
		if( operator == null )
		{
			
		}
		else if( operator.hasRole(Roles.ROLE_ADMIN) )
		{
			mav.setViewName("redirect:/admin/index");
		}
		else if( operator.hasRole(Roles.ROLE_OUT_XIAOBAITU) )
		{
			mav.setViewName("redirect:/out/order/xiaobaitu/list");
		}
		else
		{
			mav.setViewName("redirect:/store/index");
		}
		
		return mav;
	}
	
	@RequestMapping("/store/map")
	public ModelAndView showStoreMap(){
		
		ModelAndView mav = new ModelAndView("/public/map");
		
		return mav;
	}
	
	
	@RequestMapping("/store/area/{areaCode}/list")
	@ResponseBody
	public StandardAjaxResponse<Store> ajaxStoreList(@PathVariable long areaCode){
		
		StandardAjaxResponse<Store> resp = new StandardAjaxResponse<>();
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(areaCode);
		
		List<Store> stores = storeManger.queryStoresByCode(condition);
		
		resp.setCodeAndMessage(0, "查询成功");
		
		resp.setData(stores);
		
		return resp;
	}
	
	@RequestMapping("/store/location")
	@ResponseBody
	public ModelAndView storeLocation(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/public/store_location");
		mav.addObject("lng", NumberUtils.toFloat(request.getParameter("lng"), 0));
		mav.addObject("lat", NumberUtils.toFloat(request.getParameter("lat"), 0));
		mav.addObject("zoom", NumberUtils.toInt(request.getParameter("zoom"), 20));
		return mav;
	}
	
	/**
	 * @description: “我的优管家”	
	 * @DATE:2015年3月21日 下午11:59:31
	 * @return
	 */
	@RequestMapping("/my")
	public ModelAndView my(){
		ModelAndView mav = new ModelAndView("/public/my");
		
		// 当前操作人员
		Operator operator = OperatorUtils.getCurrentOperator();
		
		mav.addObject("ugj_user", operator);
		return mav;
	}
	
	/**
	 * @description:注册	
	 * @DATE:2015年4月9日 上午6:41:13
	 * @return
	 */
	@RequestMapping("/register")
	public ModelAndView register(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/public/register");
		
		// post为提交数据，get为获取页面
		if( "post".equalsIgnoreCase(request.getMethod()) )
		{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			List<String> errorFields = new ArrayList<>();
			String para;
			Store store = new Store();
			StoreAccount account = new StoreAccount();
			store.setAccount(account);
			
			// 门店名称
			para = "storename";
			store.setName(request.getParameter(para));
			if( StringUtils.isEmpty(store.getName()) )
			{
				errorFields.add(para);
			}
			// 工商注册号
			para = "registrationNo";
			store.setRegistrationNo(NumberUtils.toLong(request.getParameter(para), -1));
			if( store.getRegistrationNo() < 100000000000000l 
					|| store.getRegistrationNo() > 999999999999999l )
			{
				errorFields.add(para);
			}
			// 法定代表人
			para = "legalPerson";
			store.setLegalPerson(request.getParameter(para));
			if( StringUtils.isEmpty(store.getLegalPerson()) )
			{
				errorFields.add(para);
			}
			// 电话
			para = "phonenumber";
			store.setPhonenumber(request.getParameter(para));
			long mobile = NumberUtils.toLong(store.getPhonenumber(),  -1);
			if( mobile < 13000000000l || mobile > 19000000000l )
			{
				errorFields.add(para);
			}
			String area = "";
			// 区域编码 省
			para = "address_province";
			area += request.getParameter(para);
			// 区域编码 市
			para = "address_city";
			area += request.getParameter(para);
			// 区域编码 区
			para = "address_area";
			area += request.getParameter(para);
			
			store.setArea(NumberUtils.toInt(area, -1));
			if( store.getArea() < 100000 || store.getArea() > 999999 )
			{
				errorFields.add("address");
			}
			
			// 地址
			para = "address";
			store.setAddress(request.getParameter(para));
			if( StringUtils.isEmpty(store.getAddress()) )
			{
				errorFields.add(para);
			}
			
			// gps
			para = "location";
			String location = request.getParameter(para);
			if( StringUtils.isEmpty(location) )
			{
				errorFields.add(para);
			}
			else
			{
				String[] lnglat = location.split(",");
				if( lnglat == null || lnglat.length != 2)
				{
					errorFields.add(para);
				}
				else
				{
					
					float lng = NumberUtils.toFloat(lnglat[0], -1);
					float lat = NumberUtils.toFloat(lnglat[1], -1);
					
					if( lng <=0 || lat <= 0 )
					{
						errorFields.add(para);
					}
					else
					{
						store.setLat(lat);
						store.setLon(lng);
					}
				}
			}
			
			// 邮编
			para = "alipay";
			store.setAlipay(request.getParameter(para));
			if( StringUtils.isEmpty(store.getAlipay()) )
			{
				errorFields.add(para);
			}
			// 邮编
			para = "qq";
			store.setQq(NumberUtils.toLong(request.getParameter(para)));
			if( store.getQq() < 10000 )
			{
				errorFields.add(para);
			}
			
			// 银行账户户名
			para = "accountName";
			account.setName(request.getParameter(para));
			if( StringUtils.isEmpty(store.getName()) )
			{
				errorFields.add(para);
			}
			// 账号
			para = "accountNumber";
			account.setNumber(request.getParameter(para));
			if( StringUtils.isEmpty(account.getNumber()) )
			{
				errorFields.add(para);
			}
			// 开户行
			para = "bank";
			account.setBank(request.getParameter(para));
			if( StringUtils.isEmpty(account.getBank()) )
			{
				errorFields.add(para);
			}
			
			
			MultipartFile file = null;
			// 法人身份证（正面）
			para = "idcard_a";
			file = multipartRequest.getFile(para);
			if( file == null || file.getSize() <= 0 ){
				errorFields.add(para);
			}
			// 法人身份证（反面）
			para = "idcard_b";
			file = multipartRequest.getFile(para);
			if( file == null || file.getSize() <= 0 ){
				errorFields.add(para);
			}
			
			if( request.getParameter("cainiao") != null )
			{
				StoreServiceAccount maowuAccount = new StoreServiceAccount();
				maowuAccount.setType(Service.CAINIAO.ID);
				maowuAccount.setAccount(request.getParameter("cainiao"));
				maowuAccount.setPassword(StringUtils.EMPTY);
				
				List<StoreServiceAccount> serviceAccount = new ArrayList<>();
				serviceAccount.add(maowuAccount);
				
				store.setServiceAccounts(serviceAccount);
			}

			
			if( errorFields.size() > 0 )
			{
				mav.addObject("error_fields", errorFields);
				mav.addObject("store", store);
			}
			else
			{
				BaseResult result = storeManger.createStore( store );
				if( result.isSuccess() )
				{
					long storeId = store.getId();
					saveFile(storeId, "idcard_a", multipartRequest);
					saveFile(storeId, "idcard_b", multipartRequest);
					saveFile(storeId, "license", multipartRequest);
					saveFile(storeId, "store_a", multipartRequest);
					saveFile(storeId, "store_b", multipartRequest);
					
					mav.addObject("store", store);
					mav.setViewName("/public/register_success");
				}
				else
				{
					mav.addObject("store", store);
					mav.addObject("error_msg", result.getMessage());
				}
			}
			
		}
		
		return mav;
	}
	
	/**
	 * @description:重新注册	
	 * @DATE:2015年4月9日 上午6:41:13
	 * @return
	 */
	@RequestMapping("/reregister")
	public ModelAndView reregister(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/public/register");
		
		Store store = OperatorUtils.getCurrentStore(request);
		
		// post为提交数据，get为获取页面
		if( "post".equalsIgnoreCase(request.getMethod()) )
		{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			List<String> errorFields = new ArrayList<>();
			String para;
			StoreAccount account = new StoreAccount();
			store.setAccount(account);
			
			// 门店名称
			para = "storename";
			store.setName(request.getParameter(para));
			if( StringUtils.isEmpty(store.getName()) )
			{
				errorFields.add(para);
			}
			// 工商注册号
			para = "registrationNo";
			store.setRegistrationNo(NumberUtils.toLong(request.getParameter(para), -1));
			if( store.getRegistrationNo() < 100000000000000l 
					|| store.getRegistrationNo() > 999999999999999l )
			{
				errorFields.add(para);
			}
			// 法定代表人
			para = "legalPerson";
			store.setLegalPerson(request.getParameter(para));
			if( StringUtils.isEmpty(store.getLegalPerson()) )
			{
				errorFields.add(para);
			}
			// 电话
			para = "phonenumber";
			store.setPhonenumber(request.getParameter(para));
			long mobile = NumberUtils.toLong(store.getPhonenumber(),  -1);
			if( mobile < 13000000000l || mobile > 19000000000l )
			{
				errorFields.add(para);
			}
			String area = "";
			// 区域编码 省
			para = "address_province";
			area += request.getParameter(para);
			// 区域编码 市
			para = "address_city";
			area += request.getParameter(para);
			// 区域编码 区
			para = "address_area";
			area += request.getParameter(para);
			
			store.setArea(NumberUtils.toInt(area, -1));
			if( store.getArea() < 100000 || store.getArea() > 999999 )
			{
				errorFields.add("address");
			}
			
			// 地址
			para = "address";
			store.setAddress(request.getParameter(para));
			if( StringUtils.isEmpty(store.getAddress()) )
			{
				errorFields.add(para);
			}
			
			// gps
			para = "location";
			String location = request.getParameter(para);
			if( !StringUtils.isEmpty(location) )
			{
				String[] lnglat = location.split(",");
				if( lnglat != null && lnglat.length == 2)
				{
					float lng = NumberUtils.toFloat(lnglat[0], -1);
					float lat = NumberUtils.toFloat(lnglat[1], -1);
					
					if( lng > 0 && lat > 0 )
					{
						store.setLat(lat);
						store.setLon(lng);
					}
				}
			}

			
			// 邮编
			para = "alipay";
			store.setAlipay(request.getParameter(para));
			if( StringUtils.isEmpty(store.getAlipay()) )
			{
				errorFields.add(para);
			}
			// 邮编
			para = "qq";
			store.setQq(NumberUtils.toLong(request.getParameter(para)));
			if( store.getQq() < 10000 )
			{
				errorFields.add(para);
			}
			
			// 银行账户户名
			para = "accountName";
			account.setName(request.getParameter(para));
			if( StringUtils.isEmpty(store.getName()) )
			{
				errorFields.add(para);
			}
			// 账号
			para = "accountNumber";
			account.setNumber(request.getParameter(para));
			if( StringUtils.isEmpty(account.getNumber()) )
			{
				errorFields.add(para);
			}
			// 开户行
			para = "bank";
			account.setBank(request.getParameter(para));
			if( StringUtils.isEmpty(account.getBank()) )
			{
				errorFields.add(para);
			}
			
			if( request.getParameter("cainiao") != null )
			{
				StoreServiceAccount maowuAccount = new StoreServiceAccount();
				maowuAccount.setType(Service.CAINIAO.ID);
				maowuAccount.setAccount(request.getParameter("cainiao"));
				maowuAccount.setPassword(StringUtils.EMPTY);
				
				List<StoreServiceAccount> serviceAccount = new ArrayList<>();
				serviceAccount.add(maowuAccount);
				
				store.setServiceAccounts(serviceAccount);
			}

			
			if( errorFields.size() > 0 )
			{
				mav.addObject("error_fields", errorFields);
				mav.addObject("store", store);
			}
			else
			{
				store.setStatus(StoreStatus.APPROVAL);
				BaseResult result = storeManger.updateStore( store );
				if( result.isSuccess() )
				{
					long storeId = store.getId();
					saveFile(storeId, "idcard_a", multipartRequest);
					saveFile(storeId, "idcard_b", multipartRequest);
					saveFile(storeId, "license", multipartRequest);
					saveFile(storeId, "store_a", multipartRequest);
					saveFile(storeId, "store_b", multipartRequest);
					
					mav.addObject("store", store);
					mav.setViewName("/public/register_success");
				}
				else
				{
					mav.addObject("store", store);
					mav.addObject("error_msg", result.getMessage());
				}
			}
			
		}
		
		return mav;
	}
	
	private String saveFile(long storeId, String filecode, MultipartHttpServletRequest multipartRequest){
		
		MultipartFile file = multipartRequest.getFile(filecode);
		
		if( file == null || file.getSize() <= 0 )
		{
			return null;
		}
		
		String filename = null;
		String fullFilecode = "store.images.name." + filecode;
		if( ugjCfg.getValue(fullFilecode) != null )
		{
			filename = ugjCfg.getValue(fullFilecode).toString();
		}
		else
		{
			filename = filecode;
		}
		
		return saveFile(storeId, filename, file);
	}
	
	private String saveFile(long storeId, String filename, MultipartFile file){
		
		String path = ugjCfg.getValue("store.images.folder") + "/" + storeId;
		
		File folderScaning = new File(path);
		
		if( !folderScaning.exists() ){
			folderScaning.mkdirs();
		}
		
		File image = new File(path + "/" + filename + ".jpg");
		
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), image);
		} 
		catch (IOException e) {
			logger.error(e);
			return null;
		}
		
		return image.getAbsolutePath();
	}


	public void setStoreManger(StoreManger storeManger) {
		this.storeManger = storeManger;
	}
}

