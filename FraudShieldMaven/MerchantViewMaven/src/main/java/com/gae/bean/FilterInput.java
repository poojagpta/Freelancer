package com.gae.bean;

import java.util.Date;
import java.util.List;

import com.gae.model.OrderStatus;
import com.google.appengine.api.datastore.Key;

public class FilterInput {

	private List<Key> merchantKeyList;
	private String siteId;
	private OrderStatus status;
	private Date fromDate;
	private Date toDate;
	
	public List<Key> getMerchantKeyList() {
		return merchantKeyList;
	}
	public void setMerchantKeyList(List<Key> merchantKeyList) {
		this.merchantKeyList = merchantKeyList;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
	
}
