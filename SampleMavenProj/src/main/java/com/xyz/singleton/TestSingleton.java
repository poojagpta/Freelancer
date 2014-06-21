package com.xyz.singleton;

import java.io.Serializable;

public class TestSingleton implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static TestSingleton testsingleton = new TestSingleton();

	public String name;
	public int age;

	private TestSingleton() {

	}

	public static TestSingleton getInstance(String name, int age) {
		testsingleton.setName(name);
		testsingleton.setAge(age);
		return testsingleton;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "TestSingleton [name=" + name + ", age=" + age + "]";
	}

	/**
	 * This method is use to remove Serialization
	 * 
	 * @return
	 */
	private Object readResolve() {
		// return the true instance
		return testsingleton;
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		//return super.clone();
		
		throw new CloneNotSupportedException();
	}
	
	
}
