package com.example.chirp.app;

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
	public Response sayHello(@DefaultValue("dude") @QueryParam("name") String someName, @HeaderParam("X-NewCircle-Echo") String echoHeaderValue) {

		String retVal = "Hello " + someName + "!";

		String echo = String.format("%s, %s, %s", echoHeaderValue, echoHeaderValue, echoHeaderValue);

		return Response.ok(retVal).header("X-NewCircle-Echo-Response", echo).build();
	}

	@Path("/{name}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHelloWithPathParam(@PathParam("name") String someName) {
		return "Hello " + someName + "!";
	}
}
