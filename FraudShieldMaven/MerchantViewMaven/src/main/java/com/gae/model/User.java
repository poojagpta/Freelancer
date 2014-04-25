package com.gae.model;

import java.util.List;

import javax.persistence.Id;

import com.gae.annotation.UnIndexSearch;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;


// Need to change the User entity as Customer in order to avoid conflict with login user
// Currently only its Kind in datastore is being changed to Customer.
public class User implements BaseModel{

	@Id
	private String loginName;
	
	@UnIndexSearch
	private String loginPassword;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String cellPhone;
	private String email;
	private String alternateEmail;
	
	@UnIndexSearch
	private String ipAddress;
	
	@UnIndexSearch
	private Blob notes;
/*	private List<Key<Merchant>> merchant;
	private List<Key<Address>> address;
	private List<Key<CreditCard>> creditCards;
	private List<Key<OrderDetail>> orderDetails;*/
	
	//private List<Key> merchant;
	private List<Key> address;
	
	@UnIndexSearch
	private List<Key> creditCards;
	
	private List<Key> orderDetails;
	
	@UnIndexSearch
	private Long userId;     // represents the registered user in the system
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
	}*/


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

	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCellPhone() {
		return cellPhone;
	}


	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAlternateEmail() {
		return alternateEmail;
	}


	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
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


	/*public List<Key<Address>> getAddress() {
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
*/


	
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


	public User(String loginName, String loginPassword, String iPAddress,
			Blob notes, List<Key<Merchant>> merchant,
			List<Key<Address>> address, List<Key<CreditCard>> creditCards,
			List<Key<OrderDetail>> orderDetails) {
		super();
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.ipAddress = iPAddress;
		this.notes = notes;
		this.merchant = merchant;
		this.address = address;
		this.creditCards = creditCards;
		this.orderDetails = orderDetails;
	}

*/
	public User() {
		
	}
	
	
	
	/*public List<Key> getMerchant() {
		return merchant;
	}


	public void setMerchant(List<Key> merchant) {
		this.merchant = merchant;
	}*/


	public List<Key> getAddress() {
		return address;
	}


	public void setAddress(List<Key> address) {
		this.address = address;
	}


	public List<Key> getCreditCards() {
		return creditCards;
	}


	public void setCreditCards(List<Key> creditCards) {
		this.creditCards = creditCards;
	}


	public List<Key> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(List<Key> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public String viewPrivateData()
	{
		return getIPAddress();
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

		
	
}
