package com.example.chirp.app;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.User;

@Path("/users")
public class UserResource {

  @Context 
  private UriInfo uriInfo;
  
  @PUT
  @Path("/{username}")
  public Response createUser(@PathParam("username") String username, 
                         @FormParam("realName") String fullName) {

    User user = ChirpApplication.USER_STORE.createUser(username, fullName);

    //URI uri = uriInfo.getAbsolutePathBuilder();
    URI uri = uriInfo.getBaseUriBuilder()
        .path("/users")
        .path(username)
        .build();

    return Response.created(uri).build();
  }

  @GET
  @Path("/{username}")
  public String getUser(@PathParam("username") String username) {
    User user = ChirpApplication.USER_STORE.getUser(username);
    return user.getRealName();
  }
}













