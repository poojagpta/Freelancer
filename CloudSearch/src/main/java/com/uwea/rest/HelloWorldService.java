package com.uwea.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorldService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public String getMessage(@PathParam("name") String name){
 
        return "{'name':"+name+",'desg':'SoftWareEngg','Age':'26'}";
    }
	
}
