package com.uguanjia.o2o.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.uguanjia.o2o.service.Service;

/*******************************************
 * @CLASS:ProcessTag
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年3月24日 上午1:04:25
 *******************************************/
public class ServiceTag extends SimpleTagSupport{

	private int id;
	
	private String type;

	@Override
	public void doTag() throws JspException, IOException {
		
		String text = null;
		
		Service[] services = Service.values();
		
		for( Service service : services )
		{
			if( service.ID == id )
			{
				if(  StringUtils.isEmpty(type)
						|| "name".equalsIgnoreCase(type) )
				{
					text = service.NAME;
				}
				else if( "code".equalsIgnoreCase(type) )
				{
					text = service.CODE;
				}
				else
				{
					text = "";
				}
			}
		}
		
		if( text == null )text = "";
		
		getJspContext().getOut().write(text);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

