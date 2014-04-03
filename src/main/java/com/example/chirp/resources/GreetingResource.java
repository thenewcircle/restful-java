package com.example.chirp.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/greeting")
public class GreetingResource {

	@GET
	public String getHello(@QueryParam("name") @DefaultValue("World") String nm) {
		return "Hello " + nm + "!";
	}

	@GET
	@Path("/{name}")
	public String getHelloPath(@PathParam("name") String nm) {
		return "Hello " + nm + "!";
	}

}
