package com.uguanjia.o2o.service.zhaopianchongxi;

import java.util.List;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:ZhaopianchongxiOrder
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月27日 上午11:20:58
 *******************************************/
public class ZhaopianchongxiOrder extends Order{
	
	private List<ZhaopianchongxiOrderItem> items;
	
	public ZhaopianchongxiOrder(){
		this.type = Service.ZHAOPIANCHONGXI.ID;
	}

	public List<ZhaopianchongxiOrderItem> getItems() {
		return items;
	}

	public void setItems(List<ZhaopianchongxiOrderItem> items) {
		this.items = items;
	}
}

