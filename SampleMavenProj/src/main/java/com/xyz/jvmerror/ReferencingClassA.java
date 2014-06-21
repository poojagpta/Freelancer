package com.xyz.jvmerror;

import com.xyz.jvmerror.util.JavaEETrainingUtil;

public class ReferencingClassA {

    private final static String CLAZZ = ReferencingClassA.class.getName();
   
    static {
           System.out.println("Classloading of "+CLAZZ+" in progress..."+JavaEETrainingUtil.getCurrentClassloaderDetail());
    }
   
    public ReferencingClassA() {
           System.out.println("Creating a new instance of "+ReferencingClassA.class.getName()+"...");
    }
   
    public void doSomething() {
           //nothing to do...
    }
}