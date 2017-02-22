package com.example.chirp.app;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;

@Path("/")
public class RootResource {

    @GET
    // @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getStatus() {
        java.util.Date now = new java.util.Date();
        String when = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(now);
        return String.format("As of %s, everything is OK.", when);
    }
}