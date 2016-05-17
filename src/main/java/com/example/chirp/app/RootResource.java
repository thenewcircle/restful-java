package com.example.chirp.app;

import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class RootResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getStatus() {
    java.util.Date now = new java.util.Date();
    String when = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(now);
    return String.format("As of %s, everything is OK.", when);
  }
}