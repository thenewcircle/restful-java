package com.example.chirp.app.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;

@Path("/hello")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN_VALUE)
	public Response sayHello(@QueryParam("name") String name, @QueryParam("age") String ageString) {
		try {
			int age = (ageString == null) ? 0 : Integer.valueOf(ageString);
			String msg = (name == null) ? "Hello!" : "Hello " + name + " welcome to " + age;
			return Response.ok(msg).build();

		} catch (NumberFormatException e) {
			throw new BadRequestException("I need an int silly", e);
		}
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN_VALUE)
	public String sayHelloWithName(@PathParam("name") String name) {
		return "Hello " + name + "!";
	}

	@GET
	@Path("/{name}/{age}")
	@Produces(MediaType.TEXT_PLAIN_VALUE)
	public String sayHelloWithName(@PathParam("name") String name, @PathParam("age") int age) {
		return "Hello " + name + ", welcome to " + age;
	}
}
