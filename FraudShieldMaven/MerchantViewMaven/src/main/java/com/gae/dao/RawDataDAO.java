package com.gae.dao;

import com.gae.model.RawData;

public interface RawDataDAO  extends GenericDAO<RawData>{

	RawData getRawData(Long id);
}
