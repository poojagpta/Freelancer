package com.xyz.collection;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeSet;

import com.xyz.log.Fruit;

public class DriverClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		HashMap map;
		// TODO Auto-generated method stub
		WeatherInfo a=new WeatherInfo("pooja", 122);
		WeatherInfo b=new WeatherInfo("pooja", 123);
		WeatherInfo c=new WeatherInfo("pooja1", 122);
		Fruit f=new Fruit("ABA", "Test", 20);
		NewArrayListImpl impl=new NewArrayListImpl();
		impl.add(a);
		impl.add(b);
		impl.add(c);
		impl.add(f);
		
		
		HashMap mapt=new HashMap(); //tom,54
		mapt.put(a,"Test A");
		mapt.put(c,"Test C");
		
		a.setName("Joopa");//mot //NUllPOInterException
		c=null;
		if(c!=null)
		System.out.println(c.getName());
		mapt.remove(c);
		
		System.out.println(mapt.get(new WeatherInfo("Joopa",77)));
		System.out.println(mapt.get(new WeatherInfo("pooja1",77)));
		
		TreeSet settree=new TreeSet();
		settree.add(a);
		//settree.add(c);
		
  System.out.println("Name of thread"+Thread.currentThread().getName());
		
	}

}
