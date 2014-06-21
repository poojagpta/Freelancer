package com.xyz.singleton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DriverManagerTest {
	
	public static void main(String[] str) {
		
		TestSingleton testSingleton=TestSingleton.getInstance("Pooja", 21);
		
		try {
			FileOutputStream fos=new FileOutputStream(new File("C:\\Users\\ashok\\Desktop\\Aviation\\serialA.ser"));
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(testSingleton);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			System.out.println("Error"+e.getMessage());
			e.printStackTrace();
		}
		
		//Clonning
		TestSingleton testSingleton1=null;
		try {
			testSingleton1 = (TestSingleton) testSingleton.clone();
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(testSingleton1!=null){
			testSingleton1.setName("Pooja1");
			testSingleton1.setAge(55);
			System.out.println("Clonning for singleton object----->"+testSingleton1.toString());
		}
		
		
		System.out.println("Clonning for singleton object----->"+testSingleton.toString());
		
		
		
       	//De-serialized
		TestSingleton testSingleton3=null;
		
		try{
			FileInputStream fis=new FileInputStream(new File("C:\\Users\\ashok\\Desktop\\Aviation\\serialA.ser"));
			ObjectInputStream ois=new ObjectInputStream(fis);
			testSingleton3=(TestSingleton)ois.readObject();
			testSingleton3.setName("Pooja1");
			
					
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		TestSingleton testSingleton4=null;
		
		try{
			FileInputStream fis=new FileInputStream(new File("C:\\Users\\ashok\\Desktop\\Aviation\\serialA.ser"));
			ObjectInputStream ois=new ObjectInputStream(fis);
			testSingleton4=(TestSingleton)ois.readObject();
					
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		testSingleton4.setName("Pooja4");
		
		System.out.println("This is test for 1--->"+testSingleton3.toString());
		System.out.println("This is test for 2--->"+testSingleton4.toString());
		
		
		
		
		
		
	}

}
