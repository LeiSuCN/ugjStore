package com.uguanjia.o2o.service;
/*******************************************
 * @CLASS:OrderStatistics
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月21日 下午10:43:19
 *******************************************/
public class OrderStatistics {
	
	private long storeId;
	
	private int type;
	
	private int time;
	
	private int amount;
	
	private int total;

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}

