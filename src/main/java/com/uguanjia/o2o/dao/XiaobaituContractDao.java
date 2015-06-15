package com.uguanjia.o2o.dao;

import com.uguanjia.o2o.service.xiaobaitu.XiaobaituContract;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年1月26日 下午11:09:28
 *******************************************/
public interface XiaobaituContractDao {

	public XiaobaituContract queryContractById(int contractId);
	
	public int createContract(XiaobaituContract contract);
}

