package com.stern.fraudshields.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stern.fraudshields.dao.RawDataDAO;
import com.stern.fraudshields.dao.RawDataDAOImpl;
import com.stern.fraudshields.model.RawData;

public class RawDataServiceImpl implements RawDataService {

	private static final Logger log = LoggerFactory
			.getLogger(RawDataServiceImpl.class);

	private RawDataDAO rawDataDAO = new RawDataDAOImpl();
	
	@Override
	public RawData getRawData(Long id) {
		try {
			return rawDataDAO.getRawData(id);
		   } catch (final Throwable tr) {
			if (log.isErrorEnabled()) {
				log.error("Cought Exception: " + tr.getMessage());
				log.debug("StackTrace:", tr);
			}
		}
		
		return null;
	}
	
	@Override
	public void putRawData(RawData rawData) {
		
		try {
			rawDataDAO.create(rawData);
		   } catch (final Throwable tr) {
			if (log.isErrorEnabled()) {
				log.error("Cought Exception: " + tr.getMessage());
				log.debug("StackTrace:", tr);
			}
		}	
	}

}
