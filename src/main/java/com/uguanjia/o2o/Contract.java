package com.uguanjia.o2o;

import java.util.Date;

import com.uguanjia.o2o.meta.ContractStatus;

/*******************************************
 * @CLASS:Contract
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月14日 上午9:38:38
 *******************************************/
public class Contract {
	private int id;
	
	private long storeId;

	private Date time;
	
	private int type;
	
	private int status = ContractStatus.APPLY;
	
	private String strategy;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
}

