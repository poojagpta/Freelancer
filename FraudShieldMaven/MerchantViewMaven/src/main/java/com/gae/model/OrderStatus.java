package com.gae.model;

public enum OrderStatus {

	ACCEPTED("Accepted"), 
	DECLINE("Decline"), 
	REVIEW("Review"),
	PENDING("Pending");
	
	private String orderStatus;
	 
	private OrderStatus(String s) {
		orderStatus = s;
	}
 
	public String getOrderStatus() {
		return orderStatus;
	}
	
	public static OrderStatus findByValue(String value){
		for (OrderStatus status : values()) {
			if(status.getOrderStatus().equals(value)){
				return status;
			}
		}
		return null;
	}
}
