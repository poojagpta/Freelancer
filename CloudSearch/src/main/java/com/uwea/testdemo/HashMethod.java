package com.uwea.testdemo;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;


public class HashMethod {
	
	public static void main(String str[]) throws Exception{
		
		
		 Properties prop = new Properties();
	     prop.setProperty("SearchDocument", "search-test99-z6enl66g4cmgjzmhgrov4v5k6y.us-east-1.cloudsearch.amazonaws.com");
	     prop.setProperty("DocUpload", "doc-test99-z6enl66g4cmgjzmhgrov4v5k6y.us-east-1.cloudsearch.amazonaws.com");
	     prop.store(new FileWriter("messages.properties"), "Search/Upload Document path");
	     
	     prop.setProperty("DocUpload_test99","search-test99-z6enl66g4cmgjzmhgrov4v5k6y.us-east-1.cloudsearch.amazonaws.com");
	     prop.setProperty("DocUpload_test778", "doc-test99-z6enl66g4cmgjzmhgrov4v5k6y.us-east-1.cloudsearch.amazonaws.com");
	     prop.store(new FileWriter("messages.properties"), "New prop");
	    
	     
	   Properties prop1 = new Properties();
	     FileReader reader = new FileReader("messages.properties");
	     prop1.load(reader);
	     System.out.println(prop1.getProperty("SearchDocument_test99"));
	     
	  /*  BufferedInputStream in= new BufferedInputStream(HashMethod.class.getResourceAsStream("messages.properties"));        
			byte[] contents = new byte[1024];

			int bytesRead=0;
			String policyDocument=""; 
			 while( (bytesRead = in.read(contents)) != -1){ 
				 policyDocument = new String(contents, 0, bytesRead);               
			 }*/
	     
		/*String searchEndpoint = Messages.getString("SearchDocument");
		System.out.println(searchEndpoint);*/
		
	}
	
	

}

