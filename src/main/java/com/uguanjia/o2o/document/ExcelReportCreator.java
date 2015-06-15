package com.uguanjia.o2o.document;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/*******************************************
 * @CLASS:ExcelReportCreator
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月25日 下午4:15:51
 *******************************************/
public class ExcelReportCreator implements ReportCreator{
	
	private final Logger logger = LogManager.getLogger(getClass());
	
	private SheetHandler sheetHandler;
	
	public String create(OutputStream os){
		
		if( sheetHandler == null ){
			logger.warn("sheetHandler is null");
			return null;
		}
		
		Workbook wb = new HSSFWorkbook();
		try {
			
			Sheet sheet = wb.createSheet(sheetHandler.getSheetName());
			
			sheetHandler.handle(sheet);
			
			wb.write(os);
			
		} catch (IOException e) {
			logger.error(e);
		}
		
		return sheetHandler.getSheetName();
	}

	public void setSheetHandler(SheetHandler sheetHandler) {
		this.sheetHandler = sheetHandler;
	}
}

