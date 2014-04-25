package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.CreditCard;

public class CreditCardDAOImpl implements CreditCardDAO{
	
	private static final Logger log = LoggerFactory
			.getLogger(CreditCardDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public CreditCardDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public CreditCardDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to addressDao: "
					+ objectifyFactory.toString());
	}

	
	@Override
	public Key<CreditCard> create(CreditCard item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<CreditCard> creditCardKey=ofy.put(item);		
		return creditCardKey;
	}

	@Override
	public boolean update(CreditCard item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this creditCard already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(CreditCard.class)
											  .ancestor(item.getBin())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this creditCard already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This creditCard doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(CreditCard item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public CreditCard getCreditCard(Long bin) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getCreditCard");
		}
		CreditCard fetched = ofy.query(CreditCard.class).filter("bin", bin).get();
		return fetched;
	}

}
