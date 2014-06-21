package com.xyz.jvmerror;

import com.xyz.jvmerror.util.JavaEETrainingUtil;

public class NoClassDefFoundErrorSimulator {
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
           System.out.println("java.lang.NoClassDefFoundError Simulator - Training 1");
           System.out.println("Author: Pierre-Hugues Charbonneau");
           System.out.println("http://javaeesupportpatterns.blogspot.com");
          
           // Print current Classloader context
           System.out.println("\nCurrent ClassLoader chain: "+JavaEETrainingUtil.getCurrentClassloaderDetail());
          
           // 1. Create a new instance of CallerClassA
           CallerClassA caller = new CallerClassA();
          
           // 2. Execute method of the caller
           caller.doSomething();
          
           System.out.println("done!");
    }
}
