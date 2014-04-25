package com.uwea.bo;

import java.io.Serializable;
import java.util.Map;

public class DocumentBO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String type; //Constraints add,delete,
	public String id;
	public int version;
	public String lang; 
	public Map<String,Object> fields;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Map<String, Object> getFields() {
		return fields;
	}
	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}	
   
	
    
}
