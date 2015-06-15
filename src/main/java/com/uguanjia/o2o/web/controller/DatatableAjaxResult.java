package com.uguanjia.o2o.web.controller;
/*******************************************
 * @CLASS:DatatableAjaxResult
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月23日 下午10:30:30
 *******************************************/
public class DatatableAjaxResult {
	
	private int iTotalRecords;
	
	private int iTotalDisplayRecords;
	
	private String sEcho;
	
	private String[][] aaData;

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String[][] getAaData() {
		return aaData;
	}

	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
}

