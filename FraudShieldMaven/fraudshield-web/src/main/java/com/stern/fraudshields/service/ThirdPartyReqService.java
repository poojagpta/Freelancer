package com.stern.fraudshields.service;

import java.util.List;

import com.stern.fraudshields.model.ThirdPartyReq;

public interface ThirdPartyReqService {

	ThirdPartyReq getThirdPartyReq(String id);

	boolean update(ThirdPartyReq item) throws Exception;

	List<ThirdPartyReq> getThirdPartyPendingReq();
}
