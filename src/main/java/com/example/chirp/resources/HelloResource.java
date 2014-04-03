package com.example.chirp.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloResource {

	@GET
	public String getHello() {
		return "Hello World!";
	}

}
