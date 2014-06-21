package com.xyz.SortAlgo;
/**
 * http://interactivepython.org/runestone/static/pythonds/SortSearch/sorting.html
 * 
 * 8,11,44,3,77,1
 * Compare 8,11 (no swap required) 
 *  11,44 (no swap required)
 * 44,3 (swap required) List 8,11,3,44,77,1
 * 44,77 (no swap required)
 * 77,1(swap required) List 8,11,3,44,1,77
 * After 1st Iteration list is: 8,11,3,44,1,77
 * 
 * Next iteration is from 8,11,3,44,1 
 * After 2nd iteration 8,3,11,1,44
 * After 3rd iteration 3,8,1,11
 * After 4th iteration 3,1,8
 * After 5th iteration 1,3 (Now list is sorted)
 * 
 * The total number of comparision is 
 * For 10 element, 1st iteration no of comparision is  9
 * 1st Iteration = (n-1)
 * 2nd iteration = (n-2)
 * 3rd iteration = (n-3)
 * Total=(n-1)+(n-2)+(n-3)+.....+1=(n-1)n/2
 * 
 * Worst sort but will be able to find the if list is already sorted early on 2 example
 * Make more exchanges
 * 
 * @author pooja
 *
 */
public class BubbleSort {

	public static void main(String str[]) {

		int[] number={8,11,44,3,77,1,8,4,55,99};
		int count = number.length-1;
		int citer=0;
		while(count>0){
			for(int i=0;i<count;i++){
				citer++;
				//Exchange two number
	             if(number[i]>number[i+1]){
	            	 int temp=number[i];
	            	 number[i]=number[i+1];
	            	 number[i+1]=temp;
	             }				
			}
			
			count--;			
		}
		
		for(int no:number){
			System.out.println(no+" ");
		}
		
		System.out.println("Iterations------>"+citer);
		
		int[] number1={8,11,44,3,77,1,8,4,55,99};
		int count1 = number1.length-1;
		int citer1=0;
		boolean exchange =true;
		while(count1>0 && exchange){
			exchange=false;
			for(int i=0;i<count1;i++){
				citer1++;
				//Exchange two number
	             if(number1[i]>number1[i+1]){
	            	 exchange=true;
	            	 int temp=number1[i];
	            	 number1[i]=number1[i+1];
	            	 number1[i+1]=temp;
	             }				
			}
			
			count1--;			
		}
		
		for(int no:number1){
			System.out.println(no+" ");
		}
		
		System.out.println("Iterations------>"+citer1);
		
		
		
		
	}

	
}
