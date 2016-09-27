package com.example.chirp.app;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/greetings")
public class GreetingsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@DefaultValue("dude") @QueryParam("name") String theName) {
          return "Hello " + theName + "!";

//        if (theName == null) {
//            return "Hello!";
//        } else {
//            return "Hello " + theName + "!";
//        }
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloWithPathParam(@PathParam("name") String theName) {
        return "Hello " + theName + "!";
    }
}
