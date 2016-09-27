package com.example.chirp.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/greetings")
public class GreetingsResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(@QueryParam("tame") String theName) {
        if (theName == null) {
            return "Hello!";
        } else {
            return "Hello " + theName + "!";
        }
    }
}
