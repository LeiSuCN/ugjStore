package com.uguanjia.o2o.dao;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/*******************************************
 * @DESCRIPTION: 封装事务
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月22日
 *******************************************/
public class UTransaction
{
    private PlatformTransactionManager txManager;
    
    private TransactionStatus status;
    
    private String name;
    
    public UTransaction(PlatformTransactionManager txManager, String name)
    {
        this.txManager = txManager;
        this.name = name;
    }
    
    public void begin(){
        TransactionDefinition def = createDefinition();
        this.status = txManager.getTransaction(def);
    }
    
    public void rollback(){
        this.txManager.rollback(status);
    }
    
    public void commit(){
        this.txManager.commit(status);
    }
    
    private TransactionDefinition createDefinition(){
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(name);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return def;
    }

}

