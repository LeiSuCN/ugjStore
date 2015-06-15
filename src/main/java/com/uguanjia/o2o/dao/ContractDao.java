package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.manger.QueryCondition;


/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public interface ContractDao {
	
	public int insertContract( Contract Contract);
	
	public int queryContractsCount(QueryCondition condition);
	
	public Contract queryContract(QueryCondition condition);
	
	public List<Contract> queryContracts(QueryCondition condition);
	
	public int changeContractStatus(int contractId, int status, String comment);
}

