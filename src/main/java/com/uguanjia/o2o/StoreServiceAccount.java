package com.uguanjia.o2o;
/*******************************************
 * @CLASS:StoreServiceAccount
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月10日 下午1:17:31
 *******************************************/
public class StoreServiceAccount {
	
	private long storeId;
	
	private int type;
	
	private String account;
	
	private String password;
	
	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

