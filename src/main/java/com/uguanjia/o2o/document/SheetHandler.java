package com.uguanjia.o2o.document;

import org.apache.poi.ss.usermodel.Sheet;

/*******************************************
 * @CLASS:SheetHandler
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月25日 下午4:39:16
 *******************************************/
public interface SheetHandler {
	
	public String getSheetName();
	
	public void handle(Sheet sheet);

}

