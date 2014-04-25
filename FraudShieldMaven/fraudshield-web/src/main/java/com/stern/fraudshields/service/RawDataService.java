package com.stern.fraudshields.service;

import com.stern.fraudshields.model.RawData;

public interface RawDataService {

	RawData getRawData(Long id);
	void putRawData(RawData rawData);
	
}
