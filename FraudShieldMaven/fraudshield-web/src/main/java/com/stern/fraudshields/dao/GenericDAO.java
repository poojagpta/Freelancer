package com.stern.fraudshields.dao;

import com.googlecode.objectify.Key;

public interface GenericDAO<T> {
	
	public Key<T> create(T item) throws Exception;
	
	public boolean update(T item) throws Exception;
	
	public boolean remove(T item) throws Exception;
	
}
