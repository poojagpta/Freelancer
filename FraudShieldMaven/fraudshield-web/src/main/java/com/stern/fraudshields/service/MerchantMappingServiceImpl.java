package com.stern.fraudshields.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.dao.MerchantMappingDAO;
import com.stern.fraudshields.dao.MerchantMappingDAOImpl;
import com.stern.fraudshields.dao.OfyService;
import com.stern.fraudshields.model.MerchantMapping;

public class MerchantMappingServiceImpl implements MerchantMappingService {

	private static final Logger log = LoggerFactory
			.getLogger(MerchantMappingServiceImpl.class);
	
	
		 
	/** Singleton instance */
	protected static ObjectifyFactory factory = OfyService.factory();
	private MerchantMappingDAO blobDataDAO = new MerchantMappingDAOImpl(factory);
	
	@Override
	public Key<MerchantMapping> createMappingFile(MerchantMapping merchant){
		
		return blobDataDAO.createMappingFile(merchant);
	}

	@Override
	public MerchantMapping getMerchantMapping(String id) {
		// TODO Auto-generated method stub
		return blobDataDAO.getMerchantMapping(id);
	}

}
