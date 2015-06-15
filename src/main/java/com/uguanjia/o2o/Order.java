package com.uguanjia.o2o;

import java.util.Date;
import java.util.List;

import com.uguanjia.o2o.meta.OrderProcessActivity;


/*******************************************
 * @CLASS:Order
 * @VERSION:v1.0
 *******************************************/
public class Order {
	
	protected long id = -1;
	
	protected long store;
	
	protected int type;
	
	protected int status;
	
	protected Date createTime;
	
	protected List<OrderProcessActivity> processes;
	
	protected String customerName;
	
	protected String customerPhonenumber;
	
	protected String customerAddress;
	
	protected float revenue;
	
	protected float profit = 0;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getStore() {
		return store;
	}

	public void setStore(long store) {
		this.store = store;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhonenumber() {
		return customerPhonenumber;
	}

	public void setCustomerPhonenumber(String customerPhonenumber) {
		this.customerPhonenumber = customerPhonenumber;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getProfit() {
		return profit;
	}

	public void setProfit(float profit) {
		this.profit = profit;
	}

	public List<OrderProcessActivity> getProcesses() {
		return processes;
	}

	public void setProcesses(List<OrderProcessActivity> processes) {
		this.processes = processes;
	}

	public void copyOrderInfoFrom(Order other){
		if( other == null )return;
		this.setId(other.getId());
		this.setCreateTime(other.getCreateTime());
		this.setStore(other.getStore());
		this.setType(other.getType());
		this.setCustomerName(other.getCustomerName());
		this.setCustomerPhonenumber(other.getCustomerPhonenumber());
		this.setCustomerAddress(other.getCustomerAddress());
		this.setProcesses(other.getProcesses());
		this.setRevenue(other.getRevenue());
		this.setProfit(other.getProfit());
		this.setStatus(other.getStatus());
	}
}

