package com.gae.bean;

import java.util.Date;

import com.gae.enumeration.SearchOption;
import com.gae.model.OrderStatus;

public class SearchInput {

	private String userName;
	private String email;
	private String address;
	private String city;
	private String state;
	private String country;
	private String merchantName;
	private Date fromDate;
	private Date toDate;
	private OrderStatus orderStatus;
	private String billAddress;
	private String shipAddress;
	private SearchOption searchOption;
	//private Paging paging;
	//private String cursorString;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBillAddress() {
		return billAddress;
	}
	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}
	public String getShipAddress() {
		return shipAddress;
	}
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	public SearchOption getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(SearchOption searchOption) {
		this.searchOption = searchOption;
	}
	/*public String getCursorString() {
		return cursorString;
	}
	public void setCursorString(String cursorString) {
		this.cursorString = cursorString;
	}
*/
	/*public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}	*/
	
		
}
