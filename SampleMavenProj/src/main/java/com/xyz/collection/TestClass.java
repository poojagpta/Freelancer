package com.xyz.collection;

import java.util.HashMap;
import java.util.Map.Entry;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int i=10;
		int j=i+(i<<1);
		System.out.println(j);
		System.out.println(Integer.MAX_VALUE - 8);
		int numKeysToBeAdded=20;
		float loadFactor=0.75f;
		int initialCapacity=16;
		int intialThreadHoldValue=(int)(loadFactor*initialCapacity);
		System.out.println("ThreadHold--->"+intialThreadHoldValue);
		int targetCapacity = (int)(numKeysToBeAdded / loadFactor + 1);
		System.out.println("Target value--->"+targetCapacity);
		HashMap<String,String> map1=new HashMap<String, String>();
		map1.put("Key1", "Val1");
		map1.put("Key2", "Val2");
		map1.put("Key3", "Value3");
		for(Entry<String,String> entry:map1.entrySet()){
			System.out.println(entry.getValue());
			//map1.remove(entry.getKey()); //Concurrent exception
		}
		
	}

}
