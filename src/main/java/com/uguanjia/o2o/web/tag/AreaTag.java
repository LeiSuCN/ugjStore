package com.uguanjia.o2o.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/*******************************************
 * @CLASS:ProcessTag
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月24日 上午1:04:25
 *******************************************/
public class AreaTag extends SimpleTagSupport{

	private int area;

	@Override
	public void doTag() throws JspException, IOException {
		
		String text = null;
		
		switch (area) {
		case 440301:
			text = "广东省深圳市宝安区";
			break;
		case 440302:
			text = "广东省深圳市南山区";
			break;
		case 440303:
			text = "广东省深圳市福田区";
			break;
		case 440304:
			text = "广东省深圳市龙岗区";
			break;
		case 440305:
			text = "广东省深圳市罗湖区";
			break;
		case 440306:
			text = "广东省深圳市龙华新区";
			break;
		default:
			break;
		}
		
		if( text == null )text = "";
		
		getJspContext().getOut().write(text);
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}
}

