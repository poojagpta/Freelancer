package com.stern.fraudshields.dao;

import java.util.List;

import com.stern.fraudshields.model.ThirdPartyReq;

public interface ThirdPartyReqDAO extends GenericDAO<ThirdPartyReq>{

	ThirdPartyReq getThirdPartyReq(String id);
	List<ThirdPartyReq> getThirdPartyPendingReq();	
}
