package com.stern.fraudshields.service;

import com.stern.fraudshields.model.PaymentGateway;

public interface PaymentGatewayService {
	PaymentGateway getPaymentGateway(Long gateway_id);

}
