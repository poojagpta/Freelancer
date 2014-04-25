package com.stern.fraudshields.util;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.BaseModel;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.RawData;
import com.stern.fraudshields.model.ThirdPartyReq;

public class JSONHelper {

	private static final Logger log = LoggerFactory.getLogger(JSONHelper.class);
	
	private Merchant merchant;
	private Customer user;
	private OrderDetail orderDetail ;
	private Address orderBillingAddress ;
	private Address orderShippingAddress ;
	private Address ccardBillingAddress ;
	private CreditCard ccard ;
	private PaymentGateway paymentGateway;
	
	private static JSONHelper jsonHelper;
	
	public static JSONHelper getJsonHelperInstance(){
		if(jsonHelper == null){
			jsonHelper = new JSONHelper();
		}
		return jsonHelper;
		
	}
	

	public static Map<String, String> getObjectFromJson(HttpSession session,
			JSONObject json, String xmlMerchantMapping) throws JSONException,
			ConfigurationException {

		log.debug("Inside method getObjectFromJson");
		Map<String, String> keyValueMapping = new HashMap<String, String>();

		// find the merchant id from json...it can be part of get string also
		// better if we can provide it in json
		Reader in = new StringReader(xmlMerchantMapping);
		// find the xml corresponding to merchant id like ABCD1234 will tell
		// us to look ABCD1234.xml
		XMLConfiguration config = new XMLConfiguration();
		config.load(in);

		// iterate json pick key, fin this key in xml and pass this key in
		// config.getString(key) will give us the dbfield
		JSONObject jsonformdata = (JSONObject) json.get("formdata");
		@SuppressWarnings("rawtypes")
		Iterator iterKey = jsonformdata.keys();

		// Assigning formname if not null else formid
		String formid = json.getString("formname");
		if (formid == null || ("").equals(formid)) {
			formid = json.getString("formid");
		}

		while (iterKey.hasNext()) {
			String jsonKey = (String) iterKey.next();
			String jsonCompare = formid + "." + jsonKey;
			String dbfield = null;
			String dbfieldtype = null;
			String dbLabel = null;
			boolean mandatoryfield = false;

			Object prop = config.getProperty("field.merchantLabel");

			if (prop instanceof Collection) {
				@SuppressWarnings("unchecked")
				ArrayList<String> propCollection = (ArrayList<String>) prop;
				for (int i = 0; i < propCollection.size(); i++) {
					if (jsonCompare.equals(propCollection.get(i))) {
						dbfield = config.getString("field(" + i + ").dbfield");
						mandatoryfield = config.getBoolean("field(" + i
								+ ").mandatory");
						dbfieldtype = config.getString("field(" + i + ").type");
						dbLabel = config.getString("field(" + i + ").dbLabel");
						try {
							log.debug(dbfield + " ; " + mandatoryfield + " ; "
									+ Class.forName(dbfieldtype) + ";"
									+ dbLabel);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							log.error(e.getMessage());
						}
						break;
					}
				}
			}

			// populate this database field and associate it with particular
			// object
			if (dbfield != null && !("").equals(dbfield)) {
				String literals[] = dbfield.split(".set");
				Object obj = session.getAttribute(literals[0]);
				Method method = null;
				try {
					method = obj.getClass().getMethod("set" + literals[1],
							Class.forName(dbfieldtype));
				} catch (SecurityException e) {
					log.error("Class name issue" + e.getMessage());
				} catch (NoSuchMethodException e) {
					log.error(e.getMessage());
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage());
				}

				// In case field contain * then we should remove it...
				if (literals[1].contains("Bin")) {
					json.put(jsonKey, jsonformdata.getString(jsonKey)
							.replaceAll("\\*", ""));
					jsonKey = jsonKey.replaceAll("\\*", "");
				}

				Object jsonValObject;
				try {

					String formData = jsonformdata.getString(jsonKey);

					if (formData != null && !("").equals(formData.trim())) {

						// In case blob object append it with jsonkey
						if ((dbfieldtype).contains("Blob")) {
							log.debug("Vaue of jsonKey:" + jsonKey
									+ " formData" + formData);
							keyValueMapping.put(jsonKey, formData);
							formData = jsonKey + ":" + formData;

						} else {
							keyValueMapping.put(dbLabel, formData);
							log.debug("Vaue of dbLabel:" + dbLabel
									+ " formData" + formData);
						}

						jsonValObject = ParserHelper.toObject(
								Class.forName(dbfieldtype), formData);
						method.invoke(obj, jsonValObject);
					}

				} catch (ParseException e) {
					log.error(e.getMessage());
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					log.error(e.getMessage());
				} catch (IllegalAccessException e) {
					log.error(e.getMessage());
				} catch (InvocationTargetException e) {
					log.error(e.getMessage());
				}

				session.setAttribute(literals[0], obj);
			}

		}

		log.debug("Exit method getObjectFromJson");
		return keyValueMapping;
	}

	public static void createDBClassObjects(HttpSession session) {

		Merchant merchant = new Merchant();
		Customer user = new Customer();
		OrderDetail orderDetail = new OrderDetail();
		Address orderBillingAddress = new Address();
		Address orderShippingAddress = new Address();
		Address ccardBillingAddress = new Address();
		CreditCard ccard = new CreditCard();
		PaymentGateway paygateway = new PaymentGateway();

		session.setAttribute("merchant", merchant);

		session.setAttribute("customer", user);
		session.setAttribute("orderDetail", orderDetail);
		session.setAttribute("orderDetail.billingAddress", orderBillingAddress);
		session.setAttribute("orderDetail.shippingAddress",
				orderShippingAddress);
		session.setAttribute("orderDetail.paymentGateway", paygateway);
		session.setAttribute("creditCard", ccard);
		session.setAttribute("creditCard.billingAddress", ccardBillingAddress);

		/*JSONHelper jsonHelper = JSONHelper.getJsonHelperInstance();
		session.setAttribute("merchant", jsonHelper.getMerchant());
		session.setAttribute("customer", jsonHelper.getUser());
		session.setAttribute("orderDetail", jsonHelper.getOrderDetail());
		session.setAttribute("orderDetail.billingAddress", jsonHelper.getOrderBillingAddress());
		session.setAttribute("orderDetail.shippingAddress",jsonHelper.getOrderShippingAddress());
		session.setAttribute("orderDetail.paymentGateway", jsonHelper.getPaymentGateway());
		session.setAttribute("creditCard", jsonHelper.getCcard());
		session.setAttribute("creditCard.billingAddress", jsonHelper.getCcardBillingAddress());*/

	}

	public static List<BaseModel> getDBAddressObjects(HttpSession session) {
		List<BaseModel> baseModelClass = new ArrayList<BaseModel>();
		Address orderBillAdd = (Address) session.getAttribute("orderDetail.billingAddress");
		if (orderBillAdd != null && orderBillAdd.getFirstName() != null) {
			baseModelClass.add(orderBillAdd);
		}

		Address orderShippingAdd = (Address) session.getAttribute("orderDetail.shippingAddress");
		if (orderShippingAdd != null && orderShippingAdd.getFirstName() != null) {
			baseModelClass.add(orderShippingAdd);
		}

		Address ccBillAdd = (Address) session.getAttribute("creditCard.billingAddress");
		if (ccBillAdd != null && ccBillAdd.getFirstName() != null) {
			baseModelClass.add(ccBillAdd);
		}

		return baseModelClass;
	}

	public static List<BaseModel> getDBClassObjects(HttpSession session,
			JSONObject json, String thirdPartyreq) throws JSONException {
		List<BaseModel> baseModelClass = new ArrayList<BaseModel>();

		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant.getSiteid() != null && !("").equals(merchant.getSiteid())) {
			baseModelClass.add(merchant);
		}

		Customer user = (Customer) session.getAttribute("customer");

		if (user.getLoginName() != null && !("").equals(user.getLoginName())) {
			baseModelClass.add(user);
		}
		OrderDetail orderDetail = (OrderDetail) session.getAttribute("orderDetail");
		
		orderDetail.setDomain(ParserHelper.getMerchantName(json.getString("domain")));
		
		if (merchant.getSiteName() != null && !("").equals(merchant.getSiteName())) {
			orderDetail.setMerchant(new Key<Merchant>(Merchant.class,merchant.getSiteName()));
		}

		Address orderBillAdd = (Address) session.getAttribute("orderDetail.billingAddress");
		
		Address orderShippingAdd = (Address) session.getAttribute("orderDetail.shippingAddress");
		if (orderBillAdd.getId() != null) {
			orderDetail.setBillingAddress(new Key<Address>(Address.class, orderBillAdd.getId()));
		}

		if (orderShippingAdd.getId() != null) {
			orderDetail.setShippingAddress(new Key<Address>(Address.class,	orderShippingAdd.getId()));
		}

		if (user.getLoginName() != null) {
			orderDetail.setUser(new Key<Customer>(Customer.class, user.getLoginName()));
		}

		PaymentGateway payGateway = (PaymentGateway) session
				.getAttribute("orderDetail.paymentGateway");
		if (payGateway.getGateway_id() != null
				&& !("").equals(payGateway.getGateway_id())) {
			baseModelClass.add(payGateway);
			orderDetail.setPaymentGateway(new Key<PaymentGateway>(
					PaymentGateway.class, payGateway.getGateway_id()));
		}

		if (orderDetail.getOrderDetailId() != null
				&& !("").equals(orderDetail.getOrderDetailId())) {
			baseModelClass.add(orderDetail);
		}

		CreditCard ccard = (CreditCard) session.getAttribute("creditCard");

		Address ccBillAdd = (Address) session
				.getAttribute("creditCard.billingAddress");
		if (ccBillAdd.getId() != null) {
			ccard.setBillingAddress(new Key<Address>(Address.class, ccBillAdd.getId()));
		}

		if (ccard.getBin() != null && !("").equals(ccard.getBin())) {
			baseModelClass.add(ccard);
			orderDetail.setCreditCard(new Key<CreditCard>(CreditCard.class,	ccard.getBin()));
		}

		// Persist raw data
		RawData rawdata = new RawData();
		if (null != json) {
			rawdata.setPost(new Blob(json.toString().getBytes()));
			rawdata.setJsonObject(json.toString());

		}

		if (user != null) {
			rawdata.setRemote_ip(user.getIpAddress());
		}

		rawdata.setTimestamp(new Date());
		baseModelClass.add(rawdata);

		if (thirdPartyreq != null && !(("").equals(thirdPartyreq.trim())) && orderDetail.getOrderDetailId()!=null) {
			// Persist to thirdPartyReq object
			ThirdPartyReq thirdPartyReq = new ThirdPartyReq();
			thirdPartyReq.setOrderId(orderDetail.getOrderDetailId());
			thirdPartyReq.setReqObject(thirdPartyreq);
			thirdPartyReq.setStatus("PENDING");
			baseModelClass.add(thirdPartyReq);
		}

		return baseModelClass;
	}

	public static String writeJSON(OrderDetail entity) throws JSONException {
		log.info("creating JSON format object");
		JSONObject obj = new JSONObject();
		obj.put("Billing Add:", entity.getBillingAddress().getName());
		obj.put("Grand total", entity.getGrandTotal());

		return obj.toString();
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Address getOrderBillingAddress() {
		return orderBillingAddress;
	}

	public void setOrderBillingAddress(Address orderBillingAddress) {
		this.orderBillingAddress = orderBillingAddress;
	}

	public Address getOrderShippingAddress() {
		return orderShippingAddress;
	}

	public void setOrderShippingAddress(Address orderShippingAddress) {
		this.orderShippingAddress = orderShippingAddress;
	}

	public Address getCcardBillingAddress() {
		return ccardBillingAddress;
	}

	public void setCcardBillingAddress(Address ccardBillingAddress) {
		this.ccardBillingAddress = ccardBillingAddress;
	}

	public CreditCard getCcard() {
		return ccard;
	}

	public void setCcard(CreditCard ccard) {
		this.ccard = ccard;
	}

	public PaymentGateway getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	
}
