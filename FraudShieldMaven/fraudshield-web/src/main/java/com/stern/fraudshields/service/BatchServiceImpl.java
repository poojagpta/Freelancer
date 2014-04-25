package com.stern.fraudshields.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.dao.BatchDAO;
import com.stern.fraudshields.dao.BatchDAOImpl;
import com.stern.fraudshields.dao.OfyService;
import com.stern.fraudshields.model.OrderDetail;

public class BatchServiceImpl<T> implements BatchService<T> {

	private static final Logger log = LoggerFactory
			.getLogger(RawDataServiceImpl.class);

	/** Singleton instance */
	protected static ObjectifyFactory factory = OfyService.factory();

	private BatchDAO batchDataDAO = new BatchDAOImpl(factory);

	@Override
	public void persistObjects(Iterable<T> objectsList) throws Exception {
		batchDataDAO.persistObjects(objectsList);
	}

	@Override
	public OrderDetail fetchOrder(String orderId) throws Exception {
		// TODO Auto-generated method stub
		return batchDataDAO.fetchOrder(orderId);
	}
	
	
	
}
