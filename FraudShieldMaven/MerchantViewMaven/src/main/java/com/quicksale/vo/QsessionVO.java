package com.quicksale.vo;

import java.io.Serializable;
import com.google.appengine.api.datastore.Text;


public class QsessionVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String sessionName;
	
	private String lastIp;
	
	private Text session;
	
	private Long userLoginId;
	
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
