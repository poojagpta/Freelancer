package com.stern.fraudshields.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class Customer implements BaseModel{

	@Id
	private String loginName;
	private String loginPassword;
	private String ipAddress;
	private Blob notes;
	//private List<Key<Merchant>> merchant;
	private List<Key<Address>> address;
	private List<Key<CreditCard>> creditCards;
	private List<Key<OrderDetail>> orderDetails;
	
	
	/**
	 * @return the orderDetails
	 */
	public List<Key<OrderDetail>> getOrderDetails() {
		return orderDetails;
	}


	/**
	 * @param orderDetails the orderDetails to set
	 */
	public void setOrderDetails(List<Key<OrderDetail>> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	private String getIPAddress() {
		return ipAddress;
	}


	public void setIPAddress(String iPAddress) {
		this.ipAddress = iPAddress;
	}


	public Blob getNotes() {
		return notes;
	}


	public void setNotes(Blob notes) {
		this.notes = notes;
	}


	public List<Key<Address>> getAddress() {
		return address;
	}


	public void setAddress(List<Key<Address>> address) {
		this.address = address;
	}


	public List<Key<CreditCard>> getCreditCards() {
		return creditCards;
	}


	public void setCreditCards(List<Key<CreditCard>> creditCards) {
		this.creditCards = creditCards;
	}



	
	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	/*public List<Key<Merchant>> getMerchant() {
		return merchant;
	}


	public void setMerchant(List<Key<Merchant>> merchant) {
		this.merchant = merchant;
	}
*/

	public Customer(String loginName, String loginPassword, String iPAddress,
			Blob notes, List<Key<Merchant>> merchant,
			List<Key<Address>> address, List<Key<CreditCard>> creditCards,
			List<Key<OrderDetail>> orderDetails) {
		super();
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.ipAddress = iPAddress;
		this.notes = notes;
		//this.merchant = merchant;
		this.address = address;
		this.creditCards = creditCards;
		this.orderDetails = orderDetails;
	}


	public Customer() {
		
	}
	
	public String viewPrivateData()
	{
		return getIPAddress();
	}
}
