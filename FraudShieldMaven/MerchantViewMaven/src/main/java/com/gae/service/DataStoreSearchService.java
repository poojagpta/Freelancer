package com.gae.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gae.bean.MerchantDataGrid;
import com.gae.bean.SearchInput;
import com.gae.dao.AddressDAO;
import com.gae.dao.AddressDAOImpl;
import com.gae.dao.MerchantDAO;
import com.gae.dao.MerchantDAOImpl;
import com.gae.dao.OrderDetailDAO;
import com.gae.dao.OrderDetailDAOImpl;
import com.gae.dao.UserDAOImpl;
import com.gae.model.Address;
import com.gae.model.Merchant;
import com.gae.model.OrderDetail;
import com.gae.model.OrderStatus;
import com.gae.model.User;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DataStoreSearchService {
	
	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/*public Map<Merchant,List<User>> getMerchantUserMap(SearchInput input){
		
		List<User> userList = null;
		List<Merchant> merchantList = null;
		Map<Merchant,List<User>> merchantUserMap = new HashMap<Merchant, List<User>>();
		MerchantDAO merchantDao = new MerchantDAOImpl();
		
		try {
			merchantList = merchantDao.getMerchantList(input);
			
			for (Merchant merchant : merchantList) {
				if(merchantUserMap.get(merchant) != null){
					merchantUserMap.put(merchant, getUserList(merchant)); 
				}else{
					userList = merchantUserMap.get(merchant);
					userList.addAll(getUserList(merchant));
					merchantUserMap.put(merchant, getUserList(merchant));
				}
			
			}
			
		} catch (Exception e) {
			
		}
		return merchantUserMap;
	}
	
	public Map<User,List<Address>> getUserAddressMap(User user){
		
		Map<User,List<Address>> userAddressMap = new HashMap<User, List<Address>>();
		List<Address> addressList = new ArrayList<Address>();
		AddressDAO addressDao = new AddressDAOImpl();
		Map<Key, Entity> entityMap = addressDao.getEntityMap(user.getAddress());
		Address address = null;
		for (Entity entity : entityMap.values()) {
			address = new Address();
			address.setId((Long)entity.getProperty("id"));
			address.setAddressl((Text)entity.getProperty("address1"));
			address.setAddress2((Text)entity.getProperty("address2"));
			address.setCountry((String)entity.getProperty("country"));
			address.setCity((String)entity.getProperty("city"));
			
			addressList.add(address);
		}
		return userAddressMap;
	}
	

	public Map<Merchant,List<OrderDetail>> getMerchantOrderMap(Merchant merchant){
		
		Map<Merchant,List<OrderDetail>> merchantOrderMap = new HashMap<Merchant, List<OrderDetail>>();
		List<OrderDetail> orderList = new ArrayList<OrderDetail>(); 
		OrderDetailDAO orderDao = new OrderDetailDAOImpl();
		Map<Key, Entity> entityMap = orderDao.getEntityMap(merchant.getOrderDetails());
		OrderDetail orderDetail = null;
		for (Entity entity : entityMap.values()) {
			orderDetail = new OrderDetail();
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("billingAddress")));
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("shippingAddress")));
			orderDetail.setOrderStatus(OrderStatus.valueOf((String)entity.getProperty("orderStatus")));
			orderDetail.setUser(KeyFactory.stringToKey((String)entity.getProperty("user")));
			orderList.add(orderDetail);
		}
		merchantOrderMap.put(merchant, orderList);
		return merchantOrderMap;
	}*/
	
	
	public Map<User,List<OrderDetail>> getUserOrderMap(User user){
		
		Map<User,List<OrderDetail>> userOrderMap = new HashMap<User, List<OrderDetail>>();
		List<OrderDetail> orderList = new ArrayList<OrderDetail>(); 
		OrderDetailDAO orderDao = new OrderDetailDAOImpl();
		Map<Key, Entity> entityMap = orderDao.getEntityMap(user.getOrderDetails());
		OrderDetail orderDetail = null;
		for (Entity entity : entityMap.values()) {
			orderDetail = new OrderDetail();
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("billingAddress")));
			orderDetail.setBillingAddress(KeyFactory.stringToKey((String)entity.getProperty("shippingAddress")));
			orderDetail.setOrderStatus(OrderStatus.valueOf((String)entity.getProperty("orderStatus")));
			orderDetail.setUser(KeyFactory.stringToKey((String)entity.getProperty("customer")));
			orderList.add(orderDetail);
		}
		userOrderMap.put(user, orderList);
		return userOrderMap;
	}

	
	public List<MerchantDataGrid> searchOnMerchant(SearchInput input,Long userId){
		
		List<User> userList = null;
		List<Merchant> merchantList = null;
		List<OrderDetail> orderDetailList = null;
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		MerchantDataGrid merchantDataGrid = new MerchantDataGrid();
		MerchantDAO merchantDao = new MerchantDAOImpl();
		Map<User,List<OrderDetail>> userOrderMap = null;
		AddressDAOImpl addressDao = new AddressDAOImpl();
		
		try {
			merchantList = merchantDao.getMerchantList(input,userId);
			
			for (Merchant merchant : merchantList) {
				merchantDataGrid.setMerchantName(merchant.getSiteName());
				userList = getUserList(merchant);
				for (User user : userList) {
					merchantDataGrid.setUserName(user.getLoginName());
					userOrderMap = getUserOrderMap(user);
					orderDetailList = userOrderMap.get(user);
					for (OrderDetail orderDetail : orderDetailList) {
						merchantDataGrid.setBillingAddress(addressDao.getAddress(orderDetail.getBillingAddress()).getAddressl().toString());
						merchantDataGrid.setShippingAddress(addressDao.getAddress(orderDetail.getShippingAddress()).getAddressl().toString());
						merchantDataGrid.setDate(orderDetail.getOrderDate());
						merchantDataGrid.setOrderStatus(orderDetail.getOrderStatus());
					}
					
				}
				merchantDataList.add(merchantDataGrid);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> searchOnAddress(SearchInput input,Long userId){
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		AddressDAOImpl addressDAOImpl = new AddressDAOImpl();
		OrderDetailDAOImpl orderDao = new OrderDetailDAOImpl();
		MerchantDataGrid merchantDataGrid = new MerchantDataGrid();
		UserDAOImpl userDao = new UserDAOImpl();
		Map<Key,Address> addressMap = new HashMap<Key,Address>();
		try {
			List<Address> addressList =  addressDAOImpl.getAddressList(input,userId);
			for (Address address : addressList) {
				addressMap.put(KeyFactory.createKey("Address", address.getAddressl()),address);
			}
			if(addressMap.keySet() != null && !addressMap.keySet().isEmpty()){
				List<OrderDetail> orderList =  orderDao.getOrderDetails(new ArrayList<Key>(addressMap.keySet()));
				
			
			for (OrderDetail orderDetail : orderList) {
				Address address = addressMap.get(orderDetail.getShippingAddress());
				merchantDataGrid.setShippingAddress(address.getAddressl() +" "+ address.getAddress2());
				address = addressMap.get(orderDetail.getBillingAddress());
				merchantDataGrid.setBillingAddress(address.getAddressl() +" "+ address.getAddress2());
				
				merchantDataGrid.setOrderStatus(orderDetail.getOrderStatus());
				if(orderDetail.getUser() != null){
					User user = userDao.getUser(orderDetail.getUser());
					merchantDataGrid.setUserName(user.getLoginName());
				}
				merchantDataList.add(merchantDataGrid);
				
			}
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> searchOnUser(SearchInput input,Long userId){
		
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		UserDAOImpl userDao = new UserDAOImpl();
		AddressDAOImpl addressDao = new AddressDAOImpl();
		MerchantDAOImpl merchantDao = new MerchantDAOImpl();
		List<User> userList = null;
		List<OrderDetail> orderList = null;
		MerchantDataGrid merchantDataGrid = null;
		Address address = null;
		try {
			userList = userDao.getUserList(input,userId,null,10,0);
			for (User user : userList) {
				orderList = getOrderList(user);
				for (OrderDetail orderDetail : orderList) {
					merchantDataGrid = new MerchantDataGrid();
					merchantDataGrid.setUserName(user.getLoginName());
					if(orderDetail.getShippingAddress() != null){
						address = addressDao.getAddress(orderDetail.getShippingAddress());
						merchantDataGrid.setShippingAddress(address.getAddressl() +" "+ address.getAddress2());
					}
					if(orderDetail.getBillingAddress() != null){
						address = addressDao.getAddress(orderDetail.getBillingAddress());
						merchantDataGrid.setBillingAddress(address.getAddressl() +" "+ address.getAddress2());
					}
					merchantDataGrid.setOrderStatus(orderDetail.getOrderStatus());
					if(orderDetail.getMerchant() != null){
						merchantDataGrid.setMerchantName(merchantDao.getMerchant(orderDetail.getMerchant()).getSiteName());
					}
					merchantDataList.add(merchantDataGrid);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> searchOnOrderDetail(SearchInput input,Long userId){
		
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		OrderDetailDAOImpl orderDao = new OrderDetailDAOImpl();
		UserDAOImpl userDao = new UserDAOImpl();
		AddressDAOImpl addressDao = new AddressDAOImpl();
		List<OrderDetail> orderList = null;
		MerchantDataGrid merchantDataGrid = new MerchantDataGrid();
		Address address = null;
		try {
			orderList = orderDao.getOrderDetailList(input,userId);
			
			for (OrderDetail orderDetail : orderList) {
				if(orderDetail.getShippingAddress() != null){
					address = addressDao.getAddress(orderDetail.getShippingAddress());
					merchantDataGrid.setShippingAddress(address.getAddressl() +" "+ address.getAddress2());
				}
				if(orderDetail.getBillingAddress() != null){
					address = addressDao.getAddress(orderDetail.getBillingAddress());
					merchantDataGrid.setBillingAddress(address.getAddressl() +" "+ address.getAddress2());
				}
				merchantDataGrid.setOrderStatus(orderDetail.getOrderStatus());
				merchantDataGrid.setDate(orderDetail.getOrderDate());
				User user = userDao.getUser(orderDetail.getUser());
				merchantDataGrid.setUserName(user.getLoginName());
				
			}
				
			merchantDataList.add(merchantDataGrid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantDataList;
	}
	
	public List<User> getUserList(Merchant merchant){
		
		List<User> userList = new ArrayList<User>();
		List<Key> orderKeyList = merchant.getOrderDetails(); 
		Map<Key, Entity> entityMap = null;
		OrderDetailDAOImpl orderDao = new OrderDetailDAOImpl();
		//entityMap = userDao.getEntityMap(userKeyList);
		entityMap = orderDao.getEntityMap(orderKeyList);
		for (Entity entity : entityMap.values()) {
			OrderDetail order = new OrderDetail();
			
			
		}
		return userList;
	
	}
	
	public List<Address> getAddressList(User user){
		
		List<Address> addressList = new ArrayList<Address>();
		Map<Key, Entity> entityMap = null;
		AddressDAO addressDao = new AddressDAOImpl();
		entityMap = addressDao.getEntityMap(user.getAddress());
		for (Entity entity : entityMap.values()) {
			Address address = new Address();
			address.setAddressl((String)entity.getProperty("address1"));
			addressList.add(address);
		}
		return addressList;
	}
	
	public List<OrderDetail> getOrderList(User user){
		
		List<OrderDetail> orderList = new ArrayList<OrderDetail>();
		Map<Key,Entity> entityMap = new HashMap<Key, Entity>();
		OrderDetail order = null;
		try {
			OrderDetailDAOImpl orderDao = new OrderDetailDAOImpl();
			if(user.getOrderDetails() != null){
				entityMap =  orderDao.getEntityMap(user.getOrderDetails());
			}
			for (Entity entity : entityMap.values()) {
				order = new OrderDetail();
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
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	

}