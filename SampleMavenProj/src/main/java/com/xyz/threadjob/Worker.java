package com.xyz.threadjob;

public class Worker {

	public static void main(String str[]){
		
		Thread one=new Thread(new Job(),"one");
		Thread two=new Thread(new Job(),"two");
	/*	one.setPriority(10);
		two.setPriority(10);*/
		one.start();
		two.start();
		
		
		try {
			one.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
}
