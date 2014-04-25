package com.stern.fraudshields.model;

import javax.persistence.Id;

public class MerchantMapping implements BaseModel{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 9187788098683435830L;
	
	 @Id
	  public String name;
	  public String description;
	  public String xmlUrl;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the xmlUrl
	 */
	public String getXmlUrl() {
		return xmlUrl;
	}
	/**
	 * @param xmlUrl the xmlUrl to set
	 */
	public void setXmlUrl(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}
	
	
	
}
