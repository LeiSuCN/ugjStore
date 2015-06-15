package com.uguanjia.o2o;
/*******************************************
 * @CLASS:StoreUser
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月8日 下午9:53:06
 *******************************************/
public class User {
	
	private String username;
	
	private String password;
	
	private long storeId;
	
	private int enabled;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
}

