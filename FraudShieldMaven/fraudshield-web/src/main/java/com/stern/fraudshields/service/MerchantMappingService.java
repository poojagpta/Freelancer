package com.stern.fraudshields.service;

import com.googlecode.objectify.Key;
import com.stern.fraudshields.model.MerchantMapping;

public interface MerchantMappingService {

	public Key<MerchantMapping> createMappingFile(MerchantMapping merchant);

	  MerchantMapping getMerchantMapping(String id);

	}
