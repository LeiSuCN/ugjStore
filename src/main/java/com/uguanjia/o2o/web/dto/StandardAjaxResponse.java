package com.uguanjia.o2o.web.dto;

import java.util.List;

/*******************************************
 * @CLASS:StandardAjaxResponse
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class StandardAjaxResponse<T> {
	
	private int code;
	
	private String message;
	
	private List<T> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setCodeAndMessage(int code, String message){
		this.setCode(code);
		this.setMessage(message);
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}

