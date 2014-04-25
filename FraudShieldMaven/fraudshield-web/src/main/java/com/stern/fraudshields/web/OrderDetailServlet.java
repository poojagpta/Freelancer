package com.stern.fraudshields.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gae.service.SearchQuery;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.stern.fraudshields.model.BaseModel;
import com.stern.fraudshields.model.MerchantMapping;
import com.stern.fraudshields.model.OrderDetail;
import com.stern.fraudshields.service.BatchService;
import com.stern.fraudshields.service.BatchServiceImpl;
import com.stern.fraudshields.service.MerchantMappingService;
import com.stern.fraudshields.service.MerchantMappingServiceImpl;
import com.stern.fraudshields.util.JSONHelper;
import com.stern.fraudshields.util.ParserHelper;

public class OrderDetailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4858525709983782455L;

	private static final Logger log = LoggerFactory
			.getLogger(OrderDetailServlet.class);

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (log.isDebugEnabled()) {
			log.debug("doGet");
		}
		doPost(req, resp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (log.isDebugEnabled()) {
			log.debug("doPost");
		}

		// fetch json object from request
		JSONObject json = null;
		try {
			json = fetchJsonObjectFromRequest(req);
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
				Map<String, String> keyValueDataMapping = JSONHelper.getObjectFromJson(s,(JSONObject) obj.getJSONObject(i),
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
			listBaseModel = JSONHelper.getDBClassObjects(s, json, keyValueDataMapping);
		
			batchService.persistObjects(listBaseModel);
			
			com.gae.model.OrderDetail orderDetail = null;
			for (BaseModel baseModel : listBaseModel) {
				if(baseModel.getClass().equals(OrderDetail.class)){
					OrderDetail order = (OrderDetail)baseModel; 
					orderDetail = new com.gae.model.OrderDetail();
					orderDetail.setOrderDetailId(order.getOrderDetailId());
				}
			}
			
			SearchQuery search = new SearchQuery();
			search.updateSearchIndex(merchantId,orderDetail);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Persistance of all other fields--->Size"
					+ listBaseModel.size() + "--->" + e.getMessage());
		}

		// Call the taskQ for sending key value pair to Fidelipay site

		if (keyValueDataMapping != null && !keyValueDataMapping.equals("")) {
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

	private void addTaskToQueue(String url) {
		Queue queue = QueueFactory.getQueue("GatewayQueue");

		// Create Task and insert into PUSH Queue
		queue.add(TaskOptions.Builder.withUrl("/processTaskOrder")
				.param("URL", url).method(Method.POST));

	}

	private String getStringKeyValue(Map<String, String> orderDetailMap) {

		String content = "";

		if (orderDetailMap != null) {
			for (String key : orderDetailMap.keySet()) {
				content += key + "=" + orderDetailMap.get(key) + "&";
			}
		}

		log.debug("String posted to Fidelipay method getStringKeyValue"
				+ content);
		return content;
	}

	public JSONObject fetchJsonObjectFromRequest(HttpServletRequest req)
			throws IOException, JSONException {

		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line + "\n");
			line = reader.readLine();
		}
		reader.close();

		return new JSONObject(sb.toString());
	}

}
