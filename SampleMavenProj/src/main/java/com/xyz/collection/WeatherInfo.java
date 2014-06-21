package com.xyz.collection;

import java.io.Serializable;

public class WeatherInfo implements Serializable,Comparable<WeatherInfo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int amount;
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public WeatherInfo(String name, int amount) {
		super();
		this.name = name;
		this.amount = amount;
	}
	/**
	 * @return the amount
	 */
	public final int getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	//@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return getName().length();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WeatherInfo [name=" + name + ", amount=" + amount + "]";
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj!=null){
			return this.getName().equals(((WeatherInfo)obj).getName());	
		}
		
		return false;
	}
	@Override
	public int compareTo(WeatherInfo o) {
		// TODO Auto-generated method stub
		return this.getName().compareTo(o.getName());
	}
}
