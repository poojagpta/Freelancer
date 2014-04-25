package com.stern.fraudshields.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.Product;

public class ProductDAOImpl implements ProductDAO{

	private static final Logger log = LoggerFactory
			.getLogger(PaymentGatewayDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
	public ProductDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public ProductDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to this dao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<Product> create(Product item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Product> productKey=ofy.put(item);			
		return productKey;
	}

	@Override
	public boolean update(Product item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this Product already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(Product.class)
											  .ancestor(item.getProductname())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this Product already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This Product doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(Product item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Product getProduct(String productName) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getCreditCard");
		}
		Product fetched = ofy.query(Product.class).filter("productname",productName).get();
		return fetched;
	}
	
	

}
