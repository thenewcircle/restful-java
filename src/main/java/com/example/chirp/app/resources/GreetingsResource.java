package com.example.chirp.app.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/greetings")
public class GreetingsResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello(@DefaultValue("Mr") @QueryParam("name") String theName,
			               @HeaderParam("X-NewCircle-Echo") String echoHeaderValue) {

		String value = "Hello " + theName + "!";

		return Response.ok(value)
				.header("X-NewCircle-Echo-Response", echoHeaderValue)
				.build();
	}

	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello2(@PathParam("name") String theName) {
		return "Hello " + theName + "!";
	}
}
