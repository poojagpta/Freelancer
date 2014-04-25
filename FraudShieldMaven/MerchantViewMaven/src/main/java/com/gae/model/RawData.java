package com.gae.model;

import java.util.Date;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;

public class RawData implements BaseModel{
	@Id
	private Long id;
	private Date timestamp;
	private String remote_ip;
	private Blob headers;
	private Blob post;
	private String jsonObject;
		
	/**
	 * @return the jsonObject
	 */
	public String getJsonObject() {
		return jsonObject;
	}
	/**
	 * @param jsonObject the jsonObject to set
	 */
	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getRemote_ip() {
		return remote_ip;
	}
	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}
	public Blob getHeaders() {
		return headers;
	}
	public void setHeaders(Blob headers) {
		this.headers = headers;
	}
	public Blob getPost() {
		return post;
	}
	public void setPost(Blob post) {
		this.post = post;
	}
	

	public RawData() {
		super();
	}
	
	public RawData( Date timestamp, String remote_ip,
			Blob headers, Blob post, Integer phone) {
		super();
		this.timestamp = timestamp;
		this.remote_ip = remote_ip;
		this.headers = headers;
		this.post = post;
		}
	

}
