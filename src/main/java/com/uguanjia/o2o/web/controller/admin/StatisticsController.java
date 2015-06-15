package com.uguanjia.o2o.web.controller.admin;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uguanjia.o2o.document.ExcelReportCreator;
import com.uguanjia.o2o.document.SheetHandler;
import com.uguanjia.o2o.manger.OrderManger;
import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.service.OrderStatistics;
import com.uguanjia.o2o.service.Service;
import com.uguanjia.o2o.service.cainiao.CainiaoOrder;
import com.uguanjia.o2o.service.cainiao.CainiaoOrderManger;
import com.uguanjia.o2o.web.controller.DatatableAjaxResult;

/*******************************************
 * @CLASS:StatisticsController
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月22日 下午8:36:59
 *******************************************/
@Controller("AdminStatisticsController")
@RequestMapping("/admin/statistics")
public class StatisticsController {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	@Inject
	@Named("orderManger")
	private OrderManger orderManger;
	
	
	@Inject
	private CainiaoOrderManger cainiaoOrderManger;

	
	@RequestMapping("/cainiao")
	public ModelAndView queryOrderStatisticsCainiao(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/admin/order_statistics");
		
		int code = NumberUtils.toInt(request.getParameter("code"), -1);
		
		if( code > 0 )
		{
			mav.addObject("code", code);
		}
		
		return mav;
	}
	

	
	@RequestMapping("/cainiao/a")
	@ResponseBody
	public DatatableAjaxResult queryOrderStatisticsCainiaoAjax(HttpServletRequest request){
		
		DatatableAjaxResult ajaxResult = new DatatableAjaxResult();
		
		// page no.
		int pageNo = NumberUtils.toInt(request.getParameter("sEcho"), 1);
		
		int pageSize = NumberUtils.toInt(request.getParameter("iDisplayLength"), 10);
		
		int month = 201503;
		
		int code = NumberUtils.toInt(request.getParameter("code"), -1);
		
		QueryCondition condition = new QueryCondition();
		condition.setTime(month);
		condition.setType(Service.CAINIAO.ID);
		condition.setPageNo(pageNo);
		condition.setPageSize(pageSize);
		if( code > 0 )
		{
			condition.setStoreId(code);
		}
		
		List<OrderStatistics> statistics = this.orderManger.queryOrderStatistics(condition);
		
		ajaxResult.setiTotalRecords(condition.getTotal());
		ajaxResult.setiTotalDisplayRecords(condition.getTotal());
		
		
		String[][] data = null;
		
		if( statistics != null && statistics.size() > 0 )
		{
			int size = statistics.size();
			data = new String[size][5];
			
			String root = request.getContextPath();
			
			for( int i = 0 ; i < size ; i++ )
			{
				OrderStatistics s = statistics.get(i);
				
				// 月份
				data[i][0] = String.valueOf(s.getTime());
				// 菜鸟编号
				data[i][1] = String.valueOf(s.getStoreId());
				// 数量
				data[i][2] = String.valueOf(s.getAmount());
				// 总计
				data[i][3] = String.valueOf(s.getTotal());
				//
				StringBuilder a = new StringBuilder("<div class='hidden-sm hidden-xs action-buttons'><a class='blue' href='") ;
				a.append(root).append("/admin/statistics/cainiao/").append(month).append("/").append(s.getStoreId()).append("/download");
				a.append("' title='下载' target='_blank'><i class='ace-icon fa fa-cloud-download bigger-130'></i></a></div>");
				data[i][4] = a.toString();
			}
		}
		else
		{
			data = new String[0][5];
		}

		ajaxResult.setsEcho(String.valueOf(pageNo));
		ajaxResult.setAaData(data);
		
		return ajaxResult;
	}
	
	/**
	 * @description:下载统计明细（excel）	
	 * @DATE:2015年4月22日 下午8:43:00
	 */
	@RequestMapping("/cainiao/{month}/{code}/download")
	public void downloadOrderStatisticsDetailCainiao(HttpServletRequest request
			,HttpServletResponse response ,@PathVariable final int month
			,@PathVariable final int code){
		
		QueryCondition condition = new QueryCondition();
		condition.setStoreId(code);
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
	
	
	/**
	 * @description:下载统计明细（excel）	
	 * @DATE:2015年4月22日 下午8:43:00
	 */
	@RequestMapping("/cainiao/{month}/download")
	public void downloadOrderStatisticsCainiao(HttpServletRequest request
			,HttpServletResponse response ,@PathVariable int month){
		
		File excel = this.cainiaoOrderManger.queryStatisticsFile(month, Service.CAINIAO.ID);
		
		if( !excel.exists() || excel.isDirectory() ){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		} 
		else{
			response.setContentType("application/msexcel");
			
			response.setHeader("Content-Disposition","attachment;filename=" 
					+ "cainiaoyz_" + month
					+ ".xlsx");
		}
		
		try (FileInputStream fis = new FileInputStream(excel)){
			int len = 0;
	        
			byte buffer[]=new byte[1024];

			OutputStream out = response.getOutputStream();
			
			while((len = fis.read(buffer))> 0 ){
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOrderManger(OrderManger orderManger) {
		this.orderManger = orderManger;
	}

	public void setCainiaoOrderManger(CainiaoOrderManger cainiaoOrderManger) {
		this.cainiaoOrderManger = cainiaoOrderManger;
	}
}

