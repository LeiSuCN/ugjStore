package com.uguanjia.o2o.meta;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/*******************************************
 * @CLASS:OrderProcess
 * @VERSION:v1.0
 *******************************************/
public class OrderProcessActivity {
	
	private long id;
	
	private String operator;
	
	private Date time;
	
	private String status;
	
	private String comment = StringUtils.EMPTY;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}

