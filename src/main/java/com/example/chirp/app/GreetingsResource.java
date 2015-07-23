package com.example.chirp.app;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

// http://localhost/chirp-app/greetings
@Component
@Path("/greetings")
public class GreetingsResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello(@DefaultValue("dude") @QueryParam("name") String someName, @HeaderParam("X-NewCircle-Echo") String echoHeaderValue) {
		String retVal = "Hello " + someName + "!";
		String echo = String.format("%s, %s, %s", echoHeaderValue, echoHeaderValue, echoHeaderValue);
		return Response.ok(retVal).header("X-NewCircle-Echo-Response", echo).build();
	}

	// http://localhost/chirp-app/greetings/sad/Tom
	// @Path("/sad/{name}")

	// http://localhost/chirp-app/greetings/happy/Tom
	@Path("/happy/{name}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHelloWithPathParam(@PathParam("name") String someName, @Context HttpHeaders headers) {
		String echoHeaderValue = headers.getHeaderString("X-NewCircle-Echo");

		String retVal = "Hello " + someName + "!";
		String echo = String.format("%s, %s, %s", echoHeaderValue, echoHeaderValue, echoHeaderValue);
		return Response.ok(retVal).header("X-NewCircle-Echo-Response", echo).build();
	}
}
