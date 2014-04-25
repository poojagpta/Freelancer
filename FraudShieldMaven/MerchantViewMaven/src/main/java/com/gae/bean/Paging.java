package com.gae.bean;

public class Paging {
	
	private static Paging paging;
	
	private Paging(){
		
	}
	
	private int pageLimit;
	private int offset;
	private String cursorString;
	
	public int getPageLimit() {
		return pageLimit;
	}
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getCursorString() {
		return cursorString;
	}
	public void setCursorString(String cursorString) {
		this.cursorString = cursorString;
	}
	
	
	public static Paging getPaging(){
		if(paging != null){
			return paging;
		}else{
			return new Paging();
		}
	}

	
} 
