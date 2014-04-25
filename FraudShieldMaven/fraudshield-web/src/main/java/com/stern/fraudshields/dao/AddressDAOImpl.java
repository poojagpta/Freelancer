package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.BaseModel;

public class AddressDAOImpl implements AddressDAO{

	private static final Logger log = LoggerFactory
			.getLogger(AddressDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
		
	public AddressDAOImpl(ObjectifyFactory objectifyFactory) {
	
		this.objectifyFactory = objectifyFactory;
	}

	public AddressDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to addressDao: "
					+ objectifyFactory.toString());
	}

	@Override
	public Key<Address> create(Address item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Address> addressKey= ofy.put(item);
		
		return addressKey;
	}

	@Override
	public boolean update(Address item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this address already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(Address.class)
											  .ancestor(item.getId())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this address already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This Address doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(Address item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Address getAddress(Long id) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getAddress");
		}
		Address fetched = ofy.query(Address.class).filter("id", id).get();
		return fetched;
	}

	
}
