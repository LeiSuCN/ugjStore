package com.uguanjia.o2o;

import java.util.List;

import com.uguanjia.o2o.meta.StoreProcessActivity;

/*******************************************
 * @CLASS:Store
 * @DESCRIPTION:	
 * @VERSION:v1.0
 *******************************************/
public class Store {
	
	private long id;
	
	private String name;
	
	private long registrationNo;
	
	private String legalPerson;
	
	private String address;
	
	private String phonenumber;
	
	private String alipay;
	
	private int area;
	
	private float lat;
	
	private float lon;
	
	private long qq;
	
	private StoreAccount account;
	
	private List<StoreServiceAccount> serviceAccounts;
	
	private List<StoreProcessActivity> activities;
	
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public long getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(long registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public StoreAccount getAccount() {
		return account;
	}

	public void setAccount(StoreAccount account) {
		this.account = account;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public List<StoreServiceAccount> getServiceAccounts() {
		return serviceAccounts;
	}

	public void setServiceAccounts(List<StoreServiceAccount> serviceAccounts) {
		this.serviceAccounts = serviceAccounts;
	}

	public long getQq() {
		return qq;
	}

	public void setQq(long qq) {
		this.qq = qq;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<StoreProcessActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<StoreProcessActivity> activities) {
		this.activities = activities;
	}
}

