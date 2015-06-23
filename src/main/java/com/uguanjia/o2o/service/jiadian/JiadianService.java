package com.uguanjia.o2o.service.jiadian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.uguanjia.o2o.service.jiadian.dao.JiadianDao;

/*******************************************
 * @DESCRIPTION: 
 * @AUTHOR:sulei
 * @VERSION:v1.0
 * @DATE:2015年6月22日
 *******************************************/
public class JiadianService
{
    private final Logger logger = LogManager.getLogger(getClass());
    
    @Inject
    @Named("sqlSessionFactoryBean")
    private SqlSessionFactory sqlSessionFactory;
    
    /**
     * @DESCRIPTION: 查询家电清洁全部的服务项目
     * @AUTHOR:sulei
     * @DATE:2015年6月22日
     * @return
     */
    public List<ServiceItem> queryServiceItems(){
        
        assert sqlSessionFactory != null;
        //
        SqlSession session = sqlSessionFactory.openSession();
        JiadianDao serviceDao = session.getMapper(JiadianDao.class);
        //
        List<ServiceItem> items = serviceDao.queryAllServiceItems();
        if( items == null ){
            logger.error("家电清洁项目为null");
            return null;
        }
        // 聚合服务类目
        List<ServiceItem> categoryItems = new ArrayList<ServiceItem>();
        Map<Integer, ServiceItem> dict = new HashMap<>();
        for( ServiceItem item : items ){ // 已经安装id升序排列
            int categoryId = item.getCategory();
            if( categoryId == 0 ){
                item.setSub(new ArrayList<ServiceItem>());
                categoryItems.add(item);
                dict.put(categoryId, item);
            } else{
                ServiceItem category = dict.get(categoryId);
                category.getSub().add(item);
            }
        }
        dict.clear();
        
        return categoryItems;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory)
    {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}

