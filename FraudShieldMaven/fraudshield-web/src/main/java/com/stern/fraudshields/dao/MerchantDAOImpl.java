package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Merchant;

public class MerchantDAOImpl implements MerchantDAO{

	private static final Logger log = LoggerFactory
			.getLogger(MerchantDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public MerchantDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public MerchantDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to merchantDao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<Merchant> create(Merchant item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Merchant> merchantKey=ofy.put(item);	
		return merchantKey;
		
	}

	@Override
	public boolean update(Merchant item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this Merchant already exist " +
					"in the datastore: " + item.toString());
		boolean thisMerchantAlreadyExist =  ofy.query(Merchant.class)
											  .ancestor(item.getSiteName())
											  .get() != null;
		
		if(thisMerchantAlreadyExist){
			log.info("Confirmed: this Merchant already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This Merchant doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(Merchant item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Merchant getMerchant(String siteName) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getMerchant");
		}
		Merchant fetched = ofy.query(Merchant.class).filter("siteName", siteName).get();
		return fetched;
	}

	
}
