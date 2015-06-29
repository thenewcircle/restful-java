package com.example.chirp.app.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloResource {

	@GET
	public Response sayHello(@QueryParam("name") String name, @QueryParam("age") String ageString) {

		try {
			int age = (ageString == null) ? 0 : Integer.valueOf(ageString);
			String msg = (name == null) ? "Hello!" : "Hello " + name + " welcome to " + age;
			return Response.ok(msg).build();

		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("I need an int silly").build();
		}
	}

	@GET
	@Path("/{name}")
	public String sayHelloWithName(@PathParam("name") String name) {
		return "Hello " + name + "!";
	}

	@GET
	@Path("/{name}/{age}")
	public String sayHelloWithName(@PathParam("name") String name, @PathParam("age") int age) {
		return "Hello " + name + ", welcome to " + age;
	}
}
