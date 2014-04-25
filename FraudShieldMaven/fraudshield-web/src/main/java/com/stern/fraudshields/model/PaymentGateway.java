package com.stern.fraudshields.model;

import java.io.Serializable;

import javax.persistence.Id;

public class PaymentGateway implements BaseModel{
    @Id
	private Long gateway_id;
	private int cart_id;	
	private String name;
	private String domain;
	private String awcmid;
	private String login;
	private String password ;
	private String source_key;
	private String pin;
	private String anet_login;
	private String anet_key ;
	private String yahoo_storeid;
	private String yahoo_token;
	private String volusion_login;
	private String volusion_password;
	private String status;
	
	public PaymentGateway(int cart_id, Long gateway_id, String name,
			String domain, String awcmid, String login, String password,
			String source_key, String pin, String anet_login, String anet_key,
			String yahoo_storeid, String yahoo_token, String volusion_login,
			String volusion_password, String status) {
		super();
		this.cart_id = cart_id;
		this.gateway_id = gateway_id;
		this.name = name;
		this.domain = domain;
		this.awcmid = awcmid;
		this.login = login;
		this.password = password;
		this.source_key = source_key;
		this.pin = pin;
		this.anet_login = anet_login;
		this.anet_key = anet_key;
		this.yahoo_storeid = yahoo_storeid;
		this.yahoo_token = yahoo_token;
		this.volusion_login = volusion_login;
		this.volusion_password = volusion_password;
		this.status = status;
	}
	
	
	public PaymentGateway() {
		super();
	}


	public int getCart_id() {
		return cart_id;
	}
	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}
	public Long getGateway_id() {
		return gateway_id;
	}
	public void setGateway_id(Long gateway_id) {
		this.gateway_id = gateway_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAwcmid() {
		return awcmid;
	}
	public void setAwcmid(String awcmid) {
		this.awcmid = awcmid;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSource_key() {
		return source_key;
	}
	public void setSource_key(String source_key) {
		this.source_key = source_key;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getAnet_login() {
		return anet_login;
	}
	public void setAnet_login(String anet_login) {
		this.anet_login = anet_login;
	}
	public String getAnet_key() {
		return anet_key;
	}
	public void setAnet_key(String anet_key) {
		this.anet_key = anet_key;
	}
	public String getYahoo_storeid() {
		return yahoo_storeid;
	}
	public void setYahoo_storeid(String yahoo_storeid) {
		this.yahoo_storeid = yahoo_storeid;
	}
	public String getYahoo_token() {
		return yahoo_token;
	}
	public void setYahoo_token(String yahoo_token) {
		this.yahoo_token = yahoo_token;
	}
	public String getVolusion_login() {
		return volusion_login;
	}
	public void setVolusion_login(String volusion_login) {
		this.volusion_login = volusion_login;
	}
	public String getVolusion_password() {
		return volusion_password;
	}
	public void setVolusion_password(String volusion_password) {
		this.volusion_password = volusion_password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
