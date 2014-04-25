package com.stern.fraudshields.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.ThirdPartyReq;

public class ThirdPartyReqDAOImpl implements ThirdPartyReqDAO {

	private static final Logger log = LoggerFactory
			.getLogger(ThirdPartyReqDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public ThirdPartyReqDAOImpl(ObjectifyFactory objectifyFactory) {

		this.objectifyFactory = objectifyFactory;
	}

	public ThirdPartyReqDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to thirdPartyReqDao: "
					+ objectifyFactory.toString());
	}

	@Override
	public Key<ThirdPartyReq> create(ThirdPartyReq item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<ThirdPartyReq> thirdPartyReqKey = ofy.put(item);

		return thirdPartyReqKey;
	}

	@Override
	public boolean update(ThirdPartyReq item) throws Exception {
		log.info("update()");

		if (item == null)
			return false;

		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this thirdPartyReq already exist "
				+ "in the datastore: " + item.toString());
		boolean thisThirdPartyReqAlreadyExist = ofy.query(ThirdPartyReq.class).filter("orderId",item.getOrderId()).get() != null;

		if (thisThirdPartyReqAlreadyExist) {
			log.info("Confirmed: this thirdPartyReq already exist.");
			ofy.put(item);
			return true;
		} else {
			log.info("This thirdPartyReq doesn't exist at the datastore or "
					+ "something whas wrong (might be the ancestor reference");
			return false;
		}

	}

	@Override
	public boolean remove(ThirdPartyReq item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public ThirdPartyReq getThirdPartyReq(String id) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getThirdPartyReq");
		}
		ThirdPartyReq fetched = ofy.query(ThirdPartyReq.class).filter("id", id)
				.get();
		return fetched;
	}

	@Override
	public List<ThirdPartyReq> getThirdPartyPendingReq() {

		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getThirdPartyPendingReq");
		}

		Query<ThirdPartyReq> q = ofy.query(ThirdPartyReq.class).filter(
				"status", "PENDING");
		List<ThirdPartyReq> thirdPartyReqList = new ArrayList<ThirdPartyReq>();

		for (ThirdPartyReq thirdPartyReq : q) {
			thirdPartyReqList.add(thirdPartyReq);
		}

		return thirdPartyReqList;
	}

}
