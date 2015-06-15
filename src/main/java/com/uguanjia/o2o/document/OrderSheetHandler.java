package com.uguanjia.o2o.document;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.uguanjia.o2o.Order;

/*******************************************
 * @CLASS:OrderSheetHandler
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月25日 下午4:49:48
 *******************************************/
public class OrderSheetHandler implements SheetHandler{
	
	private String sheetName = "订单详情";
	
	private String[] headers = new String[]{"订单号",
			"门店编号",
			"创建时间",
			"订单状态",
			"客户姓名",
			"订单收入"};
	
	private List<Order> orders;

	@Override
	public String getSheetName() {
		return this.sheetName;
	}

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
		
		if( this.orders == null || this.orders.size() == 0 )return;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		for( Order order : orders ){
			columnIdx = 0;
			Row row = sheet.createRow(rowIdx++);
			row.createCell(columnIdx++).setCellValue(order.getId());
			row.createCell(columnIdx++).setCellValue(order.getStore());
			row.createCell(columnIdx++).setCellValue(sdf.format(order.getCreateTime()));
			row.createCell(columnIdx++).setCellValue(order.getStatus());
			row.createCell(columnIdx++).setCellValue(order.getCustomerName());
			row.createCell(columnIdx++).setCellValue(order.getProfit());
		}
		
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}

