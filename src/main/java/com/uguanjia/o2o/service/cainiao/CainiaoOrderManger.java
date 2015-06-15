package com.uguanjia.o2o.service.cainiao;

import java.io.File;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.uguanjia.o2o.config.Configration;
import com.uguanjia.o2o.dao.OrderCainiaoDao;
import com.uguanjia.o2o.manger.QueryCondition;

/*******************************************
 * @CLASS:CainiaoOrderManager
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月22日 下午8:49:18
 *******************************************/
public class CainiaoOrderManger{
	
	private final Logger logger = LogManager.getLogger(getClass());
	
	private SqlSessionFactory sqlSessionFactory;
	
	private Configration ugjCfg;
	
	/**
	 * @description:查询菜鸟订单	
	 * @DATE:2015年4月22日 下午9:15:30
	 * @param condition
	 * @return
	 */
	public List<CainiaoOrder> queryOrders(QueryCondition condition){
		
		List<CainiaoOrder> orders = null;
		
		try(SqlSession session = sqlSessionFactory.openSession() )
		{
			OrderCainiaoDao orderCainiaoDao = session.getMapper(OrderCainiaoDao.class);
			
			orders = orderCainiaoDao.queryOrders(condition);
		}
		catch (Exception e) 
		{
			this.logger.error(e);
		}

		return orders;
	}
	
	/**
	 * @description:查询统计信息	
	 * @DATE:2015年4月10日 上午10:01:59
	 * @param storeId
	 * @param type
	 * @return
	 */
	public File queryStatisticsFile(int time, int type){
		
		String filename = null;
		
		String fullFilecode = "statistics.excel.cainiao";
		
		if( ugjCfg.getValue(fullFilecode) != null )
		{
			filename = ugjCfg.getValue(fullFilecode).toString();
		}
		else
		{
			filename = "";
		}
		
		
		String path = filename + "/" + time + ".xlsx";
		
		File file = new File(path);
		
		return file;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setUgjCfg(Configration ugjCfg) {
		this.ugjCfg = ugjCfg;
	}
}

