package com.uwea.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.uwea.util.SearchDocMessages;

@Path("/searchdoc")
public class SearchDocumentService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{param}")
	public String getMsg(@PathParam("param") String queryString)
			throws IOException {
		
		
		String searchEndpoint = SearchDocMessages.getString("SearchDocument");

		String finalQuery = "http://" + searchEndpoint + "/2013-01-01/search?"
				+ queryString.toString();
		URL url  = new URL(finalQuery);
		HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
		httpConn.connect();
		InputStream is=httpConn.getInputStream();
		
		BufferedReader buffReader= new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = buffReader.readLine()) != null) {
			response.append(inputLine);
		}
		buffReader.close();
 
		//print result
		System.out.println(response.toString());
		
		return response.toString();
		
		//return Response.status(200).entity(response).build();

	}

}
