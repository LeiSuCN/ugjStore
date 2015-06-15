package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrderItem;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrder;
import com.uguanjia.o2o.service.xiaobaitu.XbtServiceItem;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public interface OrderXiaobaituDao {

	public int insertOrderXbt(XiaobaituOrder order);
	
	public int insertOrderXbtItems(XiaobaituOrder order);
	
	public XiaobaituOrder queryOrderById(long id);
	
	public List<XiaobaituOrderItem> queryItemsById(long id);
	
	public List<XbtServiceItem> queryAllServiceItems();
}

