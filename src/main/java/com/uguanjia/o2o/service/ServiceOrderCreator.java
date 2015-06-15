package com.uguanjia.o2o.service;

import com.uguanjia.o2o.Order;

/*******************************************
 * @CLASS:ServiceOrderCreator
 * @DESCRIPTION:服务订单生成	
 * @VERSION:v1.0
 * @DATE:2015年3月1日 下午11:21:54
 *******************************************/
public interface ServiceOrderCreator{
	
	/**
	 * @description:创建订单	
	 * @DATE:2015年3月1日 下午11:33:39
	 * @param order
	 * @return
	 */
	public ServiceOrderCreatorResult create(Order order, String strategy);
}

