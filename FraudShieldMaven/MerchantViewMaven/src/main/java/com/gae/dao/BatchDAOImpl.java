package com.gae.dao;


public class BatchDAOImpl<T> {

	/*private static final Logger log = LoggerFactory
			.getLogger(BatchDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public BatchDAOImpl(ObjectifyFactory objectifyFactory) {

		this.objectifyFactory = objectifyFactory;
	}

	public BatchDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to addressDao: "
					+ objectifyFactory.toString());
	}

	@Override
	public void persistObjects(Iterable<T> objectsList) throws Exception {
		objectifyFactory.begin().put(objectsList);
	}
	
	
	public OrderDetail fetchOrder(String orderId) throws Exception {
		
		return objectifyFactory.begin().find(OrderDetail.class, orderId);
		
		
	}
*/
}
