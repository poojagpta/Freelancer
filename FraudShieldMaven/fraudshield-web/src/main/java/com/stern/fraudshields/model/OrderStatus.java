package com.stern.fraudshields.model;

public enum OrderStatus {

	ACCEPTED("Accepted"), DECLINE("Decline"), REVIEW("Review");
	
	private String orderStatus;
	 
	private OrderStatus(String s) {
		orderStatus = s;
	}
 
	public String getOrderStatus() {
		return orderStatus;
	}
}
