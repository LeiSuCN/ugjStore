package com.uguanjia.o2o.service.xiaobaitu;

import java.util.HashMap;
import java.util.Map;

/*******************************************
 * @CLASS:XbtServiceItem
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class XbtServiceItem {
	
	private int id;
	
	private String name;

	private int category;
	
	private int price;
	
	private final Map<Integer, XbtServiceItem> items = 
			new HashMap<Integer, XbtServiceItem>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Map<Integer, XbtServiceItem> getItems() {
		return items;
	}
}

