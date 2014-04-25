package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.OrderDetail;

public class OrderDetailDAOImpl implements OrderDetailDAO {

	private static final Logger log = LoggerFactory
			.getLogger(OrderDetailDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public OrderDetailDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to accountDao: "
					+ objectifyFactory.toString());
		
	}


	public OrderDetailDAOImpl(ObjectifyFactory objectifyFactory) {
			this.objectifyFactory = objectifyFactory;
	}


	public OrderDetail getOrderDetail(String orderDetailId) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getOrderDetail");
		}
		OrderDetail fetched = ofy.query(OrderDetail.class)
				.filter("orderDetailId", orderDetailId).get();
		return fetched;
	}

	public Key<OrderDetail> create(OrderDetail orderDetail) {
		Objectify ofy = objectifyFactory.begin();
		Key<OrderDetail> orderDetailKey=ofy.put(orderDetail);
		return orderDetailKey;
	}

	@Override
	public boolean update(OrderDetail orderDetail) throws Exception {
		log.info("update()");

		if(orderDetail == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this order detail already exist " +
					"in the datastore: " + orderDetail.toString());
		boolean thisOrderDetailAlreadyExist =  ofy.query(OrderDetail.class)
											  .ancestor(orderDetail.getOrderDetailId())
											  .get() != null;
		
		if(thisOrderDetailAlreadyExist){
			log.info("Confirmed: this order detail already exist.");
			ofy.put(orderDetail);
			return true;
		}else{
			log.info("This order detail doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(OrderDetail orderDetail) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(orderDetail);
		return true;
	}

}
