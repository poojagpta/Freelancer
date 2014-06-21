package com.xyz.log;

import java.util.Arrays;
import java.util.Comparator;

public class CompareTest {

	public static void main(String str[]) {
		String[] fruits = new String[] { "Pineapple", "Apple", "Orange",
				"Banana" };

		TestComparator testComp = new TestComparator();
		
		Arrays.sort(fruits,testComp);

		int i = 0;
		for (String temp : fruits) {
			System.out.println("fruits " + ++i + " : " + temp);
		}

		
	}

	static class TestComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			String str1=(String)o1;
			String str2=(String)o2;
			return str2.compareTo(str1);
		}
		
	}
	
	
}
