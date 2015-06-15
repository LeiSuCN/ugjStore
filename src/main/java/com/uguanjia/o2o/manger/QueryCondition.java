package com.uguanjia.o2o.manger;

import java.util.Date;

/*******************************************
 * @CLASS:QueryCondition
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月24日 下午12:42:15
 *******************************************/
public class QueryCondition {
	
	public static final int DEFAULT_PAGE_NO = 1;
	
	public static final int DEFAULT_PAGE_SIZE = 20;
	
	private long orderId;
	
	private long storeId = 0;
	
	private int contractId = 0;
	
	private int pageSize = 0;
	
	private int pageNo = 0;
	
	private int total;
	
	private int type;
	
	private int status;
	
	private int time;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private int limit;
	
	public void limit(){
		
		if( this.pageNo < 1 )
			return;
		
		if( this.pageSize < 1 )
			return;
		
		this.limit = ( pageNo - 1 ) * pageSize;
	}
	

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		limit();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		limit();
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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


	public int getContractId() {
		return contractId;
	}


	public void setContractId(int contractId) {
		this.contractId = contractId;
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}
}

