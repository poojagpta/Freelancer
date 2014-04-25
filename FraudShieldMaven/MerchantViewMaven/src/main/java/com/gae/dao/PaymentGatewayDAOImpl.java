package com.gae.dao;

import java.util.logging.Logger;

import com.gae.model.PaymentGateway;
import com.google.appengine.api.datastore.Key;

public class PaymentGatewayDAOImpl{

/*	private static final Logger log = LoggerFactory
			.getLogger(PaymentGatewayDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
		
	public PaymentGatewayDAOImpl(ObjectifyFactory objectifyFactory) {
	
		this.objectifyFactory = objectifyFactory;
	}

	public PaymentGatewayDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to this dao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<PaymentGateway> create(PaymentGateway item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<PaymentGateway> paymentGatewayKey=ofy.put(item);	
	    return paymentGatewayKey;	
	}

	@Override
	public boolean update(PaymentGateway item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this PaymentGateway already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(PaymentGateway.class)
											  .ancestor(item.getGateway_id())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this PaymentGateway already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This PaymentGateway doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(PaymentGateway item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public PaymentGateway getPaymentGateway(Long gateway_id) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getCreditCard");
		}
		PaymentGateway fetched = ofy.query(PaymentGateway.class).filter("gateway_id",gateway_id).get();
		return fetched;
	}

*/	
}
