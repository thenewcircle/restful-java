package com.example.chirp.app;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;

@Path("/")
public class RootResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        java.util.Date now = new java.util.Date();
        String when = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(now);
        return String.format("As of %s, everything is OK.", when);
    }

    @GET
    @Path("/greetings")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getGreetings(@DefaultValue("dude") @QueryParam("name") String someName,
                                 @HeaderParam("X-NewCircle-Echo") String otherName) {
        String text;

        if (someName == null) {
            text = "Hello!";
        } else {
            text = "Hello "  + someName + "!";
        }

        String headerValue = String.format("%s, %s, %s", otherName, otherName, otherName);

        return Response.ok(text)
                .header("X-NewCircle-Echo-Response", headerValue)
                .build();

    }

    @GET // http://localhost/greetings
    @Path("/greetings/{his-name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHelloWithPathParam(@PathParam("his-name") String someName) {
        return "Hello "  + someName + "!";
    }

}