package com.gae.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gae.bean.SearchInput;
import com.gae.model.OrderDetail;
import com.gae.model.OrderStatus;
import com.gae.model.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class OrderDetailDAOImpl implements OrderDetailDAO{

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/*private static final Logger log = LoggerFactory
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
	}*/
	
	public Map<Key,Entity> getEntityMap(List<Key> keyList){
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Map<Key,Entity> entityMap = null;
		
		try {
			entityMap = dataStoreService.get(keyList);
		
		} catch (Exception e) {
			
		}
		return entityMap;
	}

	public OrderDetail getOrderDetail(String orderDetailId) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		OrderDetail orderDetail = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(KeyFactory.createKey("OrderDetail", orderDetailId));
			orderDetail = new OrderDetail();
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("billingAddress")));
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("shippingAddress")));
			orderDetail.setOrderStatus(OrderStatus.valueOf((String)entity.getProperty("orderStatus")));
			orderDetail.setUser(KeyFactory.stringToKey((String)entity.getProperty("customer")));
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return orderDetail;
		
	}
	
	public OrderDetail getOrderDetail(Key orderDetailKey) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		OrderDetail orderDetail = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(orderDetailKey);
			orderDetail = new OrderDetail();
			orderDetail.setBillingAddress(((List<Key>)entity.getProperty("billingAddress")).get(0));
			orderDetail.setShippingAddress(((List<Key>)entity.getProperty("shippingAddress")).get(0));
			orderDetail.setOrderStatus(OrderStatus.findByValue((String)entity.getProperty("orderStatus")));
			orderDetail.setOrderType((String)entity.getProperty("orderType"));
			orderDetail.setOrderDate(dateFormatter.parse((String)entity.getProperty("orderDate")));
			orderDetail.setUser(((List<Key>)entity.getProperty("customer")).get(0));
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return orderDetail;
		
	}

	public List<OrderDetail> getOrderDetails(List<Key> addressKeyList) throws ParseException {
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		
		Filter addressFilter = null;
				
		addressFilter = CompositeFilterOperator.or(new Query.FilterPredicate("billingAddress",FilterOperator.IN,addressKeyList),
													new Query.FilterPredicate("shippingAddress",FilterOperator.IN,addressKeyList));
		
		Query query = new Query("OrderDetail").setFilter(addressFilter);
		PreparedQuery preparedQuery = dataStoreService.prepare(query);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		
		for (Entity entity : preparedQuery.asIterable()) {
			OrderDetail order = new OrderDetail();
			if(entity.getProperty("billingAddress") != null)
				order.setBillingAddress(((List<Key>)entity.getProperty("billingAddress")).get(0));
			if(entity.getProperty("shippingAddress") != null)
				order.setShippingAddress(((List<Key>)entity.getProperty("shippingAddress")).get(0));
			order.setOrderDate(dateFormatter.parse((String)entity.getProperty("orderDate")));
			order.setOrderStatus(OrderStatus.findByValue((String)entity.getProperty("orderStatus")));
			order.setGrandTotal((String)entity.getProperty("grandTotal"));
			order.setOrderType((String)entity.getProperty("orderType"));
			order.setProductList((List<Key>)entity.getProperty("productList"));
			order.setSalesRep((String)entity.getProperty("salesRep"));
			if(entity.getProperty("customer") != null)
				order.setUser(((List<Key>)entity.getProperty("customer")).get(0));
			if(entity.getProperty("merchant") != null)
				order.setMerchant(((List<Key>)entity.getProperty("merchant")).get(0));
			orderDetailList.add(order);
		}
		
		return orderDetailList;
		
	}

	public List<OrderDetail> getOrderDetailList(SearchInput input,Long userId) throws ParseException{
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		//Filter merchantFilter = new Query.FilterPredicate("merchant", FilterOperator.IN, input.getMerchantKeyList());
		Filter dateFilter = null;
		Filter statusFilter = null;
		List<Filter> filterList = new ArrayList<Filter>();
				
		if(userId != null){
			Filter userFilter = new Query.FilterPredicate("userId",FilterOperator.EQUAL,userId);
			filterList.add(userFilter);
		}
		
		if(input != null){
			if(input.getFromDate() != null){
				dateFilter = CompositeFilterOperator.or(new Query.FilterPredicate("orderDate",FilterOperator.GREATER_THAN_OR_EQUAL,input.getFromDate()),
													new Query.FilterPredicate("orderDate",FilterOperator.LESS_THAN_OR_EQUAL,input.getToDate()));
				filterList.add(dateFilter);
			}
			
			if(input.getOrderStatus() != null){
				statusFilter = new Query.FilterPredicate("orderStatus",FilterOperator.EQUAL,input.getOrderStatus().getOrderStatus());
				filterList.add(statusFilter);
			}
		}
		Filter searchFilter = filterList.get(0);
		
		if(filterList.size() >= 2)
			searchFilter = CompositeFilterOperator.and(filterList);
		
		Query query = new Query("OrderDetail").setFilter(searchFilter);
		
		PreparedQuery preparedQuery = dataStoreService.prepare(query);
		
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		
		for (Entity entity : preparedQuery.asIterable()) {
			OrderDetail order = new OrderDetail();
			if(entity.getProperty("billingAddress") != null){
				order.setBillingAddress(((List<Key>)entity.getProperty("billingAddress")).get(0));
			}
			if(entity.getProperty("shippingAddress") != null){
				order.setShippingAddress(((List<Key>)entity.getProperty("shippingAddress")).get(0));
			}
			order.setOrderDate(dateFormatter.parse((String)entity.getProperty("orderDate")));
			order.setOrderStatus(OrderStatus.findByValue((String)entity.getProperty("orderStatus")));
			order.setGrandTotal((String)entity.getProperty("grandTotal"));
			order.setOrderType((String)entity.getProperty("orderType"));
			order.setProductList((List<Key>)entity.getProperty("productList"));
			order.setSalesRep((String)entity.getProperty("salesRep"));
			if(entity.getProperty("customer") != null){
				order.setUser(((List<Key>)entity.getProperty("customer")).get(0));
			}
			orderDetailList.add(order);
		}
		
		return orderDetailList;
	}
	
	public List<OrderDetail> getOrderDetailList(Long userId){
		
		try {
			return getOrderDetailList(null, userId);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}

}
