package com.gae.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


public class CreditCard implements BaseModel{
	
	private Long   bin; 
	private String cclast4;
	private String cardtype;
	private String ccexpires;
	private Blob notes;
	/*private Key<User> userDetail;
	private Key<Address> billingAddress;*/
	private Key userDetail;
	private Key billingAddress;
	
	public Long getBin() {
		return bin;
	}
	public void setBin(Long bin) {
		this.bin = bin;
	}
	public String getCclast4() {
		return cclast4;
	}
	public void setCclast4(String cclast4) {
		this.cclast4 = cclast4;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCcexpires() {
		return ccexpires;
	}
	public void setCcexpires(String ccexpires) {
		this.ccexpires = ccexpires;
	}
	public Blob getNotes() {
		return notes;
	}
	public void setNotes(Blob notes) {
		this.notes = notes;
	}
	/*public Key<User> getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(Key<User> userDetail) {
		this.userDetail = userDetail;
	}
	public Key<Address> getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Key<Address> billingAddress) {
		this.billingAddress = billingAddress;
	}
	public CreditCard(Long bin, String cclast4, String cardtype,
			String ccexpires, Blob notes, Key<User> userDetail,
			Key<Address> billingAddress) {
		super();
		this.bin = bin;
		this.cclast4 = cclast4;
		this.cardtype = cardtype;
		this.ccexpires = ccexpires;
		this.notes = notes;
		this.userDetail = userDetail;
		this.billingAddress = billingAddress;
	}*/
	
	public Key getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(Key userDetail) {
		this.userDetail = userDetail;
	}
	public Key getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Key billingAddress) {
		this.billingAddress = billingAddress;
	}

	
	public CreditCard() {
		super();
		
	}
		
}
