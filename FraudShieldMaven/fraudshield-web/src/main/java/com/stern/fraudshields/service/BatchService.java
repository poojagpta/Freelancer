package com.stern.fraudshields.service;

import com.stern.fraudshields.model.OrderDetail;

public interface BatchService<T> {
	
	void persistObjects(Iterable<T> objectsList) throws Exception;
	OrderDetail fetchOrder(String orderId) throws Exception; 

}
