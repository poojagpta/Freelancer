package com.uwea.cache;

import java.util.ArrayList;
import java.util.List;

public class IndexFieldCache {

	private static List<String> cacheIndexFields=new ArrayList<String>();
	
	public static List<String> getcacheIndexFields(){
		return cacheIndexFields;
	}
	
	public static void put(List<String> list){
		cacheIndexFields=list;
	}
	
}
