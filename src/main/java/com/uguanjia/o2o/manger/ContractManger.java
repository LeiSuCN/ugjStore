package com.uguanjia.o2o.manger;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.TransactionStatus;

import com.uguanjia.o2o.Contract;
import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.dao.ContractDao;
import com.uguanjia.o2o.service.BaseResult;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:ContractManger
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月13日 下午5:48:12
 *******************************************/
public class ContractManger extends BaseManger{
	
	private Configration ugjCfg;
	
	public List<Contract> queryContracts(QueryCondition condition){
		
		logger.info("begin to query contracts ");
		
		List<Contract> contracts = null;
		
		try (SqlSession session = sqlSessionFactory.openSession())
		{
			ContractDao contractDao = session.getMapper(ContractDao.class);
			
			int total = contractDao.queryContractsCount(condition);
			
			contracts = contractDao.queryContracts(condition);
			
			condition.setTotal(total);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("success to query contracts");
		return contracts;
	}
	
	/**
	 * @description:创建合同	
	 * @DATE:2015年3月3日 下午11:40:44
	 * @param contract
	 */
	public void createContract( Contract contract ){
		
		if( contract == null ){
			logger.warn("contract is null");
			return;
		}

		if( logger.isInfoEnabled() ){
			logger.info("begin to create " + contract.getType() 
					+ " contract for " + contract.getStoreId() );
		}
		
		BaseResult result = new BaseResult();
		
		// ---- ----> 开始事务
		TransactionStatus status = beginTransaction("createContract");
		
		try(SqlSession session = sqlSessionFactory.openSession()) 
		{
			
			Service service = Service.getById(contract.getType());
			
			if( service != null ){
				
				setDefaultStrategy(contract);
				
				ContractDao contractDao = session.getMapper(ContractDao.class);
				
				int rowCount = contractDao.insertContract(contract);
				
				if( rowCount < 1 )
				{
					result.setFailureMsg("创建申请失败！");
				} 
				else
				{
					result.setSuccessMsg("申请成功");
				}
				
			} else{
				logger.error("找不到编码为" + contract.getType() + "的服务！" );
			}
			
		} catch (Exception e) {
			logger.error(e);
			result.setFailureMsg("创建申请失败！");
		}
		
		// 结果为空或者结果不成功，需要回滚事务
		if( !result.isSuccess() ){
			// ---- ----> 回滚事务
			rollbackTransaction(status);
			logger.error("failure to create contract");
		} 
		// 否则提交事务
		else {
			// ---- ----> 提交事务
			commitTransaction(status);
			if( logger.isInfoEnabled() ){
				logger.info("success to create " + contract.getType() 
						+ " contract for " + contract.getStoreId() );
			}
		} 
	}
	
	/**
	 * @description:更新合同状态	
	 * @DATE:2015年3月9日 上午1:15:42
	 * @param contractId
	 * @param status
	 * @param comment
	 */
	public BaseResult changeContractStatus(int contractId, int status, String comment){
		
		BaseResult result = new BaseResult();
		
		if( logger.isInfoEnabled() ){
			logger.info("begin change the status of contract " + contractId
					+ " to " + status);
		}
		
		// ---- ----> 开始事务
		TransactionStatus transactionStatus = beginTransaction("createContract");
		
		try(SqlSession session = sqlSessionFactory.openSession()) {
			
			ContractDao contractDao = session.getMapper(ContractDao.class);
			contractDao.changeContractStatus(contractId, status, comment);
			
		} catch (Exception e) {
			logger.error(e);
			result.setFailureMsg(e.getMessage());
		}
		
		if( result.isSuccess() ){
			commitTransaction(transactionStatus);
			if( logger.isInfoEnabled() ){
				logger.info("success change the status of contract " + contractId
						+ " to " + status);
			}
		} 
		else{
			rollbackTransaction(transactionStatus);
			logger.error("error change the status of contract " + contractId
					+ " to " + status);
		}

		return result;
	}
	
	public File queryContractHtmlTemplate(String serviceCode){
		
		File template = null;
		
		Object html = ugjCfg.getValue("contract.template.folder");
		
		if( html != null )
		{
			template = new File(html.toString() + "/" + serviceCode + ".html");
		}

		return template;
	}
	
	private void setDefaultStrategy(Contract contract){
		
		Service service = Service.getById(contract.getType() );
		if( service == null )
		{
			contract.setStrategy("-1");
		}
		else
		{
			Object strategy = this.ugjCfg.getValue("contract." + service.CODE + ".strategy");
			
			if( strategy != null )
			{
				contract.setStrategy(strategy.toString());
			}
			else
			{
				contract.setStrategy("-1");
			}
		}
	}

	public void setUgjCfg(Configration ugjCfg) {
		this.ugjCfg = ugjCfg;
	}
}

