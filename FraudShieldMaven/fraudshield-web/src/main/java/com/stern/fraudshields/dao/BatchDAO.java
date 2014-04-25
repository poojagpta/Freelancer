package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.OrderDetail;


public interface BatchDAO<T> {

	void persistObjects(Iterable<T> objectsList) throws Exception;

	OrderDetail fetchOrder(String orderId) throws Exception; 
	
}
