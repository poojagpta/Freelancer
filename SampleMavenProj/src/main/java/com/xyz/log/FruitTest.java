package com.xyz.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FruitTest {

	public static class GenricSort implements Comparator<Fruit>{

		@Override
		public int compare(Fruit o1, Fruit o2) {
			// TODO Auto-generated method stub
			if(o1.getQuantity()>o2.getQuantity()){
				return 1;
			}else if(o1.getQuantity()<o2.getQuantity()){
				return -1;
			}
			
			
			return 0;
		}
		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=new String(new char[]{'a','b','c','c'},2,2);
		System.out.println(s.hashCode());
		System.out.println("cc".hashCode());
		
		/**
		 * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
		 */
		System.out.println(99*31+99*31);
		
		Object[] obj=new Object[10];
		//System.out.println(obj.length);
		//System.out.println(obj[0]);
		
		
		Fruit fruit1 = new Fruit("Apple", "dfsa", 33);
		Fruit fruit2 = new Fruit("Gauava", "EFSD", 12);
		Fruit fruit3 = new Fruit("Orange", "dfsa", 93);
		Fruit fruit4 = new Fruit("Banana", "dfsa", 8);
		List<Fruit> fruits = new ArrayList<Fruit>();
		fruits.add(fruit1);
		fruits.add(fruit2);
		fruits.add(fruit3);
		fruits.add(fruit4);
		Collections.sort(fruits);
		for (Fruit fruit : fruits) {
			System.out.println(fruit);			
		}

		GenricSort gs=new GenricSort();
		Collections.sort(fruits,gs);
		System.out.println("Fruits by comparator-------->");
		for (Fruit fruit : fruits) {
			System.out.println(fruit);			
		}

		
	}

}
