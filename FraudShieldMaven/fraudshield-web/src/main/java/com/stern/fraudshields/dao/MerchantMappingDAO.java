package com.stern.fraudshields.dao;

import com.googlecode.objectify.Key;
import com.stern.fraudshields.model.MerchantMapping;

public interface MerchantMappingDAO {

	
	public Key<MerchantMapping> createMappingFile(MerchantMapping merchant);
	public MerchantMapping getMerchantMapping(String id);
	
}
