package com.uguanjia.o2o.service.xiaobaitu;

import java.util.List;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:XbtOrder
 * @DESCRIPTION:小白兔订单	
 * @VERSION:v1.0
 * @DATE:2015-01-27 09:00:56
 *******************************************/
public class XiaobaituOrder extends Order{
	
	private String pack;
	
	private int payment;
	
	private String voucher;
	
	private List<XiaobaituOrderItem> items;
	
	public XiaobaituOrder() {
		this.type = Service.XIAOBAITU.ID;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public List<XiaobaituOrderItem> getItems() {
		return items;
	}

	public void setItems(List<XiaobaituOrderItem> items) {
		this.items = items;
	}
}

