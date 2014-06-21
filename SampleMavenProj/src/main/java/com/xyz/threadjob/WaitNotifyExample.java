package com.xyz.threadjob;

public class WaitNotifyExample {

	public static void main(String str[]) {
		
		ThreadB b1 = new ThreadB(3);        
        ThreadB b2 = new ThreadB(8);        
        ThreadB b3 = new ThreadB(4);   
        
        ThreadA a1 = new ThreadA(b1);
		a1.setName("A1");
		ThreadA a2 = new ThreadA(b2);
		a2.setName("A2");
		ThreadA a3 = new ThreadA(b3);
		a3.setName("A3");
		a1.start();
		a2.start();
		a3.start();
		
	}

	static class ThreadA extends Thread {

		ThreadB threadB;

		public ThreadA(ThreadB threadB) {
			this.threadB = threadB;
		}

		public void run() {
			synchronized (threadB) {
				threadB.start();
				try {
					threadB.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			System.out.println(threadB.getTotal());
		}

	}

	static class ThreadB extends Thread {
		private int total = 0;
		private int count = 0;

		public ThreadB(int num) {
			this.count = num;
		}

		public void run() {
			synchronized (this) {
				for (int i = 0; i < count; i++) {
					total += i;
					//System.out.println("Value of i " + i);
				}

				notifyAll();
			}
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

	}

}
