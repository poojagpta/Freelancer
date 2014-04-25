package com.gae.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gae.bean.SearchInput;
import com.gae.model.Address;
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
import com.google.appengine.api.datastore.Text;

public class AddressDAOImpl implements AddressDAO{


	/*private static final Logger log = LoggerFactory
			.getLogger(AddressDAOImpl.class);

	private ObjectifyFactory objectifyFactory;
	
		
	public AddressDAOImpl(ObjectifyFactory objectifyFactory) {
	
		this.objectifyFactory = objectifyFactory;
	}

	public AddressDAOImpl() {
		if (objectifyFactory != null)
			log.info("objectifyFactory was injected succesfully to addressDao: "
					+ objectifyFactory.toString());
	}

	@Override
	public Key<Address> create(Address item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		Key<Address> addressKey= ofy.put(item);
		
		return addressKey;
	}

	@Override
	public boolean update(Address item) throws Exception {
		log.info("update()");

		if(item == null)
			return false;
		
		Objectify ofy = objectifyFactory.begin();

		log.info("verify if this address already exist " +
					"in the datastore: " + item.toString());
		boolean thisAddressAlreadyExist =  ofy.query(Address.class)
											  .ancestor(item.getId())
											  .get() != null;
		
		if(thisAddressAlreadyExist){
			log.info("Confirmed: this address already exist.");
			ofy.put(item);
			return true;
		}else{
			log.info("This Address doesn't exist at the datastore or " +
					"something whas wrong (might be the ancestor reference");
			return false;
		}
	}

	@Override
	public boolean remove(Address item) throws Exception {
		Objectify ofy = objectifyFactory.begin();
		ofy.delete(item);
		return true;
	}

	@Override
	public Address getAddress(Long id) {
		Objectify ofy = objectifyFactory.begin();
		if (log.isDebugEnabled()) {
			log.debug("getAddress");
		}
		Address fetched = ofy.query(Address.class).filter("id", id).get();
		return fetched;
	}

*/	
	
	
	public Address getAddress(Long id) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Address address = null;
		
		try {
			Entity entity = datastoreService.get(KeyFactory.createKey("Address", id));
			address = new Address();
			address.setAddressl((String) entity.getProperty("address1"));
			
		}catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return address;
	}
	
	public Address getAddress(Key key) {
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Address address = null;
		
		try {
			Entity entity = datastoreService.get(key);
			address = new Address();
			address.setId(entity.getKey().getId());
			address.setAddressl((String)entity.getProperty("address1"));
			address.setAddress2((String)entity.getProperty("address2"));
			address.setCity((String) entity.getProperty("city"));
			address.setState((String) entity.getProperty("state"));
			address.setCountry((String) entity.getProperty("country"));
			address.setZip((String) entity.getProperty("zip"));
			
		}catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return address;
	}
	
	public Map<Key, Entity> getEntityMap(List<Key> keyList) {
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		Map<Key,Entity> entityMap = null;
		
		try {
			entityMap = dataStoreService.get(keyList);
		} catch (Exception e) {
			
		}
		return entityMap;
		
	}
	
	public List<Address> getAddressList(SearchInput input,Long userId){
		
		DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();
		
		Filter addressFilter = null;
		Filter cityFilter = null;
		Filter stateFilter = null;
		Filter countryFilter = null;
		List<Filter> filterList = new ArrayList<Query.Filter>();
		
		if(userId != null){
			Filter userFilter = new Query.FilterPredicate("userId",FilterOperator.EQUAL,userId);
			filterList.add(userFilter);
		}
		
		if(input != null){
			if(input.getAddress() != null){
				addressFilter = CompositeFilterOperator.or(new Query.FilterPredicate("address1",FilterOperator.EQUAL,input.getAddress()),
															new Query.FilterPredicate("address2",FilterOperator.EQUAL,input.getAddress()));
				filterList.add(addressFilter);
			}
			
			if(input.getCity() != null && !input.getCity().isEmpty()){
				cityFilter = new Query.FilterPredicate("city",FilterOperator.EQUAL,input.getCity());
				filterList.add(cityFilter);
			}
			if(input.getState() != null && !input.getState().isEmpty()){
				stateFilter = new Query.FilterPredicate("state",FilterOperator.EQUAL,input.getState());
				filterList.add(stateFilter);
			}
			if(input.getCountry() != null && !input.getCountry().isEmpty()){
				countryFilter = new Query.FilterPredicate("country",FilterOperator.EQUAL,input.getCountry());
				filterList.add(countryFilter);
			}
		}
		Filter searchFilter = filterList.get(0);
		if(filterList.size() >= 2)
			searchFilter = CompositeFilterOperator.and(filterList);
		
		Query query = new Query("Address").setFilter(searchFilter);
		PreparedQuery preparedQuery = dataStoreService.prepare(query);
		
		List<Address> addressList = new ArrayList<Address>();
		
		for (Entity entity : preparedQuery.asIterable()) {
			Address address = new Address();
			
			//address.setId(entity.getKey().getId());
			address.setAddressl((String)entity.getProperty("address1"));
			address.setAddress2((String)entity.getProperty("address2"));
			address.setCity((String) entity.getProperty("city"));
			address.setState((String) entity.getProperty("state"));
			address.setCountry((String) entity.getProperty("country"));
			
			addressList.add(address);
		}
		
		return addressList;
	}
	
	public List<Address> getAddressList(Long userId){
		return getAddressList(null,userId);
		
	}

}
