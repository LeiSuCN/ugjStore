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
public class ProcessTag extends SimpleTagSupport{
	
	private String type = "order";
	
	private int status;

	@Override
	public void doTag() throws JspException, IOException {
		
		if( "order".equalsIgnoreCase(type) )
		{
			String s = null;
			
			switch (status) {
			case 1:
				s = "新建";
				break;
			case 2:
				s = "接受";
				break;
			case 3:
				s = "拒绝";
				break;
			case 98:
				s = "取消";
				break;
			case 99:
				s = "完成";
				break;

			default:
				s = null;
				break;
			}
			
			if( s != null )
			{
				getJspContext().getOut().write(s);
			}
			
		}
		else if(  "contract".equalsIgnoreCase(type)  )
		{
			
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

