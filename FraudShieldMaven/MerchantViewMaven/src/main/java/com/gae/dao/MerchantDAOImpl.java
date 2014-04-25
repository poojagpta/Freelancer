package com.gae.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gae.bean.SearchInput;
import com.gae.model.Merchant;
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

public class MerchantDAOImpl implements MerchantDAO{

	/*private static final Logger log = LoggerFactory.getLogger(MerchantDAOImpl.class);

	private ObjectifyFactory objectifyFactory;

	public MerchantDAOImpl(ObjectifyFactory objectifyFactory) {
		
		this.objectifyFactory = objectifyFactory;
	}

	public MerchantDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to merchantDao: "
					+ objectifyFactory.toString());
	}
	
	@Override
	public Key<Merchant> create(Merchant item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Merchant> merchantKey=ofy.put(item);	
		return merchantKey;
		
	}

	@Override
	public boolean update(Merchant item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this Merchant already exist " +
					"in the datastore: " + item.toString());
		boolean thisMerchantAlreadyExist =  ofy.query(Merchant.class)
											  .ancestor(item.getSiteName())
											  .get() != null;
		
		if(thisMerchantAlreadyExist){
			log.info("Confirmed: this Merchant already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This Merchant doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(Merchant item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Merchant getMerchant(String siteName) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getMerchant");
		}
		Merchant fetched = ofy.query(Merchant.class).filter("siteName", siteName).get();
		return fetched;
	}
*/
	public Merchant getMerchant(String siteName) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Merchant merchant = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(KeyFactory.createKey("Merchant", siteName));
			merchant = new Merchant();
			merchant.setSiteName(entity.getKey().getName());
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return merchant;
		
	}
	
	public Key create(Entity entity){
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Key key = dataStoreService.put(entity);
		
		return key;
	}
	
	public List<Merchant> getMerchantList(SearchInput input,Long userId){
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		//Filter merchantFilter = new Query.FilterPredicate("merchant", FilterOperator.IN, input.getMerchantKeyList());
	
		List<Filter> filterList = new ArrayList<Filter>();
		
		if(userId != null){
			Filter userFilter = new Query.FilterPredicate("userId",FilterOperator.EQUAL,userId);
			filterList.add(userFilter);
		}
		
		Filter siteNameFilter = null;
		if(input.getMerchantName() != null){
			siteNameFilter = new Query.FilterPredicate("name",FilterOperator.EQUAL,input.getMerchantName());
			filterList.add(siteNameFilter);
		}
		
		Filter searchFilter = filterList.get(0);
		
		if(filterList.size() >= 2)
			searchFilter = CompositeFilterOperator.and(filterList);
		
		Query query = new Query("Merchant").setFilter(searchFilter);
		PreparedQuery preparedQuery = dataStoreService.prepare(query);
		
		List<Merchant> merchantList = new ArrayList<Merchant>();
		
		for (Entity result : preparedQuery.asIterable()) {
			Merchant merchant = new Merchant();
			merchant.setSiteName(result.getKey().getName());
			merchant.setSiteName((String)result.getProperty("name"));
			merchant.setSiteid((String)result.getProperty("siteid"));
			merchant.setOrderDetails((List<Key>)result.getProperty("transactionDetail"));
			merchantList.add(merchant);
		}
		
		return merchantList;
	}

	public Map getEntityMap(List keyList) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Map<Key,Entity> entityMap = null;
		
		try {
			entityMap = dataStoreService.get(keyList);
		} catch (Exception e) {
			
		}
		return entityMap;
		
	}

	
	public Merchant getMerchant(Key key) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Merchant merchant = null;
		Entity entity = null;
		try {
			entity = dataStoreService.get(key);
			merchant = new Merchant();
			merchant.setSiteName(entity.getKey().getName());
			merchant.setSiteName((String)entity.getProperty("name"));
			merchant.setSiteid((String)entity.getProperty("siteid"));
			merchant.setOrderDetails((List<Key>)entity.getProperty("transactionDetail"));
			
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return merchant;
		
	}
	
	
	
	
}
