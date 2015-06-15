package com.uguanjia.o2o.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.uguanjia.o2o.Order;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrderItem;
import com.uguanjia.o2o.service.xiaobaitu.XiaobaituOrder;

/*******************************************
 * @CLASS:XbtOrderDTO
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class XbtOrderDTO implements OrderDTO{
	
	private String customerName;
	
	private String customerPhonenumber;
	
	private String customerAddress;
	
	private int payment;
	
	private String pack;
	
	private String voucher;
	
	private List<XbtOrderItemDTO> items;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhonenumber() {
		return customerPhonenumber;
	}

	public void setCustomerPhonenumber(String customerPhone) {
		this.customerPhonenumber = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
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

	public List<XbtOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<XbtOrderItemDTO> items) {
		this.items = items;
	}

	@Override
	public Order toOrder() {
		
		XiaobaituOrder order = new XiaobaituOrder();
		XbtOrderDTO orderDto = this;
		
		order.setCustomerName(orderDto.getCustomerName());
		order.setCustomerPhonenumber(orderDto.getCustomerPhonenumber());
		order.setCustomerAddress(orderDto.getCustomerAddress());
		order.setPayment(orderDto.getPayment());
		order.setVoucher(orderDto.getVoucher());
		order.setPack(orderDto.getPack());
		List<XbtOrderItemDTO> orderItemDtos = orderDto.getItems();
		
		if( orderItemDtos != null ){
			
			if( order.getItems() == null ){
				order.setItems(new ArrayList<XiaobaituOrderItem>(orderItemDtos.size()));
			}
			
			List<XiaobaituOrderItem> items = order.getItems();
			
			for( XbtOrderItemDTO orderItemDto: orderItemDtos){
				XiaobaituOrderItem orderItem = new XiaobaituOrderItem();
				orderItem.setName(orderItemDto.getName());
				orderItem.setAmount(orderItemDto.getCount());
				items.add(orderItem);
			}
		}
		
		return order;
	}
}

