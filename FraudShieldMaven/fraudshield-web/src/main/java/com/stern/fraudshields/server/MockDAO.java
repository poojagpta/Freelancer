package com.stern.fraudshields.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.BaseModel;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.Product;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.service.BatchService;
import com.stern.fraudshields.service.BatchServiceImpl;

public class MockDAO {
	
	public MockDAO()
	{
		Blob blogField=new Blob(new String("This is a test").getBytes());
		
		
		//Fetch current date
		Date date = new Date();
			
		//Create a Address Entity--Complete
		Address address = new Address(0L, "firstName", "middleName", "lastName", "company", new Text("addressl"), new Text("address2"), "city", "state", "province", "zip", "country", "000-0000-000", "000-0000-000", "000-0000-000", "test@test.com", "test@test.com", blogField);
		Key<Address> addKey=new Key<Address>(Address.class, "id:UniqueAddressID?");
		List<Key<Address>> addList=new ArrayList<Key<Address>>();
		addList.add(addKey);
		
		//Create a CreditCard--Need to encrypt the Card number Complete -- Will do it from Json object
		CreditCard ccard=new CreditCard(new Long(123456), "1234", "cardtype", "1014", blogField, null, addKey);
		Key<CreditCard> ccKey=new Key<CreditCard>(CreditCard.class, 123456);
		List<Key<CreditCard>> ccList=new ArrayList<Key<CreditCard>>();
		ccList.add(ccKey);
		
		
		//Create a payment gateway--Done -- Need to be present 
		PaymentGateway payGate=new PaymentGateway(1,  new Long(1), "test", "test", "test", "login", "password", "source_key", "pin", "anet_login", "anet_key", "yahoo_storeid", "yahoo_token", "volusion_login", "volusion_password", "status");
		Key<PaymentGateway> payGateKey=new Key<PaymentGateway>(PaymentGateway.class, 1);
		List<Key<PaymentGateway>> payGateList=new ArrayList<Key<PaymentGateway>>();
		payGateList.add(payGateKey);
		
		//Create a dummy merchant--complete
		Merchant merchant=new Merchant("siteName1", "siteid1", "appid1", "portalid1", date, blogField, null, null,payGateList,addList);
		Key<Merchant> merchantKey=new Key<Merchant>(Merchant.class, "siteName1");
		List<Key<Merchant>> merchantList=new ArrayList<Key<Merchant>>();
		merchantList.add(merchantKey);
		
		
		//Create a user--Complete
		Customer user=new Customer("loginName1", "loginPassword1", "iPAddress1",  blogField,merchantList, addList, ccList,null);
		Key<Customer> userKey=new Key<Customer>(Customer.class, "loginName1");
		List<Key<Customer>> userList=new ArrayList<Key<Customer>>();
		userList.add(userKey);
		//merchant.setUser(userList);
		ccard.setUserDetail(userKey);
		
			
		//Create a product--Complete
		Product product=new Product("ProdName", "100", "giftmessage", blogField, userList, null, merchantList);
		Key<Product> productKey=new Key<Product>(Product.class, "ProdName");
		List<Key<Product>> productList=new ArrayList<Key<Product>>();
		productList.add(productKey);
		
		 //Create a order--Complete
		OrderDetail order=new OrderDetail("Order1121", date, "ordertype", "salesRep", "promoCode", "100", "shippingMethod", "customerComments", "salesrepComments", "inboundCallerId", addKey, addKey, productList, userKey, blogField);
		Key<OrderDetail> orderKey=new Key<OrderDetail>(OrderDetail.class, "Order1121");
		List<Key<OrderDetail>> orderList=new ArrayList<Key<OrderDetail>>();
		orderList.add(orderKey);
		product.setOrderDetail(orderList);
		
		//Creating Transaction Detail--I believe its id is same as Order id
		/*TransactionDetail txnDetail=new TransactionDetail("Order1121", orderKey, "cidresponse", payGateKey, "referringcode", "cavvresultcode", "ecicode", "avscode", blogField, OrderStatus.ACCEPTED.getOrderStatus());
		Key<TransactionDetail> txnKey=new Key<TransactionDetail>(TransactionDetail.class, "Order1121");
		List<Key<TransactionDetail>> txnList=new ArrayList<Key<TransactionDetail>>();
		txnList.add(txnKey);*/
		merchant.setOrderDetails(null);
		user.setOrderDetails(null);
		
		
		//ofy.put(address,ccard,payGate,merchant,user,product,order);
		List<BaseModel> listItems=new ArrayList<BaseModel>();
		listItems.add(address);
		listItems.add(ccard);
		
		BatchService<BaseModel> service = new BatchServiceImpl<BaseModel>();
		try {
			service.persistObjects(listItems);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Iterable<T>
		
	}

}
