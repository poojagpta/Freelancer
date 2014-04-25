package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.PaymentGateway;

public interface PaymentGatewayDAO extends GenericDAO<PaymentGateway>{

	PaymentGateway getPaymentGateway(Long gateway_id);
}
