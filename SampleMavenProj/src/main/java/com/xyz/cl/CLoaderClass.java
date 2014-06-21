package com.xyz.cl;

//import com.uwea.CLoaderInterface;

public class CLoaderClass {

	public static void main(String str[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		
		/**
		 * We can set the class loader at thread context level also 
		 * Thread.currentThread().setContextClassLoader(new CustomClassLoader());
		 * 
		 * 
		 *  URL[] urls = ...
			ClassLoader originalClassLoader = Thread.currentThread().getClassLoader();
			ClassLoader newClassLoader = new JarSeekingURLClassLoader(new File(Config.getDynamicLibraryLocation());
 
			try {    
    				Thread.currentThread().setContextClassLoader(newClassLoader);
    				// write code to load new classes
    				Class.forName("com.dynamic.library.class");
				} finally {
    				Thread.currentThread().setCLassLoader(originalClassLoader);
				}
		 */
		
		
		//CustomClassLoader loader=new CustomClassLoader();
		//Class clInterface= loader.loadClass("com.uwea.CLoader");
		//CLoaderInterface clInterface=(CLoaderInterface)Class.forName("CLoader", true, loader);
		//CLoaderInterface test=(CLoaderInterface)clInterface.newInstance();
		//System.out.println(test.myName("Test Class "));
	}
	
	
}
