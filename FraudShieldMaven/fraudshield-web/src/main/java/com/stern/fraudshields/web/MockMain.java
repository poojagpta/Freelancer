package com.stern.fraudshields.web;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONException;
import org.json.JSONObject;

import com.stern.fraudshields.mock.JSONMock;
import com.stern.fraudshields.model.Address;
import com.stern.fraudshields.model.CreditCard;
import com.stern.fraudshields.model.Merchant;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.model.PaymentGateway;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.util.JSONHelper;

public class MockMain {

	public static String XML_ROOT_PATH = "D:/example workspace/FraudShieldMaven/xml"; // http://localhost:8080/xml/
	public static String XML_POSTFIX = ".xml";

	public static void main(String str[]) throws ClassNotFoundException {

		System.out.println(CustomMessages.getString("MerchantXMLPath"));

		JSONHelper mainT = new JSONHelper();

		Map<String, Object> mapDBClassObject = null;// mainT.createDBClassObjects();
		try {
			JSONObject json = JSONMock.createMockJson();
			System.out.println(json.toString());
			// mapDBClassObject=(Map<String,
			// Object>)mainT.getObjectFromJson(json,
			// mapDBClassObject,XML_ROOT_PATH,XML_POSTFIX);
			Merchant m = (Merchant) mapDBClassObject.get("merchant");
			System.out.println(m.getAppid());
			Customer user = (Customer) mapDBClassObject.get("user");
			OrderDetail orderDetail = (OrderDetail) mapDBClassObject
					.get("orderDetail");
			Address orderBillingAddress = (Address) mapDBClassObject
					.get("orderDetail.billingAddress");
			Address orderShippingAddress = (Address) mapDBClassObject
					.get("orderDetail.shippingAddress");
			Address ccardBillingAddress = (Address) mapDBClassObject
					.get("creditCard.billingAddress");
			CreditCard ccard = (CreditCard) mapDBClassObject.get("creditCard");
			PaymentGateway paygateway = (PaymentGateway) mapDBClassObject
					.get("orderDetail.paymentGateway");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
