package com.uguanjia.o2o.service.xiaobaitu;

import java.util.HashMap;
import java.util.Map;

/*******************************************
 * @CLASS:XbtService
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class XbtService {
	
	private final Map<Integer, XbtServiceItem> items = 
			new HashMap<Integer, XbtServiceItem>();

	public Map<Integer, XbtServiceItem> getItems() {
		return items;
	}
}

