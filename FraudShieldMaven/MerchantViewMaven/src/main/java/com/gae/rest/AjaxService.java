package com.gae.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/kind")
public class AjaxService {
	
	@GET
	@Path("/get/list")
	@Produces(MediaType.APPLICATION_JSON)	
	public String getKindList(@Context HttpServletResponse res) throws IOException, JSONException{

		List<String> kindList = new ArrayList<String>();
		kindList.add("Merchant");
		kindList.add("User");
		kindList.add("Address");
		JSONObject json = new JSONObject();
		
		json.put("list",new JSONArray().put(kindList));
		return json.toString();
		
		//return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}
}
