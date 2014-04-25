package com.stern.fraudshields.model;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class CreditCard implements BaseModel{
	
	@Id
	private Long   bin; 
	private String cclast4;
	private String cardtype;
	private String ccexpires;
	private Blob notes;
	private Key<Customer> userDetail;
	private Key<Address> billingAddress;
	
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
		
		String prevValue="";
		if(this.notes!=null)
		{
			prevValue=this.notes.toString();
		}
		
		this.notes = new Blob((prevValue+","+notes.toString()).getBytes());	
	}
	public Key<Customer> getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(Key<Customer> userDetail) {
		this.userDetail = userDetail;
	}
	public Key<Address> getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Key<Address> billingAddress) {
		this.billingAddress = billingAddress;
	}
	public CreditCard(Long bin, String cclast4, String cardtype,
			String ccexpires, Blob notes, Key<Customer> userDetail,
			Key<Address> billingAddress) {
		super();
		this.bin = bin;
		this.cclast4 = cclast4;
		this.cardtype = cardtype;
		this.ccexpires = ccexpires;
		this.notes = notes;
		this.userDetail = userDetail;
		this.billingAddress = billingAddress;
	}
	public CreditCard() {
		super();
		
	}
	
}
