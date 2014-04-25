package com.stern.fraudshields.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.dao.OfyService;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.RawData;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.Product;
import com.stern.fraudshields.model.Customer;




/**
 */
public final class ContextInitializer implements ServletContextListener {
	
	private static final Logger log = LoggerFactory.getLogger(ContextInitializer.class);
	
    @Override
    public void contextInitialized(final ServletContextEvent sce) {
//    	ObjectifyService.factory().register(PaymentGateway.class);
//    	ObjectifyService.factory().register(Merchant.class);
//        ObjectifyService.factory().register(Customer.class);
//        ObjectifyService.factory().register(Address.class);
//        ObjectifyService.factory().register(CreditCard.class);
//        ObjectifyService.factory().register(OrderDetail.class);
//        ObjectifyService.factory().register(Product.class);
//        ObjectifyService.factory().register(RawData.class);
    	log.info("**************contextInitialized");
        
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce) {
        // empty
    	log.info("**************contextDestroyed");
    }
}
