package com.uguanjia.o2o.service.cainiao;
/*******************************************
 * @CLASS:CainiaoOrder
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月22日 下午8:52:20
 *******************************************/
public class CainiaoOrder {

	private int time;
	
	private int  code;
	
	/* 服务站订单编码 */
	private long storeOrderNo;
	
	/* 服务站名称  */
	private String storeName;
	
	/* 运单号 */
	private long orderNumber;
	
	/* 物流公司id */
	private int logisticsCompanyId;
	
	/* 订单来源 */
	private int orderSource;
	
	/* 记录创建时间 */
	private String orderCreateTime;
	
	/* 货物到站时间 */
	private String orderArriveTime;
	
	/* 货物提货时间 */
	private String orderTakeTime;
	
	/* 交易订单id */
	private long orderId;
	
	/* 金额 */
	private float sum;
	
	/* 备注 */
	private String comment;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getStoreOrderNo() {
		return storeOrderNo;
	}

	public void setStoreOrderNo(long storeOrderNo) {
		this.storeOrderNo = storeOrderNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getLogisticsCompanyId() {
		return logisticsCompanyId;
	}

	public void setLogisticsCompanyId(int logisticsCompanyId) {
		this.logisticsCompanyId = logisticsCompanyId;
	}

	public int getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(int orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderArriveTime() {
		return orderArriveTime;
	}

	public void setOrderArriveTime(String orderArriveTime) {
		this.orderArriveTime = orderArriveTime;
	}

	public String getOrderTakeTime() {
		return orderTakeTime;
	}

	public void setOrderTakeTime(String orderTakeTime) {
		this.orderTakeTime = orderTakeTime;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

