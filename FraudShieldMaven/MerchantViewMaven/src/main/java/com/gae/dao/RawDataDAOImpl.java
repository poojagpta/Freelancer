package com.gae.dao;

import java.util.logging.Logger;

import com.gae.model.OrderDetail;
import com.gae.model.RawData;
import com.google.appengine.api.datastore.Key;

public class RawDataDAOImpl{

	/*private static final Logger log = LoggerFactory
			.getLogger(RawDataDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
	public RawDataDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to accountDao: "
					+ objectifyFactory.toString());
		
	}


	public RawDataDAOImpl(ObjectifyFactory objectifyFactory) {
			this.objectifyFactory = objectifyFactory;
	}

	

	public RawData getRawData(Long id) {
		Objectify ofy=objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getRawData");
		}
		RawData fetched = ofy.query(RawData.class)
				.filter("id", id).get();
		return fetched;
	}


	@Override
	public Key<RawData> create(RawData item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<RawData> rawDataKey=ofy.put(item);
		return rawDataKey;
	}


	@Override
	public boolean update(RawData item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this raw data already exist " +
					"in the datastore: " + item.toString());
		boolean thisOrderDetailAlreadyExist =  ofy.query(OrderDetail.class)
											  .ancestor(item.getId())
											  .get() != null;
		
		if(thisOrderDetailAlreadyExist){
			log.info("Confirmed: this raw data already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This raw data doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}


	@Override
	public boolean remove(RawData item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

*/	
	
}
