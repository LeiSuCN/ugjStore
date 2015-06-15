package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.manger.QueryCondition;
import com.uguanjia.o2o.service.cainiao.CainiaoOrder;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public interface OrderCainiaoDao {
	
	public List<CainiaoOrder> queryOrders(QueryCondition condition);
}

