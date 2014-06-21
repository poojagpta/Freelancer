package com.xyz.SortAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author pooja
 *
 */
public class MergeSort {

	public static void main(String str[]) {

		Integer[] number = { 100, 11, 44, 3, 77, 1, 8, 4, 55, 79 };

		int citer = 0;
		 MergeSort sort=new MergeSort();
		 List newList=sort.mergeAndSort(new ArrayList<Integer>(Arrays.asList(number)));

		for (Object intVal : newList) {
			System.out.println(intVal);
		}

	}

	public List mergeAndSort(List number) {

		if (number.size() > 1) {
			int divpoint = number.size() / 2;

			List left = new ArrayList(divpoint);
			List right = new ArrayList(divpoint + 1);
			for (int i = 0; i < divpoint; i++) {
				left.add(number.get(i));
			}
			for (int i = divpoint; i <= number.size() - 1; i++) {
				right.add(number.get(i));
			}

			mergeAndSort(left);
			mergeAndSort(right);

			int i = 0, j = 0, k = 0;

			
			while (i < left.size() && j < right.size()) {

				if (((Integer) left.get(i)).intValue() < ((Integer) right
						.get(j)).intValue()) {
					number.set(k, left.get(i));
					i++;
				} else {
					number.set(k, right.get(j));
					j++;
				}
				k++;
			}

			while (i < left.size()) {
				number.set(k, left.get(i));
				i++;
				k++;
			}

			while (j < right.size()) {
				number.set(k, right.get(j));
				j++;
				k++;
			}

		}

		return number;
	}

}
