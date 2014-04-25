package com.quicksale.vo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Persistent;

import com.quicksale.jdo.Qsession;
import com.quicksale.jdo.User;

public class UserLoginVO implements Serializable {

	private Long Id;

	private String loginName;

	private String password;

	private String currentIp;

	private String sessionId;

	private Date expirationDate;
	
	private Long userId;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

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

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
