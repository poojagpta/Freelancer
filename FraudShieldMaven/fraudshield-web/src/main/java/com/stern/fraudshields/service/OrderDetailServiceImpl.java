package com.stern.fraudshields.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stern.fraudshields.dao.OrderDetailDAO;
import com.stern.fraudshields.dao.OrderDetailDAOImpl;
import com.stern.fraudshields.model.OrderDetail;

public class OrderDetailServiceImpl implements OrderDetailService {

	private static final Logger log = LoggerFactory
			.getLogger(OrderDetailServiceImpl.class);

	private OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

	public OrderDetail getOrderDetail(String orderDetailId) {
		OrderDetail orderDetail = null;
		try {
			orderDetail = orderDetailDAO.getOrderDetail(orderDetailId);
		} catch (final Throwable tr) {
			if (log.isErrorEnabled()) {
				log.error("Cought Exception: " + tr.getMessage());
				log.debug("StackTrace:", tr);
			}
		}
		return orderDetail;

	}

}
