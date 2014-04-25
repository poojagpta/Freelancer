package com.stern.fraudshields.web;

import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.BaseModel;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.MerchantMapping;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.service.BatchService;
import com.stern.fraudshields.service.BatchServiceImpl;
import com.stern.fraudshields.service.MerchantMappingService;
import com.stern.fraudshields.service.MerchantMappingServiceImpl;
import com.stern.fraudshields.util.JSONHelper;
import com.stern.fraudshields.util.ParserHelper;

public class OrderDetailServletTest {

	private static final Logger log = LoggerFactory.getLogger(OrderDetailServletTest.class);
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
/*	 private final LocalServiceTestHelper helper =
		        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	 */
	 
	 private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(50));
	 
	 

   
    public void setUp() {
	        helper.setUp();
    }

	
	@Test
	public void testfetchJsonObjectFromRequest() throws IOException {

		// strict mock forces you to specify the correct order of method calls
		HttpServletRequest request = EasyMock.createStrictMock(HttpServletRequest.class);

		InputStream inputStream = OrderDetailServletTest.class.getClassLoader()
				.getResourceAsStream("JSONInput.txt");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));

		EasyMock.expect(request.getReader()).andStubReturn(reader);

		// unexpected method calls after this point will throw exceptions
		replay(request);

		OrderDetailServlet serv = new OrderDetailServlet();
		try {

			JSONObject jsonObj = serv.fetchJsonObjectFromRequest(request);
			
			assertNotNull(jsonObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void testdoPost() {
		
	try {
		
		HttpServletRequest request = EasyMock.createStrictMock(HttpServletRequest.class);
		HttpServletResponse response = EasyMock.createStrictMock(HttpServletResponse.class);
		HttpSession session = EasyMock.createStrictMock(HttpSession.class);
		EasyMock.expect(request.getSession(true)).andStubReturn(session);
		
		EasyMock.expect(request.getSession()).andReturn(session);
		    /*EasyMock.expect(session.getAttribute("merchant")).andReturn(null);
		    session.setAttribute(EasyMock.eq("merchant"), EasyMock.isA(HttpSession.class));*/
			
			JSONHelper jsonHelper = JSONHelper.getJsonHelperInstance();
			jsonHelper.setMerchant(new Merchant());
			jsonHelper.setUser(new Customer());
			jsonHelper.setOrderDetail(new OrderDetail());
			jsonHelper.setOrderBillingAddress(new Address());
			jsonHelper.setOrderShippingAddress(new Address());
			jsonHelper.setPaymentGateway(new PaymentGateway());
			jsonHelper.setCcard(new CreditCard());
			jsonHelper.setCcardBillingAddress(new Address());
			
			//EasyMock.expect(session.getAttribute("merchant")).andReturn(jsonHelper.getMerchant());
		/*	String merchant = "merchant";
			String user = "customer";
			String orderDetail = "orderDetail";
			String orderDetail.billingAddress = "";
			String orderDetail.shippingAddress = "" ;
			String orderDetail.paymentGateway = "";
			String creditCard = "" ;
			String creditCard.billingAddress = "";*/
			
			
			
			session.setAttribute("merchant",jsonHelper.getMerchant());
			session.setAttribute("customer", jsonHelper.getUser());
			session.setAttribute("orderDetail", jsonHelper.getOrderDetail());
			session.setAttribute("orderDetail.billingAddress",jsonHelper.getOrderBillingAddress());
			session.setAttribute("orderDetail.shippingAddress",jsonHelper.getOrderShippingAddress());
					
			session.setAttribute("orderDetail.paymentGateway", jsonHelper.getPaymentGateway());
			session.setAttribute("creditCard", jsonHelper.getCcard());
			session.setAttribute("creditCard.billingAddress", jsonHelper.getCcardBillingAddress());
			
			EasyMock.expect(session.getAttribute("merchant")).andStubReturn(jsonHelper.getMerchant());
			EasyMock.expect(session.getAttribute("customer")).andStubReturn(jsonHelper.getUser());
			EasyMock.expect(session.getAttribute("orderDetail")).andStubReturn(jsonHelper.getOrderDetail());
			EasyMock.expect(session.getAttribute("orderDetail.billingAddress")).andStubReturn(jsonHelper.getOrderBillingAddress());
			EasyMock.expect(session.getAttribute("orderDetail.shippingAddress")).andStubReturn(jsonHelper.getOrderShippingAddress());
					
			EasyMock.expect(session.getAttribute("orderDetail.paymentGateway")).andStubReturn(jsonHelper.getPaymentGateway());
			EasyMock.expect(session.getAttribute("creditCard")).andStubReturn(jsonHelper.getCcard());
			EasyMock.expect(session.getAttribute("creditCard.billingAddress")).andStubReturn(jsonHelper.getCcardBillingAddress());
			
			
			
			
			
		/*	session.setAttribute("merchant",EasyMock.isA(Merchant.class));
			session.setAttribute("customer", EasyMock.isA(User.class));
			session.setAttribute("orderDetail", EasyMock.isA(OrderDetail.class));
			session.setAttribute("orderDetail.billingAddress", EasyMock.isA(Address.class));
			session.setAttribute("orderDetail.shippingAddress", EasyMock.isA(Address.class));
					
			session.setAttribute("orderDetail.paymentGateway", EasyMock.isA(PaymentGateway.class));
			session.setAttribute("creditCard", EasyMock.isA(CreditCard.class));
			session.setAttribute("creditCard.billingAddress", EasyMock.isA(Address.class));*/
			
			//EasyMock.expect(request.getSession(false)).andReturn(session).anyTimes();
			replay(session);
			
			
			// EasyMock.expect(request.getSession()).andReturn(session).anyTimes();
			InputStream inputStream = OrderDetailServletTest.class.getClassLoader().getResourceAsStream("JSONInput.txt");

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			EasyMock.expect(request.getReader()).andStubReturn(reader);
			replay(request);
			
			testDataStore(request, response);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public void testDataStore(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		if (log.isDebugEnabled()) {
			log.debug("doPost");
		}

		// fetch json object from request
		JSONObject json = null;
		OrderDetailServlet orderServlet = new OrderDetailServlet();
		try {
			json = orderServlet.fetchJsonObjectFromRequest(req);
		} catch (JSONException e) {

			log.error("Error in json message" + e.getMessage());
		}

		// Create the class objects in session
		HttpSession s = req.getSession(true);

		JSONHelper.createDBClassObjects(s);
		String merchantId = null;
		String merchantMappingXML = null;
		Map<String, String> keyValueDataMappingfinal = new HashMap<String, String>();
		try {

			merchantId = ParserHelper.getMerchantName(json.getString("domain"));
			MerchantMappingService service = new MerchantMappingServiceImpl();
			MerchantMapping mapping = service.getMerchantMapping(merchantId);
			BlobKey keyb = new BlobKey(mapping.getXmlUrl());

			merchantMappingXML = new String(blobstoreService.fetchData(keyb, 0,
					BlobstoreService.MAX_BLOB_FETCH_SIZE - 1));

			// from consolidated json iterate each json pass json to below
			// method

			JSONArray obj = json.getJSONArray("data");

			for (int i = 0; i < obj.length(); i++) {
				Map<String, String> keyValueDataMapping = JSONHelper
						.getObjectFromJson(s,
								(JSONObject) obj.getJSONObject(i),
								merchantMappingXML);

				if (keyValueDataMapping != null) {
					keyValueDataMappingfinal.putAll(keyValueDataMapping);
				}

			}

		} catch (Exception e) {
			log.error("Error in xml data processing for" + merchantId + "---> "
					+ merchantMappingXML + "-->" + e.getMessage());
		}

		// Persisting information to db
		List<BaseModel> listAddressModel = JSONHelper.getDBAddressObjects(s);
		BatchService<BaseModel> batchService = new BatchServiceImpl<BaseModel>();
		try {
			batchService.persistObjects(listAddressModel);
		} catch (Exception e) {

			log.error("Error in persistance to address object" + e.getMessage());
		}

		String keyValueDataMapping = null;

		if (keyValueDataMappingfinal != null) {
			keyValueDataMapping = getStringKeyValue(keyValueDataMappingfinal);
		}
		List<BaseModel> listBaseModel = null;
		try {
		// Persist information to database
		listBaseModel = JSONHelper.getDBClassObjects(s, json,
				keyValueDataMapping);
		
			batchService.persistObjects(listBaseModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Persistance of all other fields--->Size"
					+ listBaseModel.size() + "--->" + e.getMessage());
		}

		// Call the taskQ for sending key value pair to Fidelipay site

		if (keyValueDataMapping != null) {
			addTaskToQueue(CustomMessages.getString("URL"));
		}

		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Expose-Headers", "*");
		resp.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,HEAD,OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		resp.setHeader("Access-Control-Max-Age", "86400");

		resp.getWriter().write("success");
	}
	
	private String getStringKeyValue(Map<String, String> orderDetailMap) {

		String content = "";

		if (orderDetailMap != null) {
			for (String key : orderDetailMap.keySet()) {
				content += key + "=" + orderDetailMap.get(key) + "\n";
			}
		}

		log.debug("String posted to Fidelipay method getStringKeyValue"
				+ content);
		return content;
	}

	private void addTaskToQueue(String url) {
		Queue queue = QueueFactory.getQueue("GatewayQueue");

		// Create Task and insert into PUSH Queue
		queue.add(TaskOptions.Builder.withUrl("/processTaskOrder")
				.param("URL", url).method(Method.POST));

	}
	
	public static void main(String args[]){
		new OrderDetailServletTest().testdoPost();
	}
}
