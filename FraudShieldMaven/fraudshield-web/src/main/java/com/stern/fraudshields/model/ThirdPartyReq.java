package com.stern.fraudshields.model;

import javax.persistence.Id;

public class ThirdPartyReq implements BaseModel{
	
	@Id
	private String orderId;
	private String reqObject;
	private String respObject;
	private String status;
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the reqObject
	 */
	public String getReqObject() {
		return reqObject;
	}
	/**
	 * @param reqObject the reqObject to set
	 */
	public void setReqObject(String reqObject) {
		this.reqObject = reqObject;
	}
	/**
	 * @return the respObject
	 */
	public String getRespObject() {
		return respObject;
	}
	/**
	 * @param respObject the respObject to set
	 */
	public void setRespObject(String respObject) {
		this.respObject = respObject;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
