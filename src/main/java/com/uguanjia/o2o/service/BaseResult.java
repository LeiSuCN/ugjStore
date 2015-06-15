package com.uguanjia.o2o.service;


/*******************************************
 * @CLASS:BaseResult
 * @VERSION:v1.0
 * @DATE:2015年3月1日 下午11:37:24
 *******************************************/
public class BaseResult {
	
	public static final int CODE_SUCCESS = 0;
	public static final int CODE_FAILURE = -1;
	
	private int code = CODE_SUCCESS;
	
	private String message;

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
	
	public void setSuccessMsg(String msg){
		this.setCodeAndMessage(CODE_SUCCESS, msg);
	}
	
	public void setFailureMsg(String msg){
		this.setCodeAndMessage(CODE_FAILURE, msg);
	}
	
	public boolean isSuccess(){
		return this.code == CODE_SUCCESS;
	}
}

