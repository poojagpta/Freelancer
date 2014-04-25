package com.gae.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.gae.annotation.ReferencedName;
import com.gae.annotation.UnIndexSearch;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


public class OrderDetail implements BaseModel {

	@Id
	private String orderDetailId;
	private Date orderDate;
	private String orderType;
	private String salesRep;
	private String promoCode;
	private String grandTotal;
	private String shippingMethod;
	private OrderStatus orderStatus;
	private String customerComments;
	private String salesrepComments;
	private String inboundCallerId;
    private String consumerReferral;
	
	

/*	private Key<Address> billingAddress;
	private Key<Address> shippingAddress;
	private List<Key<Product>> productList;
	private Key<User> user;*/

    @ReferencedName(name="billingAddress")
    private Key billingAddress;
    
    @ReferencedName(name="shippingAddress")
	private Key shippingAddress;
    
	private List<Key> productList;
	private Key user;
    
	@UnIndexSearch
	private Blob notes;
	
	@UnIndexSearch
	private String cidresponse;
	//private	Key<PaymentGateway> paymentGateway;
	@UnIndexSearch
	private	Key paymentGateway;
	
	private	String	referringcode;
	
	@UnIndexSearch
	private	String	cavvresultcode;
	
	@UnIndexSearch
	private	String	ecicode;
	
	@UnIndexSearch
	private	String	avscode;
	//private Key<CreditCard> creditCard;
	@UnIndexSearch
	private Key creditCard;
	
	private Key merchant;

	/**
	 * @return the consumerReferral
	 */
	public String getConsumerReferral() {
		return consumerReferral;
	}

	/**
	 * @param consumerReferral the consumerReferral to set
	 */
	public void setConsumerReferral(String consumerReferral) {
		this.consumerReferral = consumerReferral;
	}
	
	/**
	 * @return the cidresponse
	 */
	public String getCidresponse() {
		return cidresponse;
	}

	/**
	 * @param cidresponse the cidresponse to set
	 */
	public void setCidresponse(String cidresponse) {
		this.cidresponse = cidresponse;
	}

	/**
	 * @return the paymentGateway
	 */
	/*public Key<PaymentGateway> getPaymentGateway() {
		return paymentGateway;
	}

	*//**
	 * @param paymentGateway the paymentGateway to set
	 *//*
	public void setPaymentGateway(Key<PaymentGateway> paymentGateway) {
		this.paymentGateway = paymentGateway;
	}*/

	/**
	 * @return the referringcode
	 */
	public String getReferringcode() {
		return referringcode;
	}

	/**
	 * @param referringcode the referringcode to set
	 */
	public void setReferringcode(String referringcode) {
		this.referringcode = referringcode;
	}

	/**
	 * @return the cavvresultcode
	 */
	public String getCavvresultcode() {
		return cavvresultcode;
	}

	/**
	 * @param cavvresultcode the cavvresultcode to set
	 */
	public void setCavvresultcode(String cavvresultcode) {
		this.cavvresultcode = cavvresultcode;
	}

	/**
	 * @return the ecicode
	 */
	public String getEcicode() {
		return ecicode;
	}

	/**
	 * @param ecicode the ecicode to set
	 */
	public void setEcicode(String ecicode) {
		this.ecicode = ecicode;
	}

	/**
	 * @return the avscode
	 */
	public String getAvscode() {
		return avscode;
	}

	/**
	 * @param avscode the avscode to set
	 */
	public void setAvscode(String avscode) {
		this.avscode = avscode;
	}

	/**
	 * @return the orderDetailId
	 */
	public String getOrderDetailId() {
		return orderDetailId;
	}

	/**
	 * @param orderDetailId
	 *            the orderDetailId to set
	 */
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderdate) {
		this.orderDate = orderdate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSalesRep() {
		return salesRep;
	}

	public void setSalesRep(String salesRep) {
		this.salesRep = salesRep;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getCustomerComments() {
		return customerComments;
	}

	public void setCustomerComments(String customerComments) {
		this.customerComments = customerComments;
	}

	public String getSalesrepComments() {
		return salesrepComments;
	}

	public void setSalesrepComments(String salesrepComments) {
		this.salesrepComments = salesrepComments;
	}

	public String getInboundCallerId() {
		return inboundCallerId;
	}

	public void setInboundCallerId(String inboundCallerId) {
		this.inboundCallerId = inboundCallerId;
	}

	/*public Key<Address> getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Key<Address> billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Key<Address> getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Key<Address> shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<Key<Product>> getProductList() {
		return productList;
	}

	public void setProductList(List<Key<Product>> productList) {
		this.productList = productList;
	}

	public Key<User> getUser() {
		return user;
	}

	public void setUser(Key<User> user) {
		this.user = user;
	}
*/
	
	public Blob getNotes() {
		return notes;
	}

	public void setNotes(Blob notes) {
		this.notes = notes;
	}

	/*public OrderDetail(String orderDetailId, Date orderdate, String ordertype,
			String salesRep, String promoCode, String grandTotal,
			String shippingMethod, String customerComments,
			String salesrepComments, String inboundCallerId,
			Key<Address> billingAddress, Key<Address> shippingAddress,
			List<Key<Product>> productList, Key<User> user, Blob notes) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderdate = orderdate;
		this.ordertype = ordertype;
		SalesRep = salesRep;
		this.promoCode = promoCode;
		this.grandTotal = grandTotal;
		this.shippingMethod = shippingMethod;
		this.customerComments = customerComments;
		this.salesrepComments = salesrepComments;
		this.inboundCallerId = inboundCallerId;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.productList = productList;
		this.user = user;
		this.notes = notes;
	}*/

	public OrderDetail() {
		super();
	}

	public Key getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Key billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Key getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Key shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<Key> getProductList() {
		return productList;
	}

	public void setProductList(List<Key> productList) {
		this.productList = productList;
	}

	public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}

	public Key getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(Key paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public Key getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Key creditCard) {
		this.creditCard = creditCard;
	}

	public Key getMerchant() {
		return merchant;
	}

	public void setMerchant(Key merchant) {
		this.merchant = merchant;
	}
	
	
/*
	public Key<CreditCard> getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Key<CreditCard> creditCard) {
		this.creditCard = creditCard;
	}*/

}
