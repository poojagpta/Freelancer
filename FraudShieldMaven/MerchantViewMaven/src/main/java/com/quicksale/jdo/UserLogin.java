package com.quicksale.jdo;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.persistence.Id;

public class UserLogin {
	
	@Id
	private Long Id;
	@Persistent
	private String loginName;
	@Persistent
	private String password;
	@Persistent
	private String currentIp;
	@Persistent
	private String sessionName;
	@Persistent
	private Date expirationDate;
	@Persistent
	private Long userId;
	
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getCurrentIp() {
		return currentIp;
	}
	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	
	
	
}
