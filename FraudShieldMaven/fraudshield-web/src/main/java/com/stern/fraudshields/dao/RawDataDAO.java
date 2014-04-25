package com.stern.fraudshields.dao;

import com.stern.fraudshields.model.RawData;

public interface RawDataDAO  extends GenericDAO<RawData>{

	RawData getRawData(Long id);
}
