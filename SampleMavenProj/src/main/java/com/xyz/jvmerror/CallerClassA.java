package com.xyz.jvmerror;

import com.xyz.jvmerror.util.JavaEETrainingUtil;

public class CallerClassA {
    
    private final static String CLAZZ = CallerClassA.class.getName();
   
    static {
           System.out.println("Classloading of "+CLAZZ+" in progress..."+JavaEETrainingUtil.getCurrentClassloaderDetail());
    }
   
    public CallerClassA() {
           System.out.println("Creating a new instance of "+CallerClassA.class.getName()+"...");
    }
   
    public void doSomething() {
          
           // Create a new instance of ReferencingClassA
           ReferencingClassA referencingClass = new ReferencingClassA();             
    }
}