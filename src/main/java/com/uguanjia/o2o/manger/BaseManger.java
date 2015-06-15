package com.uguanjia.o2o.manger;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/*******************************************
 * @CLASS:BaseManger
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月3日 下午11:44:06
 *******************************************/
public class BaseManger implements ApplicationContextAware{
	
	protected final Logger logger = LogManager.getLogger(getClass());
	
	protected ApplicationContext context;
	
	protected PlatformTransactionManager txManager;
	
	protected SqlSessionFactory sqlSessionFactory;
	
	public TransactionStatus beginTransaction(String transactionName){
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(transactionName);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		return status;
	}
	
	public void rollbackTransaction(TransactionStatus status){
		this.txManager.rollback(status);
	}
	
	public void commitTransaction(TransactionStatus status){
		this.txManager.commit(status);
	}
	
	public PlatformTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.context = applicationContext;
		
	}

}

