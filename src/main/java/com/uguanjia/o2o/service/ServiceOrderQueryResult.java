package com.uguanjia.o2o.service;

import com.uguanjia.o2o.Order;

/*******************************************
 * @CLASS:ServiceContractQueryResult
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class ServiceOrderQueryResult extends BaseResult{
	
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}

