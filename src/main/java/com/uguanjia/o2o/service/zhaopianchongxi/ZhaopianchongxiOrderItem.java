package com.uguanjia.o2o.service.zhaopianchongxi;
/*******************************************
 * @CLASS:ZhaopianchongxiOrderItem
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月27日 上午11:23:57
 *******************************************/
public class ZhaopianchongxiOrderItem {
	
	private String size;
	
	private String name;
	
	private float price;
	
	private int amount;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

