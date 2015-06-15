package com.uguanjia.o2o.web.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.uguanjia.o2o.meta.ContractStatus;

/*******************************************
 * @CLASS:OrderQueryDTO
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015 2 24
 *******************************************/
public class ContractQueryDTO {
	
	public static final int NONE_PAGE = -1;
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final int DEFAULT_PAGE_NO = 1;
	
	private int pageSize;
	
	private int pageNo;
	
	private int pages;
	
	private int total;
	
	private String dateFrom;
	
	private String dateTo;
	
	private int type;
	
	private int status = ContractStatus.APPLY;
	
	private String storeId;
	
	public void restore(){
		
		this.setPageNo(DEFAULT_PAGE_NO);
		this.setPageSize(DEFAULT_PAGE_SIZE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		setDateTo(sdf.format(today));
		calendar.add(Calendar.MONTH, -1);
		Date lastMonth = calendar.getTime();
		setDateFrom(sdf.format(lastMonth));
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
}

