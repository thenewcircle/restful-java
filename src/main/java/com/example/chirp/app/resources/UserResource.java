package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;

@Path("/users")
public class UserResource {

  @PUT
  @Path("/{username}")
  public Response createUser(@PathParam("username") String username, 
                             @FormParam("realName") String fullName,
                             @Context UriInfo uriInfo) {

    if (username.contains(" ")) {
      throw new BadRequestException("The user name cannot contain spaces.");
    }
    
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













