package com.uguanjia.o2o.meta;
/*******************************************
 * @CLASS:ContractStatus
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月13日 下午6:07:55
 *******************************************/
public class ContractStatus {
	public static final int APPLY = 1;
	public static final int ACCEPT = 2;
	public static final int REJECT = 3;
	
	public static String getName(int status){
		
		String name = "未知状态";
		
		switch (status) {
		case APPLY:
			name = "申请";
			break;
			
		case ACCEPT:
			name = "接受";
			break;
			
		case REJECT:
			name = "拒绝";
			break;

		default:
			name = "未知状态";
			break;
		}
		
		return name;
	}
}

