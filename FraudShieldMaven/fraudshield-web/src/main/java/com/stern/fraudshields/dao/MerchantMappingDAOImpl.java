package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.MerchantMapping;

public class MerchantMappingDAOImpl implements MerchantMappingDAO {

	private static final Logger log = LoggerFactory
			.getLogger(MerchantMappingDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public MerchantMappingDAOImpl(ObjectifyFactory objectifyFactory) {

		this.objectifyFactory = objectifyFactory;
	}

	public MerchantMappingDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to addressDao: "
					+ objectifyFactory.toString());
	}

	@Override
	public Key<MerchantMapping> createMappingFile(MerchantMapping merchant) {

		Objectify ofy = objectifyFactory.begin();
		Key<MerchantMapping> merchantKey = ofy.put(merchant);
		return merchantKey;
	}

	@Override
	public MerchantMapping getMerchantMapping(String id) {

		Objectify ofy = objectifyFactory.begin();
		return ofy.get(MerchantMapping.class, id);

	}


}
