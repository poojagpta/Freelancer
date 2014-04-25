package com.gae.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gae.bean.MerchantDataGrid;
import com.gae.bean.Paging;
import com.gae.bean.SearchInput;
import com.gae.enumeration.SearchOption;
import com.gae.manager.SearchServiceManager;
import com.gae.model.OrderStatus;
import com.quicksale.vo.UserVO;

@Path("/search")
public class CustomerSearchRestService {
	
    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    /*@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public String getMerchant(@Context HttpServletRequest request,
								@Context HttpServletResponse response) throws JSONException, IOException, ServletException{
    	
    	JSONObject json = new JSONObject();
    	UserVO user = (UserVO)request.getSession().getAttribute("user") ;
    	
    	if(user == null){
    		json.put("result", "Invalid");
    		//res.sendRedirect("/home.jsp");
    		return json.toString();
    	}
    	
    	try {
    		
    		List<MerchantDataGrid> merchantDataList = null; 
    		
    		//merchantDataList = SearchServiceManager.executeFullTextSearch(input, user);
    		SearchInput input = new SearchInput();
    		input.setSearchOption(SearchOption.CUSTOMER);
    		merchantDataList = SearchServiceManager.executeDataStoreSearch(input,user);  // in order to search from datastore directly
    	
    		JSONArray jsonArray = toJson(merchantDataList);
    		
    		json.put("result", jsonArray);
    		System.out.println("------"+jsonArray);
    		
    		
    	} catch (Exception e) {
   			e.printStackTrace();
    	}
    	response.sendRedirect("/search.jsp");
    	return json.toString();
    		
    	//return Response.created(null).status(HttpServletResponse.SC_OK).build();
   	}*/


	
    
	@POST
	@Produces(MediaType.APPLICATION_JSON)	
	public String getMerchant(@FormParam("user") String userName,
								@FormParam("email") String email,
								@FormParam("address") String address,
								@FormParam("city") String city,
								@FormParam("state") String state,
								@FormParam("country") String country,
								@FormParam("merchant") String merchant,
								@FormParam("fromDate") String fromDate,
								@FormParam("toDate") String toDate,
								@FormParam("status") String status,
								@FormParam("billAddress") String billAddress,
								@FormParam("shipAddress") String shipAddress,
								@FormParam("searchOption") int searchOption,
								@Context HttpServletRequest req,
								@Context HttpServletResponse res) throws IOException, JSONException{
	
	JSONObject json = new JSONObject();
	UserVO user = (UserVO)req.getSession().getAttribute("user") ;
	if(user == null){
		json.put("result", "Invalid");
		//res.sendRedirect("/home.jsp");
		return json.toString();
	}
	
	try {
		
		Paging.getPaging().setCursorString((String)req.getAttribute("cursorString"));
		SearchInput input = buildSearchInput(userName, email, address, city,state, country, merchant, 
											fromDate, toDate, status, billAddress, shipAddress, searchOption);
		
		List<MerchantDataGrid> merchantDataList = null; 
		
		merchantDataList = SearchServiceManager.executeDataStoreSearch(input,user);  // in order to search from datastore directly
	
		JSONArray jsonArray = toJson(merchantDataList);
		
		json.put("result", jsonArray);
		System.out.println("------"+jsonArray);
		req.setAttribute("cursorString",Paging.getPaging().getCursorString());
		
	} catch (Exception e) {
			e.printStackTrace();
	
	}
    	return json.toString();
		//return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}
	
	
	  
	@POST
	@Path("fullsearch")
	@Produces(MediaType.APPLICATION_JSON)	
	public String getCustomerInfo(@FormParam("user") String userName,
									@FormParam("email") String email,
									@FormParam("address") String address,
									@FormParam("city") String city,
									@FormParam("state") String state,
									@FormParam("country") String country,
									@FormParam("merchant") String merchant,
									@FormParam("fromDate") String fromDate,
									@FormParam("toDate") String toDate,
									@FormParam("status") String status,
									@FormParam("billAddress") String billAddress,
									@FormParam("shipAddress") String shipAddress,
									@FormParam("searchOption") int searchOption,
									@Context HttpServletRequest req,
									@Context HttpServletResponse res) throws IOException, JSONException{

		JSONObject json = new JSONObject();
		UserVO user = (UserVO)req.getSession().getAttribute("user") ;
		if(user == null){
			json.put("result", "Invalid");
			//res.sendRedirect("/home.jsp");
			return json.toString();
		}
			
		try {
			
			Paging.getPaging().setCursorString((String)req.getAttribute("cursorString"));
			SearchInput input = buildSearchInput(userName, email, address, city,state, country, merchant, 
									fromDate, toDate, status, billAddress, shipAddress, searchOption);
			
			List<MerchantDataGrid> merchantDataList = null; 
			
			merchantDataList = SearchServiceManager.executeFullTextSearch(input, user);
			
			JSONArray jsonArray = toJson(merchantDataList);
			
			json.put("result", jsonArray);
			System.out.println("------"+jsonArray);
			
			
			} catch (Exception e) {
			e.printStackTrace();
			
			}
			req.setAttribute("cursorString",Paging.getPaging().getCursorString());
			return json.toString();
			//return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}

	
	private SearchInput buildSearchInput(String userName, String email,
			String address, String city, String state, String country,
			String merchant, String fromDate, String toDate, String status,
			String billAddress, String shipAddress, int searchOption)
			throws ParseException {
		SearchInput input = new SearchInput();
		input.setSearchOption(SearchOption.getByKey(searchOption));
		input.setUserName(userName);
		input.setEmail(email);
		input.setAddress(address);
		input.setCity(city);
		input.setState(state);
		input.setCountry(country);
		input.setMerchantName(merchant);
		if(fromDate != null && !fromDate.equals(""))
			input.setFromDate(dateFormatter.parse(fromDate));
		if(toDate != null && !toDate.equals(""))
			input.setToDate(dateFormatter.parse(toDate));
		if(status != null && !status.equals(0))
			input.setOrderStatus(OrderStatus.findByValue(status));
		input.setBillAddress(billAddress);
		input.setShipAddress(shipAddress);
		return input;
	}
	
	private JSONArray toJson(List<MerchantDataGrid> merchantDataList)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject json2 = null;
		for (MerchantDataGrid merchantDataGrid : merchantDataList) {
			json2 = new JSONObject();
			json2.put("userName", merchantDataGrid.getUserName()== null ? "" : merchantDataGrid.getUserName());
			json2.put("address", merchantDataGrid.getAddress() == null ? "" : merchantDataGrid.getAddress());
			json2.put("city", merchantDataGrid.getCity()== null ? "" : merchantDataGrid.getCity());
			json2.put("state", merchantDataGrid.getState()== null ? "" : merchantDataGrid.getState());
			json2.put("country", merchantDataGrid.getCountry()== null ? "" : merchantDataGrid.getCountry());
			json2.put("merchantName", merchantDataGrid.getMerchantName()== null ? "" : merchantDataGrid.getMerchantName());
			json2.put("date", merchantDataGrid.getDate()== null ? "" : merchantDataGrid.getDate());
			json2.put("status", merchantDataGrid.getOrderStatus()== null ? "" : merchantDataGrid.getOrderStatus());
			json2.put("billingAddress", merchantDataGrid.getBillingAddress()== null ? "" : merchantDataGrid.getBillingAddress());
			json2.put("shippingAddress", merchantDataGrid.getShippingAddress()== null ? "" : merchantDataGrid.getShippingAddress());
			
			jsonArray.put(json2);
		}
		return jsonArray;
	}
    

}
