package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.MerchantMapping;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.Product;
import com.stern.fraudshields.model.RawData;
import com.stern.fraudshields.model.ThirdPartyReq;
import com.stern.fraudshields.model.Customer;

public class OfyService {

	private static final Logger log = LoggerFactory.getLogger(OfyService.class);
    private static final ObjectifyFactory factory = new ObjectifyFactory();
    
	static {
		factory().register(Address.class);
		factory().register(PaymentGateway.class);
		factory().register(Merchant.class);

		factory().register(Customer.class);

		factory().register(CreditCard.class);
		factory().register(OrderDetail.class);
		factory().register(Product.class);
		factory().register(RawData.class);
		factory().register(MerchantMapping.class);
		factory().register(ThirdPartyReq.class);
		log.info("#############Static method callled#############################");

	}

	public static ObjectifyFactory factory() {
		log.info("#############Factory called  @@@@@@@@@@@@@@@@@@@");
		return factory;
		
	}

}
