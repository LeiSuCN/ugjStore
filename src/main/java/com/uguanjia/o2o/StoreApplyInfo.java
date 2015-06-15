package com.uguanjia.o2o;

import java.util.Date;

/*******************************************
 * @CLASS:StoreApplyInfo
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月8日 上午8:20:19
 *******************************************/
public class StoreApplyInfo {
	
	private int id;
	
	private long storeId;
	
	private int status;
	
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

