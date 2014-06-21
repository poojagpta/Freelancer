package com.xyz.threadjob;

public class WithdrawlJob implements Runnable{

	private Account account=new Account(50);
	
	public static void main(String str[]){
		WithdrawlJob withjob=new WithdrawlJob();
		Thread one=new Thread(withjob,"Fred");
		Thread two=new Thread(withjob,"Lucy");
		one.start();
		two.start();
		try {
			one.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			makeWithrawl(10);
			
			if(account.getBalance()<0){
				System.out.println("Account is over drawn"+account.getBalance());
			}
		}
	}

	
	private synchronized void makeWithrawl(int amt){
		if(account.getBalance()>amt){
			System.out.println(Thread.currentThread().getName()+" is going to withdrawl");
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			account.withdrawl(amt);
			System.out.println(Thread.currentThread().getName()+" has completed withdrawl");
			
		}else{
			System.out.println(Thread.currentThread().getName()+"is not able to withdrawl--->");
		}
	}
	
}
