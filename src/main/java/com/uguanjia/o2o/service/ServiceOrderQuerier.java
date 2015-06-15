package com.uguanjia.o2o.service;

import com.uguanjia.o2o.Order;

/*******************************************
 * @CLASS:ServiceContractCreator
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月3日 下午11:26:32
 *******************************************/
public interface ServiceOrderQuerier{
	
	public ServiceOrderQueryResult query(Order order);

}

