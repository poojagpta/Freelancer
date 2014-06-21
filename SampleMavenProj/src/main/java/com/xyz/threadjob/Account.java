package com.xyz.threadjob;

public class Account {
	
	private int balance;

	public int getBalance() {
		return balance;
	}

	public Account(int balance) {
		super();
		this.balance = balance;
	}

	public void withdrawl(int withdrawl){
		balance=balance- withdrawl;		
	}

}
