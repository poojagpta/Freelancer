package com.gae.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


public class Merchant implements BaseModel{
	@Id
	private String	siteName;
	private String	siteid;
	private String	appid;
	private String	portalid;
	private Date	timestamp;
	private Blob notes;
	/*private List<Key<OrderDetail>> orderDetails;
	
	private List<Key<User>> user;
	private List<Key<PaymentGateway>> paymentGateway;
	private List<Key<Address>> address;*/
	private List<Key> orderDetails;
	
	//private List<Key> user;
	private List<Key> paymentGateway;
	private List<Key> address;
	private Long userId;
	/**
	 * @return the orderDetails
	 */
	/*public List<Key<OrderDetail>> getOrderDetails() {
		return orderDetails;
	}
	*//**
	 * @param orderDetails the orderDetails to set
	 *//*
	public void setOrderDetails(List<Key<OrderDetail>> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public List<Key<PaymentGateway>> getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(List<Key<PaymentGateway>> paymentGateway) {
		this.paymentGateway = paymentGateway;
	}*/
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPortalid() {
		return portalid;
	}
	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Blob getNotes() {
		return notes;
	}
	public void setNotes(Blob notes) {
		this.notes = notes;
	}
	
	/*public List<Key<User>> getUser() {
		return user;
	}
	public void setUser(List<Key<User>> user) {
		this.user = user;
	}

	
	public Merchant(String siteName, String siteid, String appid,
			String portalid, Date timestamp, Blob notes,
			List<Key<OrderDetail>> orderDetails,
			List<Key<User>> user, List<Key<PaymentGateway>> paymentGateway,
			List<Key<Address>> address) {
		super();
		this.siteName = siteName;
		this.siteid = siteid;
		this.appid = appid;
		this.portalid = portalid;
		this.timestamp = timestamp;
		this.notes = notes;
		this.orderDetails = orderDetails;
		this.user = user;
		this.paymentGateway = paymentGateway;
		this.address = address;
	}*/
	
	
	
	public Merchant() {
		super();
	}
	public List<Key> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<Key> orderDetails) {
		this.orderDetails = orderDetails;
	}
/*	public List<Key> getUser() {
		return user;
	}
	public void setUser(List<Key> user) {
		this.user = user;
	}*/
	public List<Key> getPaymentGateway() {
		return paymentGateway;
	}
	public void setPaymentGateway(List<Key> paymentGateway) {
		this.paymentGateway = paymentGateway;
	}
	public List<Key> getAddress() {
		return address;
	}
	public void setAddress(List<Key> address) {
		this.address = address;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
