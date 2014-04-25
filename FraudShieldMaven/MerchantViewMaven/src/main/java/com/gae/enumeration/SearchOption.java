package com.gae.enumeration;

public enum SearchOption {
	
	CUSTOMER(0,"Customer"),
	ADDRESS(1,"Address"),
	MERCHANT(2,"Merchant"),
	ORDER_DETAIL(3,"OrderDetail");
	
	private SearchOption(int key, String value) {
		this.key = key;
		this.value = value;
	}
	private int key;
	private String value;
	
	public int getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
	public static SearchOption getByKey(int key) {
	
		for (SearchOption searchOption : values()) {
			if(searchOption.getKey() == key){
				return searchOption;
			}
		}
		return null;

	}
	
	public static SearchOption findByValue(String value) {
		for (SearchOption searchKind : values()) {
			if(searchKind.getValue().equals(value)){
				return searchKind;
			}
		}
		return null;

	}
	
	

}
