package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.stores.UserStore;

@Path("/users")
public class UserResource {
 
  private final UserStore userStore = ChirpApplication.USER_STORE;

  @PUT
  @Path("/{username}")
  public Response createUser(@PathParam("username") String username,
                         @FormParam("realName") String realName,
                         @Context UriInfo uriInfo) {

    userStore.createUser(username, realName);
    URI location = uriInfo.getBaseUriBuilder().path("users").path(username).build();
    
     return Response.created(location).build();
//    return Response.status(201).header("Location", location.toString()).build();
  }

  @GET
  @Path("/{username}")
  public String getUser(@PathParam("username") String username) {
    return userStore.getUser(username).getRealName();
  }
}