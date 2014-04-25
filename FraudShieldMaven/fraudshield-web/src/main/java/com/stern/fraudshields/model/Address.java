package com.stern.fraudshields.model;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Unindexed;


public class Address  implements BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String company;
	private Text addressl;
	private Text address2;
	private String city;
	private String state;
	private String province;
	private String zip;
	private String country;
	private String phone;
	private String dayPhone;
	private String cellPhone;
	private String email;
	private String alternateEmail;
	@Unindexed private Blob notes;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Text getAddressl() {
		return addressl;
	}
	public void setAddressl(Text addressl) {
		this.addressl = addressl;
	}
	public Text getAddress2() {
		return address2;
	}
	public void setAddress2(Text address2) {
		this.address2 = address2;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDayPhone() {
		return dayPhone;
	}
	public void setDayPhone(String dayPhone) {
		this.dayPhone = dayPhone;
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
	
	public Address(Long id, String firstName, String middleName,
			String lastName, String company, Text addressl, Text address2,
			String city, String state, String province, String zip,
			String country, String phone, String dayPhone, String cellPhone,
			String email, String alternateEmail, Blob notes) {

		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.company = company;
		this.addressl = addressl;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.province = province;
		this.zip = zip;
		this.country = country;
		this.phone = phone;
		this.dayPhone = dayPhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.alternateEmail = alternateEmail;
		this.notes = notes;
	}
	public Address() {
		
	}
	
	
}
