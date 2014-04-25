package com.gae.bean;

import java.util.Date;

import com.gae.model.OrderStatus;

public class MerchantDataGrid {
	
	private String userName;
	private String address;
	private String city;
	private String state;
	private String country;
	private String merchantName;
	private Date date;
	private OrderStatus orderStatus;
	private String billingAddress;
	private String shippingAddress;
	
	public MerchantDataGrid() {
		super();
	}

	public MerchantDataGrid(String userName, String address, String city,
			String state, String country, String merchantName, Date date,
			OrderStatus status, String billingAddress, String shippingAddress) {
		super();
		this.userName = userName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.merchantName = merchantName;
		this.date = date;
		this.orderStatus = status;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}



}
