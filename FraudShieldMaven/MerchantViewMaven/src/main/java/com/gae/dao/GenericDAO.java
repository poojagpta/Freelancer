package com.gae.dao;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface GenericDAO<T> {
	
/*	public Key<T> create(T item) throws Exception;
	
	public boolean update(T item) throws Exception;
	
	public boolean remove(T item) throws Exception;*/
	
	Map<Key, Entity> getEntityMap(List<Key> keyList);
	
}
