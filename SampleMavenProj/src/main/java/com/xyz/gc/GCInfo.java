package com.xyz.gc;

import com.xyz.collection.WeatherInfo;

public class GCInfo extends WeatherInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8431167940764874038L;

	public GCInfo(String name, int amount) {
		super(name, amount);
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
       System.out.println("Came in finalized method");
	}
	

}
