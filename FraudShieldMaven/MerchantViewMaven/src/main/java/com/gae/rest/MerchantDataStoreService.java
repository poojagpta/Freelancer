package com.gae.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gae.bean.SearchInput;
import com.gae.dao.MerchantDAOImpl;
import com.gae.model.Merchant;
import com.google.appengine.api.datastore.Entity;

@Path("merchant")
public class MerchantDataStoreService {

	@GET
	@Path("/get/{siteId}")
	@Produces(MediaType.APPLICATION_JSON)	
	public JSONObject getMerchant(@PathParam("siteId") String siteId,@Context HttpServletResponse res) throws IOException{
	
	JSONObject json = null;
    try {
		MerchantDAOImpl dao = new MerchantDAOImpl();
		SearchInput input = new SearchInput();
		/*input.setFromDate(fromDate);
		input.setToDate(toDate);*/
		Long userId = null;
		List<Merchant> merchantList = dao.getMerchantList(input,userId);
		json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(merchantList);
		json.put("list", jsonArray);
		
	} catch (JSONException e) {
			e.printStackTrace();
	
	}
    	return json;
		//return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}
	
	
	@GET
	@Path("/create/{kind}/{siteId}")
	@Produces(MediaType.APPLICATION_JSON)	
	public void createEntity(@PathParam("kind") String kind,@PathParam("siteId") String siteId,@Context HttpServletResponse res) throws IOException{
		MerchantDAOImpl dao = new MerchantDAOImpl();
		Entity entity = new Entity(kind);
		entity.setProperty("siteid", siteId);
		//entity.setProperty("timestamp", date);
		dao.create(entity);
		res.sendRedirect("/success.jsp");
		//return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}
}
