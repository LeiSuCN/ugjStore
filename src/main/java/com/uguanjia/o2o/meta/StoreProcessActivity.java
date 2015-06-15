package com.uguanjia.o2o.meta;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/*******************************************
 * @CLASS:OrderProcess
 * @VERSION:v1.0
 *******************************************/
public class StoreProcessActivity {
	
	private long storeId;
	
	private Date time;
	
	private String operator;
	
	private int status;
	
	private String comment = StringUtils.EMPTY;

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

