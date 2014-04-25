package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.OrderDetail;

public interface OrderDetailDAO extends GenericDAO<OrderDetail>{

	OrderDetail getOrderDetail(String orderDetailId);

}
