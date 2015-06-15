package com.uguanjia.o2o;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/*******************************************
 * @CLASS:Operator
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年2月10日 下午10:06:07
 *******************************************/
public class Operator {
	
	private String username;
	
	private List<String> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public boolean hasRole(String role){
		
		boolean has = false;
		
		if( StringUtils.isEmpty(role) || this.roles != null ){
			for( String r : roles ){
				if( role.equalsIgnoreCase(r) ){
					has = true;
					break;
				}
			}
		}
		
		return has;
		
	}
}

