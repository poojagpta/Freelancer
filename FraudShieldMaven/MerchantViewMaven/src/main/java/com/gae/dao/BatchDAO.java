package com.gae.dao;

import com.gae.model.OrderDetail;

public interface BatchDAO<T> {

	void persistObjects(Iterable<T> objectsList) throws Exception;

	OrderDetail fetchOrder(String orderId) throws Exception; 
	
}
