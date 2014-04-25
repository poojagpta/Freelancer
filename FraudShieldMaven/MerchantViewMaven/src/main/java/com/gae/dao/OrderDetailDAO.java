package com.gae.dao;

import com.gae.model.OrderDetail;

public interface OrderDetailDAO extends GenericDAO<OrderDetail>{

	OrderDetail getOrderDetail(String orderDetailId);

}
