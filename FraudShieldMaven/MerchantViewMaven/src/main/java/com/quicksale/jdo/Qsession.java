package com.quicksale.jdo;

import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

public class Qsession {
	
	@Persistent
	private String sessionName;
	@Persistent
	private String lastIp;
	@Persistent
	private Text session;
	@Persistent
	private Long userLoginId;
	@Persistent
	private Long userId;
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Text getSession() {
		return session;
	}
	public void setSession(Text session) {
		this.session = session;
	}
	public Long getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(Long userLoginId) {
		this.userLoginId = userLoginId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
}
