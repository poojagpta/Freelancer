package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.RawData;
import com.stern.fraudshields.model.Customer;

public class UserDAOImpl implements UserDAO{

	
	private static final Logger log = LoggerFactory
			.getLogger(PaymentGatewayDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
	public UserDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public UserDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to this dao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<Customer> create(Customer item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Customer> userKey=ofy.put(item);
		return userKey;
		
	}

	@Override
	public boolean update(Customer item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this PaymentGateway already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(Customer.class)
											  .ancestor(item.getLoginName())
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
	public boolean remove(Customer item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Customer getUser(String userName) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getCreditCard");
		}
		Customer fetched = ofy.query(Customer.class).filter("loginName",userName).get();
		return fetched;
	}

}
