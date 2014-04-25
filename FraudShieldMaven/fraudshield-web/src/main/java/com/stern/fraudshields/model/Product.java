package com.stern.fraudshields.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class Product implements BaseModel{
	
	@Id
	private String	productname;
	private String	productprice;
	private String	giftmessage;
	private Blob notes;
	private List<Key<Customer>> userlist;
	private List<Key<OrderDetail>> orderDetail;
	private List<Key<Merchant>> merchant;
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductprice() {
		return productprice;
	}
	public void setProductprice(String productprice) {
		this.productprice = productprice;
	}
	public String getGiftmessage() {
		return giftmessage;
	}
	public void setGiftmessage(String giftmessage) {
		this.giftmessage = giftmessage;
	}
	public Blob getNotes() {
		return notes;
	}
	public void setNotes(Blob notes) {
		this.notes = notes;
	}
	public List<Key<Customer>> getUserlist() {
		return userlist;
	}
	public void setUserlist(List<Key<Customer>> userlist) {
		this.userlist = userlist;
	}
	public List<Key<OrderDetail>> getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(List<Key<OrderDetail>> orderDetail) {
		this.orderDetail = orderDetail;
	}
	
	public List<Key<Merchant>> getMerchant() {
		return merchant;
	}
	public void setMerchant(List<Key<Merchant>> merchant) {
		this.merchant = merchant;
	}
	public Product(String productname, String productprice, String giftmessage,
			Blob notes, List<Key<Customer>> userlist,
			List<Key<OrderDetail>> orderDetail,
			List<Key<Merchant>> merchant) {
		super();
		this.productname = productname;
		this.productprice = productprice;
		this.giftmessage = giftmessage;
		this.notes = notes;
		this.userlist = userlist;
		this.orderDetail = orderDetail;
		this.merchant = merchant;
	}
	public Product() {
		super();
	}
	
	
}
