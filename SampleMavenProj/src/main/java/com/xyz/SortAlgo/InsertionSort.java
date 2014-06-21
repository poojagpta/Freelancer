package com.xyz.SortAlgo;
/**
 *  Create a sub sorted list and then keep on addition elements in between
 *  List: 80,11,44,3,77,1
 * 
 * Let take 1st iteration start from position 1 (currval=11, position=1, values more then 11 is move to right and currval place at 0): 11,80 --(sorted sub list)  ,44,3,77,1
 * 
 * Let take 2nd iteration  start from position 2(position=2,cuuval=44, value>44 move to right and currval placed at 1): 11,44,80,3,77,1
 * 
 * 
 * @author Pooja
 *
 */
public class InsertionSort {

	public static void main(String str[]){
		int[] number={100,11,44,3,77,1,8,4,55,79};
		
		int citer=0;
		for(int i=1;i<number.length;i++){
			int position =i;
			int currval=number[i];
			while(position>0 && number[position-1]>currval){
				number[position]=number[position-1];
				 position--;
				 citer++;
			}			
			number[position]=currval;
			
/*			while(position>=0){

                if(position>0 && currval < number[position-1] ) {
                	number[position]=number[position-1];
                   position--;
                }else{
                	number[position]=currval;
                    break;
                } 
            }*/
		}
		
		for(int no:number){
			System.out.print(no+" ");
		}
		
		System.out.println("Iterations------>"+citer);
	}
}
