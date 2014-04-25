package com.stern.fraudshields.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.dao.OfyService;
import com.stern.fraudshields.dao.ThirdPartyReqDAO;
import com.stern.fraudshields.dao.ThirdPartyReqDAOImpl;
import com.stern.fraudshields.model.ThirdPartyReq;

public class ThirdPartyReqServiceImpl implements ThirdPartyReqService {

	private static final Logger log = LoggerFactory
			.getLogger(ThirdPartyReqServiceImpl.class);

	/** Singleton instance */
	protected static ObjectifyFactory factory = OfyService.factory();
	private ThirdPartyReqDAO thirdPartyReqDao = new ThirdPartyReqDAOImpl(
			factory);

	@Override
	public ThirdPartyReq getThirdPartyReq(String id) {

		return thirdPartyReqDao.getThirdPartyReq(id);
	}

	@Override
	public boolean update(ThirdPartyReq item) throws Exception {

		return thirdPartyReqDao.update(item);
	}

	@Override
	public List<ThirdPartyReq> getThirdPartyPendingReq() {

		return thirdPartyReqDao.getThirdPartyPendingReq();
	}

}
