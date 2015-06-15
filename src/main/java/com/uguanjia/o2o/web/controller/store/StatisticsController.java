package com.uguanjia.o2o.web.controller.store;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.Store;
import com.uguanjia.o2o.StoreServiceAccount;
import com.uguanjia.o2o.document.ExcelReportCreator;
import com.uguanjia.o2o.document.SheetHandler;
import com.uguanjia.o2o.manger.OperatorUtils;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.service.OrderStatistics;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.cainiao.CainiaoOrder;
import com.uguanjia.o2o.service.cainiao.CainiaoOrderManger;

/*******************************************
 * @CLASS:StatisticsController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月22日 下午8:36:59
 *******************************************/
@Controller("StoreStatisticsController")
@RequestMapping("/store/statistics")
public class StatisticsController {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	@Inject
	@Named("orderManger")
	private OrderManger orderManger;
	
	
	@Inject
	private CainiaoOrderManger cainiaoOrderManger;

	
	@RequestMapping("/cainiao")
	public ModelAndView queryOrderStatisticsCainiao(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/store/order_statistics");
		
		// 获取菜鸟账号
		Store store = OperatorUtils.getCurrentStore(request);
		List<StoreServiceAccount> accounts = store.getServiceAccounts();
		StoreServiceAccount cainiaoAccount = null;
		if( accounts != null && accounts.size() > 0 )
		{
			int cainiaoType = Service.CAINIAO.ID;
			for( StoreServiceAccount account : accounts )
			{
				if( account.getType() == cainiaoType )
				{
					cainiaoAccount = account;
					break;
				}
			}
		}
		
		if( cainiaoAccount == null )
		{
			mav.setViewName("/store/error");
			mav.addObject("error_msg", "您还没有菜鸟驿站的账号！");
		}
		else
		{
			long cainiaoCode = NumberUtils.toLong(cainiaoAccount.getAccount(), -1);
			if( cainiaoCode <= 0 )
			{
				mav.setViewName("/store/error");
				mav.addObject("error_msg", "您的菜鸟驿站账号不合法！");
			}
			else
			{
				QueryCondition condition = new QueryCondition();
				condition.setStoreId(cainiaoCode);
				condition.setType(Service.CAINIAO.ID);
				
				List<OrderStatistics> statistics = this.orderManger.queryOrderStatistics(condition);
				
				mav.addObject("statistics", statistics);
			}
		}
		
		return mav;
	}
	
	/**
	 * @description:下载统计明细（excel）	
	 * @DATE:2015年4月22日 下午8:43:00
	 */
	@RequestMapping("/cainiao/detail/{month}/download")
	public void downloadOrderStatisticsDetailCainiao(HttpServletRequest request
			,HttpServletResponse response ,@PathVariable final int month){
		
		// 获取菜鸟账号
		Store store = OperatorUtils.getCurrentStore(request);
		List<StoreServiceAccount> accounts = store.getServiceAccounts();
		StoreServiceAccount cainiaoAccount = null;
		if( accounts != null && accounts.size() > 0 )
		{
			int cainiaoType = Service.CAINIAO.ID;
			for( StoreServiceAccount account : accounts )
			{
				if( account.getType() == cainiaoType )
				{
					cainiaoAccount = account;
					break;
				}
			}
		}
		
		if( cainiaoAccount == null )
		{
			// TODO
		}
		else
		{
			long cainiaoCode = NumberUtils.toLong(cainiaoAccount.getAccount(), -1);
			if( cainiaoCode <= 0 )
			{
				// TODO
			}
			else
			{
				QueryCondition condition = new QueryCondition();
				condition.setStoreId(cainiaoCode);
				condition.setTime(month);
				
				final List<CainiaoOrder> orders = cainiaoOrderManger.queryOrders(condition);

				
				response.setContentType("application/msexcel");
				
				response.setHeader("Content-Disposition","attachment;filename=" 
						+ "cainiaoyz_" + month
						+ ".xls");
				
				ExcelReportCreator rc = new ExcelReportCreator();
				rc.setSheetHandler(new SheetHandler() {
					
					String[] headers = new String[]{
							"运单号",
							"物流公司id",
							"订单来源 ",
							"记录创建时间",
							"货物到站时间",
							"货物提货时间",
							"交易订单id",
							"金额",
							"备注"};
					
					@Override
					public void handle(Sheet sheet) {
						
						
						int rowIdx = 0;
						short columnIdx = 0;
						
						// Headers
						Row header = sheet.createRow(rowIdx++);
						for( String column : headers ){
							Cell cell = header.createCell(columnIdx++);
							cell.setCellValue(column);
						}
						
						if( orders == null || orders.size() == 0 )return;
						
						
						for( CainiaoOrder order : orders ){
							columnIdx = 0;
							Row row = sheet.createRow(rowIdx++);
							row.createCell(columnIdx++).setCellValue(String.valueOf(order.getOrderNumber()));
							row.createCell(columnIdx++).setCellValue(order.getLogisticsCompanyId());
							row.createCell(columnIdx++).setCellValue(order.getOrderSource());
							row.createCell(columnIdx++).setCellValue(order.getOrderCreateTime());
							row.createCell(columnIdx++).setCellValue(order.getOrderArriveTime());
							row.createCell(columnIdx++).setCellValue(order.getOrderTakeTime());
							row.createCell(columnIdx++).setCellValue(String.valueOf(order.getOrderId()));
							row.createCell(columnIdx++).setCellValue(order.getSum());
							row.createCell(columnIdx++).setCellValue(order.getComment());
						}
						
					}
					
					@Override
					public String getSheetName() {
						return String.valueOf(month);
					}
				});
				
				try(OutputStream os = response.getOutputStream())
				{
					rc.create(os);
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
		
	}

	public void setOrderManger(OrderManger orderManger) {
		this.orderManger = orderManger;
	}

	public void setCainiaoOrderManger(CainiaoOrderManger cainiaoOrderManger) {
		this.cainiaoOrderManger = cainiaoOrderManger;
	}
}

