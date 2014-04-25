package com.stern.fraudshields.mock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMock {
	
	public static JSONObject createMockJson() throws JSONException {
		JSONObject obj = new JSONObject();
		
		//Case Volusion
		obj.put("ShipAddress2", "");
		obj.put("BillingPostalCodeChanged", "N");
		
		
		
		obj.put("ShipAddress1", "17 Stern St.");
		obj.put("CheckNumber", "");
		obj.put("hidden_btncalc_shipping", "");
		obj.put("BankName", "");
		obj.put("Previous_Tax_Percents", "000");
		obj.put("remove_ccardid", "");
		obj.put("My_Saved_Billing", "2");
		obj.put("CreditCardType", "6");
		obj.put("Previous_Calc_Shipping", "0");
		obj.put("ShipCompanyName", "Stern Holdings LLC");
		obj.put("ShipTo", "A Different Address");
		obj.put("AccountType", "");
		obj.put("BillingAddress2", "");
		obj.put("My_Saved_Shipping", "7");
		obj.put("BillingAddress1", "17 Stern St.");
		obj.put("BillingCountry", "United States");
		obj.put("CreditCardNumber", "************2998");
		obj.put("BillingState_dropdown", "NY");
		obj.put("AccountNumber", "");
		obj.put("ShipCity", "Spring Valley");
		obj.put("PONum", "");
		obj.put("merchantid", "v1341462.d6gdjhey9rw4.demo6.volusion.com");
		obj.put("ShipState", "NY");
		obj.put("BillingCity", "Spring Valley");
		obj.put("ShipPhoneNumber", "8452288484");
		obj.put("RoutingNumber", "");
		obj.put("code2", "");
		obj.put("ShipLastName", "Breuer");
		obj.put("CC_ExpDate_Month", "11");
		obj.put("RoutingNumber", "");
		obj.put("code2", "");
		obj.put("ShipLastName", "Breuer");
		obj.put("PaymentMethodTypeDisplay", "Credit Card");
		obj.put("BillingPhoneNumber", "8452288484");
		obj.put("BillingFirstName", "Aaron");
		obj.put("ShipState_dropdown", "NY");
		obj.put("BillingState", "NY");
		obj.put("BillingPostalCode", "10977");
		obj.put("PaymentMethodType", "Credit Card");
		obj.put("BillingCompanyName", "Stern Holdings LLC");
		obj.put("CC_ExpDate_Year", "2013");

		obj.put("ShipPostalCode", "10977");
		obj.put("CardHoldersName", "Aaron Breuer");
		obj.put("BillingLastName", "Breuer");
		obj.put("ShipResidential", "Y");

		obj.put("ShipCountry", "United States");
		obj.put("ShipFirstName", "Aaron");
		obj.put("CCards", "4");
				
		//Case 1:
		/*obj.put("merchantid", "ABCD1234");
		
		obj.put("portalid", "ABCD1234");
		obj.put("username", "mark888");
		obj.put("loginPassword", "kjhg5643");
		obj.put("ipAddress", "182.151.114.251");
		obj.put("orderId", "987462");
		obj.put("orderDate", "2012-08-21");
		obj.put("SalesRep", "MD");
		obj.put("PromoCode", "SPRING2012");
		
		//Billing Address
		obj.put("BillingAddFirstName", "Mark");
		obj.put("BillingAddMiddleName", "");
		obj.put("BillingAddlastName", "Statford");
		obj.put("BillingAddCompany", "");
		obj.put("BillingAddressLine1", "22 Hello Sreet");
		obj.put("BillingAddressLine2", "");
		obj.put("BillingAddressCity", "Miami");
		obj.put("BillingAddressState", "FL");
		obj.put("BillingAddressProvince", "FL");
		obj.put("BillingAddressZip", "087921");
		obj.put("BillingAddressCountry", "US");
		obj.put("BillingAddressPhone", "514-213-5489");
		obj.put("BillingAddressDayPhone", "631-789-3258");
		obj.put("BillingAddressCellPhone", "514-613-6118");
		obj.put("BillingAddressEmail", "anybody@yahoo.com");
		obj.put("BillingAddressAlternativeEmail", "mark@gmail.com");
		
		//Shipping Address
		obj.put("ShippingAddFirstName", "claude");
		obj.put("ShippingAddMiddleName", "");
		obj.put("ShippingAddlastName", "MqGuire");
		obj.put("ShippingAddCompany", "Telco Limited");
		obj.put("ShippingAddressLine1", "123 Smith Street");
		obj.put("ShippingAddressLine2", "Floor 16");
		obj.put("ShippingAddressCity", "Stamford");
		obj.put("ShippingAddressState", "CT");
		obj.put("ShippingAddressProvince", "CT");
		obj.put("ShippingAddressZip", "06907");
		obj.put("ShippingAddressCountry", "US");
		obj.put("ShippingAddressPhone", "2036587469");
		obj.put("ShippingAddressDayPhone", "2036587469");
		obj.put("ShippingAddressCellPhone", "314-213-6118");
		obj.put("ShippingAddressEmail", "claude@yahoo.ca");
				
		
		
		obj.put("shippingMethod", "UPS Ground");   
		obj.put("GrandTotal", "879.36");
		obj.put("CIDResponse", "M");
		obj.put("GatewayTransactionID", "82345699");
		obj.put("GatewayStatus", "Approved");
		obj.put("AVSCode", "Z");
		
		obj.put("ReferringCode", "pricegrabber");   
		obj.put("CAVVResultCode", "2");
		obj.put("CustomerComments", "please dont include invoice in box");
		obj.put("ConsumerReferral", "web");
		obj.put("SalesRepComments", "customer says he owns the company we are shipping to");
		obj.put("InboundCallerID", "2036587469");
		obj.put("ECICode", "02");
			
		
		 //Credit card details
		obj.put("binValue", "551409");
		obj.put("cardtype", "V");
		obj.put("Cclast4", "CCLast4");
		obj.put("ccexpires", "1014");
		obj.put("ccAddFirstName", "ABCD1234");
		obj.put("ccAddMiddleName", "ABCD1234");
		obj.put("ccAddlastName", "ABCD1234");
		obj.put("ccAddCompany", "ABCD1234");
		obj.put("ccAddressLine1", "ABCD1234");
		obj.put("ccAddressLine2", "ABCD1234");
		obj.put("ccAddressCity", "ABCD1234");
		obj.put("ccAddressState", "ABCD1234");
		obj.put("ccAddressProvince", "ABCD1234");
		obj.put("ccAddressZip", "ABCD1234");
		obj.put("ccAddressCountry", "ABCD1234");
		obj.put("ccAddressPhone", "ABCD1234");
		obj.put("ccAddressDayPhone", "ABCD1234");
		obj.put("ccAddressCellPhone", "ABCD1234");
		obj.put("ccAddressEmail", "ABCD1234");
		obj.put("ccAddressAlternativeEmail", "ABCD1234");
        
	
		System.out.println("Data retuned----->"+obj.toString());
		//TODO: Create card items (Checkout items) 
		/*obj.put("productId", "1234UN");
		obj.put("ItemName", "LCD TV");
		obj.put("Price", "100");
		obj.put("Quantity","500");
	*/
		
				
		/*
		 * JSONArray list = new JSONArray(); list.put("msg 1");
		 * list.put("msg 2"); list.put("msg 3");
		 * 
		 * obj.put("messages", list);
		 */
		return obj;
	}

}
