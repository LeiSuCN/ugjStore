package com.uguanjia.o2o.service.zhaopianchongxi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.dao.OrderZhaopianchongxiDao;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrder;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrderItem;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiServiceItem;
import com.uguanjia.o2o.web.controller.CommonOrderController;
import com.uguanjia.o2o.web.controller.RequestValue;
import com.uguanjia.o2o.web.controller.ServiceOrderController;

/*******************************************
 * @CLASS:XiaobaituServiceOrderController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015��3��2�� ����10:34:11
 *******************************************/
@Controller
@RequestMapping("/order/zhaopianchongxi")
public class ZhaopianchongxiServiceOrderController implements ServiceOrderController{
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private Service service = Service.ZHAOPIANCHONGXI;
	
	@Inject
	@Named("commonOrderController")
	private ServiceOrderController parent;
	
	
	@Inject
	@Named("ugj_cfg")
	Configration ugjCfg;

	@Override
	public void setParent(ServiceOrderController parent) {
		this.parent = parent;
	}

	@Override
	@RequestMapping("/detail/{orderId}")
	public ModelAndView queryOrderDetail(HttpServletRequest request, @PathVariable long orderId) {
		
		ModelAndView mav = this.parent.queryOrderDetail(request, orderId);
		
		mav.setViewName("/" + service.CODE + "/order_detail");
		
		return mav;
	}

	@Override
	@RequestMapping(value="/place")
	public ModelAndView placeOrder(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("/" + service.CODE + "/order");
		
		// 
		List<ZhaopianchongxiServiceItem> items = null;
		
		OrderManger orderManger = ((CommonOrderController)this.parent).getOrderManger();
		
		try (SqlSession sqlSession = orderManger.getSqlSessionFactory().openSession()){
			OrderZhaopianchongxiDao dao = 
					sqlSession.getMapper(OrderZhaopianchongxiDao.class);
			
			items = dao.queryServiceItems();
		} 
		catch (Exception e)
		{
			logger.error(e);
		}
		
		if( items == null )
			items = new ArrayList<>();
		
		mav.addObject("serviceItems", items);
		return mav;
	}

	@RequestMapping(value="", method=RequestMethod.POST)
	@Override
	public ModelAndView createOrder(HttpServletRequest request
			, Order order) {
		
		ModelAndView mav = new ModelAndView();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		try {
			ZhaopianchongxiOrder zhaopianchongxiOrder = new ZhaopianchongxiOrder();
			
			// 客户姓名
			String customerName = request.getParameter("customerName");
			
			// 客户电话
			String customerPhonenumber = request.getParameter("customerPhone");
			
			// 客户地址
			String customerAddress = request.getParameter("customerAddress");
			
			// 订单明细：名称
			String[] itemNames = request.getParameterValues("item_name");
			// 订单明细：名称
			String[] itemSizes = request.getParameterValues("item_size");
			// 订单明细：数量
			String[] itemCounts = request.getParameterValues("item_count");
			
			zhaopianchongxiOrder.setCustomerName(customerName);
			zhaopianchongxiOrder.setCustomerPhonenumber(customerPhonenumber);
			zhaopianchongxiOrder.setCustomerAddress(customerAddress);
			if( itemNames != null && itemNames.length > 0 
					&& itemCounts != null && itemCounts.length > 0
					&& itemSizes != null && itemSizes.length > 0)
			{
				int len = Math.min(itemNames.length, itemCounts.length);
				len = Math.min(len, itemSizes.length);
				List<ZhaopianchongxiOrderItem> items = new ArrayList<>(len);
				for( int i = 0 ; i < len ; i++ )
				{
					String itemName = itemNames[i];
					String itemCount = itemCounts[i];
					String itemSize = itemSizes[i];
					ZhaopianchongxiOrderItem item = new ZhaopianchongxiOrderItem();
					item.setName(itemName);
					item.setSize(itemSize);
					item.setAmount(NumberUtils.toInt(itemCount, 0));
					items.add(item);
				}
				zhaopianchongxiOrder.setItems(items);
			}
			
			mav.addObject(RequestValue.ORDER, zhaopianchongxiOrder);
			
			// 保存文件
			MultipartFile file = multipartRequest.getFile("imgRar");
			
			if( file == null || file.getSize() <= 0 )
			{
				mav.addObject(RequestValue.ERROR_MSG, "压缩包为空！");
			}
			else
			{
				mav = this.parent.createOrder(request, zhaopianchongxiOrder);
				
				long orderId = zhaopianchongxiOrder.getId();
				
				String path = ugjCfg.getValue("service.zhaopianchongxi.rar") + "/" + orderId + ".rar";
				
				File rar = new File(path);
				
				FileUtils.copyInputStreamToFile(file.getInputStream(), rar);
				
				mav.setViewName("redirect:/store/order/detail/" + orderId);
			}
		} 
		catch (Exception e) {
			logger.error(e);

			mav.addObject(RequestValue.ERROR_MSG, "数据不合法！");
		}
		
		if( mav.getModel().get(RequestValue.ERROR_MSG) != null )
		{
			mav.setViewName("forward:/order/zhaopianchongxi/place");
		}
		
		return mav;
	}
	
	/**
	 * @description:下载订单照片文件	
	 * @DATE:2015年5月1日 上午11:09:16
	 * @param request
	 * @param resp
	 * @param orderId
	 */
	@RequestMapping("/{orderId}/photos")
	public void downloadRar(HttpServletRequest request,HttpServletResponse resp
			, @PathVariable long orderId){
		
		File rar = new File(ugjCfg.getValue("service.zhaopianchongxi.rar") + "/" + orderId + ".rar");
		
		if( !rar.exists() || !rar.isFile() )
		{
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else
		{
			try 
			{
				resp.setContentType("application/octet-stream");
				
				resp.setHeader("Content-Disposition","attachment;filename=" 
						+ orderId
						+ ".rar");
				
				FileUtils.copyFile(rar, resp.getOutputStream());
			} 
			catch (IOException e)
			{
				logger.error(e);
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
}

