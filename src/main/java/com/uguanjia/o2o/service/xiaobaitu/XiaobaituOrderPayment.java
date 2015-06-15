package com.uguanjia.o2o.service.xiaobaitu;
/*******************************************
 * @CLASS:XiaobaituOrderPayment
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月1日 下午10:46:13
 *******************************************/
public class XiaobaituOrderPayment {
	
	
	public static String getPaymentName(int paymentCode){
		
		String paymentName = null;
		
		switch (paymentCode) {
		case 1:
			paymentName = "现金";
			break;
		case 2:
			paymentName = "洗衣券";
			break;
		case 3:
			paymentName = "洗衣卡号";
			break;
		default:
			paymentName = "";
			break;
		}
		
		return paymentName;
	}

}

