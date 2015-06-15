package com.uguanjia.o2o.dao;

import java.util.List;

import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrder;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiOrderItem;
import com.uguanjia.o2o.service.zhaopianchongxi.ZhaopianchongxiServiceItem;

/*******************************************
 * @CLASS:OrderDao
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @Date
 *******************************************/
public interface OrderZhaopianchongxiDao {

	public int createOrderItems(ZhaopianchongxiOrder order);
	
	public List<ZhaopianchongxiOrderItem> queryOrderItemsById(long id);
	
	public List<ZhaopianchongxiServiceItem> queryServiceItems();
}

