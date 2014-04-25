package com.gae.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gae.bean.SearchInput;
import com.gae.model.User;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class UserDAOImpl implements UserDAO{

	/*private static final Logger log = LoggerFactory
			.getLogger(PaymentGatewayDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
	public UserDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public UserDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to this dao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<User> create(User item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<User> userKey=ofy.put(item);
		return userKey;
		
	}

	@Override
	public boolean update(User item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this PaymentGateway already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(User.class)
											  .ancestor(item.getLoginName())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this PaymentGateway already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This PaymentGateway doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(User item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public User getUser(String userName) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getCreditCard");
		}
		User fetched = ofy.query(User.class).filter("loginName",userName).get();
		return fetched;
	}
*/
	
	public User getUser(String userName) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		User user = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(KeyFactory.createKey("Customer", userName));
			user = new User();
			user.setLoginName(entity.getKey().getName());
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return user;
		
	}
	public User getUser(Key key) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		User user = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(key);
			user = new User();
			user.setLoginName((String)entity.getProperty("loginName"));
			user.setFirstName((String)entity.getProperty("firstName"));
			user.setMiddleName((String)entity.getProperty("middleName"));
			user.setLastName((String)entity.getProperty("laststName"));
			user.setAddress((List<Key>)entity.getProperty("address"));
			user.setOrderDetails((List<Key>)entity.getProperty("transactionDetails"));
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return user;
		
	}
	public Map<Key,Entity> getEntityMap(List<Key> keyList){
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Map<Key,Entity> entityMap = null;
		
		try {
			entityMap = dataStoreService.get(keyList);
		
		} catch (Exception e) {
			
		}
		return entityMap;
	}
	

	public List<User> getUserList(SearchInput input,Long userId, String cursor, int limit, int offset){
		
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		//Filter merchantFilter = new Query.FilterPredicate("merchant", FilterOperator.IN, input.getMerchantKeyList());
		
		List<Filter> filterList = new ArrayList<Filter>();
		
		if(userId != null){
			Filter userFilter = new Query.FilterPredicate("userId",FilterOperator.EQUAL,userId);
			filterList.add(userFilter);
		}
		
		if(input != null){
			if(input.getUserName() != null && !input.getUserName().equals("")){
				Filter nameFilter = new Query.FilterPredicate("loginName",FilterOperator.EQUAL,input.getUserName());
				filterList.add(nameFilter);
			}
			
			if(input.getEmail() != null && !input.getEmail().equals("")){
				Filter emailFilter = new Query.FilterPredicate("email",FilterOperator.EQUAL,input.getEmail());
				filterList.add(emailFilter);
			}
		}
		Filter searchFilter = filterList.get(0);
		
		if(filterList.size() >= 2)
			searchFilter = CompositeFilterOperator.and(filterList);
		
		
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
		
		
	    if (cursor != null) {
	      fetchOptions.startCursor(Cursor.fromWebSafeString(cursor));
	    }

		
		fetchOptions.offset(offset);
		
		
		Query query = new Query("Customer").setFilter(searchFilter);
		PreparedQuery preparedQuery = dataStoreService.prepare(query);
		
		List<User> userList = new ArrayList<User>();
		
		for (Entity entity : preparedQuery.asIterable()) {
			
			User user = new User();
			user.setLoginName((String)entity.getProperty("loginName"));
			user.setFirstName((String)entity.getProperty("firstName"));
			user.setMiddleName((String)entity.getProperty("middleName"));
			user.setLastName((String)entity.getProperty("laststName"));
			user.setAddress((List<Key>)entity.getProperty("address"));
			user.setOrderDetails((List<Key>)entity.getProperty("transactionDetails"));
			userList.add(user);
		}
		
		return userList;
	}
	
	public List<User> getUserList(Long userId){
		
		return getUserList(null,userId,null,10,10);
		
	}
}
