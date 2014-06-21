package com.xyz.SortAlgo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSort {

	public static void main(String str[]){
		Integer[] number = { 54,26,93,17,77,31,44,55,20 };

		QuickSort sort=new QuickSort();
		 List newList=sort.quicksort(new ArrayList<Integer>(Arrays.asList(number)));

		for (Object intVal : newList) {
			System.out.println(intVal);
		}
		
	}
	
	public List quicksort(List number){
		
		if(number.size()>1){
			
		     int pivotVal=(Integer)number.get(0);
		     int i=1,j=number.size()-1;
		     while(i<j && i<number.size() && j<number.size()){
		    	 int leftpoint=(Integer)number.get(i);
		    	 int rightpoint=(Integer)number.get(j);
		    	     		 
	    		 if(leftpoint>rightpoint){
	    			number.set(j,leftpoint);
		    		number.set(i,rightpoint); 
	    		 }
	    		 
	    		//finding leftPoint
		    	 if(pivotVal>leftpoint){
		    	   i++; 
		    	 }
		    	 
		    	 //Finding rightPoint
	    		 if(pivotVal<rightpoint){
	    			j--;
	    		 }		    	
		     }
			
		     //Replace right point with pivotVal
		     if((Integer)number.get(j)<pivotVal){
		    	 number.set(0,number.get(j));
		    	 number.set(j, pivotVal);
		    	 
		    	 List left=number.subList(0, j);
		    	 quicksort(left);
		    	 if(!(j+1>number.size()-1)){
		    		 List right=number.subList(j+1, number.size()-1);	
		    		 quicksort(right);
		    	 } 	
		     }  
		     else{
		    	 quicksort(number.subList(1, number.size()-1));		    	 
		     }
		}		
	
		return number;
	}	
	
	
	
	
}
