package com.gae.dao;

import com.gae.model.PaymentGateway;

public interface PaymentGatewayDAO extends GenericDAO<PaymentGateway>{

	PaymentGateway getPaymentGateway(Long gateway_id);
}
