package com.gae.dao;

import java.util.logging.Logger;

import com.gae.model.MerchantMapping;
import com.google.appengine.api.datastore.Key;

public class MerchantMappingDAOImpl {

	/*private static final Logger log = LoggerFactory
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
*/
}
