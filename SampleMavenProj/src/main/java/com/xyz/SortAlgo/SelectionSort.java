package com.xyz.SortAlgo;
/**
 * List: 8,11,44,3,77,1
 * Find position of greatest number: 
 * Iterated List and find position 4th
 * Swap the last number with no at 4th position.
 * So, Now List: 8,11,44,3,1,77
 * 
 * List for 2nd iteration: 8,11,44,3,1
 * Largest No position: 2nd position,swap
 * List:8,11,1,3,44
 * 
 * And so on
 * 
 * Same iteration as in Bubble sort
 * But less never of exchanges
 * 
 * 
 * @author pooja
 *
 */
public class SelectionSort {

	public static void main(String str[]){
		int[] number={8,11,44,3,77,1,8,4,55,99};
		int count = number.length-1;
		int citer=0;
		while(count>0){			
			int position=0;
			for(int i=0;i<count;i++){
				if(number[i]>number[position]){
					position=i;					
				}
				citer++;
			}
			int temp=number[position];
			number[position]=number[count];
			number[count]=temp;
			count--;
		}
		
		for(int no:number){
			System.out.println(no+" ");
		}
		
		System.out.println("Iterations------>"+citer);
		
	}
	
	
}
