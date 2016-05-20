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

import org.springframework.stereotype.Component;

@Component
@Path("/greetings")
public class GreetingsResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response getGreeting(@DefaultValue("Dude") @QueryParam("name") String theName,
                            @HeaderParam("X-NewCircle-Echo") String headerValue) {
   
    String greeting = "Hello " + theName + "!";
    String echo = headerValue + " " + headerValue + " " + headerValue;
    
    return Response
        .ok(greeting)
        .header("X-NewCircle-Echo-Response", echo)
        .build();
  }

  @GET
  @Path("/{x-name}")
  @Produces(MediaType.TEXT_PLAIN)
  public String sayHelloWithPathParam(@PathParam("x-name") String theName) {
      return "Hello " + theName + "!";
  }
}










