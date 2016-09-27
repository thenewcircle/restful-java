package com.example.chirp.app;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/greetings")
public class GreetingsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@DefaultValue("dude") @QueryParam("name") String theName,
                             @HeaderParam("X-NewCircle-Echo") String echoHeaderValue) {

        String echo = String.format("%s, %s, %s", echoHeaderValue, echoHeaderValue, echoHeaderValue);

        return Response.ok("Hello " + theName + "!")
                        .header("X-NewCircle-Echo-Response", echo)
                        .build();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloWithPathParam(@PathParam("name") String theName) {
        return "Hello " + theName + "!";
    }
}
