package com.xyz.gc;

import java.util.WeakHashMap;

/**
 * This this class is used for garbage collector functioning.
 * 1. Finalized method call before garbage collection.
 * 2. SoftReference/WeakReference/Phantom Reference   
 *  
 * 
 * 3. Generate HPROF file (heap dump) for analysis of memory useage.
 * Take snapshot of heap (heap dump) by jconsole ( In java_home/bin folder) and analyse it using eclipse MAT
 * Or can take heap dump using jvisualvm
 * 
 * @author pooja
 *
 */
public class GCImpl implements Runnable{

	@Override
	public void run() {
		
		WeakHashMap<GCInfo,String> gcinfos=new WeakHashMap<GCInfo,String>(1000000);
		  for(int i=0;i<1000000;i++){
			  GCInfo infoNew=new GCInfo("Val"+i, i);
			   gcinfos.put(infoNew,"Valu"+i);
			  infoNew=null;
			  System.out.println("Value of"+i);
		  }
		
		  //Free all the collection for garbage collection
		  for(GCInfo gcInfo:gcinfos.keySet()){
			  gcInfo=null;
		  }
		  gcinfos=null;
		  		
	}
	
	public static void main(String str[]){
		Thread t=new Thread(new GCImpl());
		t.start();
	}

}
