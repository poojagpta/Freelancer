package com.xyz.SortAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSortByBook {

	public static void main(String str[]){
		
		Integer[] number = { 54,26,93,17,77,31,44,55,20 };
		QuickSortByBook sort=new QuickSortByBook();
		List newArr=new ArrayList<Integer>(Arrays.asList(number));
		sort.quickSortHelper(newArr,0,number.length-1);
		for (Object intVal : newArr) {
			System.out.println(intVal);
		}
	}
	
	public void quickSortHelper(List alist, int first, int last) {
		if (first < last) {
			//Shuffle
			int splitpoint = partition(alist, first, last);

			quickSortHelper(alist, first, splitpoint - 1);
			quickSortHelper(alist, splitpoint + 1, last);

		}

	}

	public int partition(List alist, int first, int last) {
		int pivotvalue = (Integer) alist.get(first);
		int leftmark = first + 1;
		int rightmark = last;
		boolean done = false;
		while (!done) {
			while (leftmark <= rightmark
					&& (Integer) alist.get(leftmark) <= pivotvalue) {
				leftmark++;
			}

			while ((Integer) alist.get(rightmark) >= pivotvalue
					&& rightmark >= leftmark) {
				rightmark--;
			}

			if (rightmark < leftmark) {
				done = true;
			} else {
				int temp = (Integer) alist.get(leftmark);
				alist.set(leftmark, alist.get(rightmark));
				alist.set(rightmark, temp);
			}
		}

		int temp = (Integer) alist.get(first);
		alist.set(first, alist.get(rightmark));
		alist.set(rightmark, temp);

		return rightmark;

	}

}
