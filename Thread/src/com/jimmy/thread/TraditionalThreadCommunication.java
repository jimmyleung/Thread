package com.jimmy.thread;

public class TraditionalThreadCommunication {

	public static void main(String[] args) {
		
		final Business business = new Business();
		
		new Thread(new Runnable(){
			public void run(){
				for(int i = 0; i < 50; i++){
					business.sub();
				}
			}
			
		}).start();
		
		for(int i = 0; i < 50; i++){
			business.main();
		}
		

	}
	
	static class Business{
		boolean bShouldSub = true;
		public synchronized void sub() { 
			//这里使用while会更好，避免伪唤醒，因为会一直检查while是否正确
			//wait的源代码也是使用while的
			//if (!bShouldSub) {
			while(!bShouldSub) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int j = 0; j <  10; j++){
				System.out.println("child thread:" + j );
			}
			bShouldSub = false;
			this.notify();
		}
		
		public synchronized void main(){
			//if (bShouldSub) {
			while(bShouldSub) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int j = 0; j <  10; j++){
				System.out.println("main thread:" + j );
			}
			bShouldSub = true;
			this.notify();
		}
	}

}
