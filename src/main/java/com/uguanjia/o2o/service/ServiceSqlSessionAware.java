package com.uguanjia.o2o.service;

import org.apache.ibatis.session.SqlSession;

/*******************************************
 * @CLASS:ServiceSqlSessionAwareCreator
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月3日 下午11:29:41
 *******************************************/
public interface ServiceSqlSessionAware {
	
	/**
	 * @description:设置session	
	 * @DATE:2015年3月1日 下午11:33:47
	 * @param session
	 */
	public void setSession( SqlSession session );
}

