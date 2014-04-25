package com.gae.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Path("/get/{id}")
public class BlobRestService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getBlob(@PathParam("id") String id,@Context HttpServletResponse res){
		 BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		 
		 BlobKey blobKey = new BlobKey(id);
		 try {	
			blobstoreService.serve(blobKey, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 return Response.created(null).status(HttpServletResponse.SC_OK).build();
	}

}
