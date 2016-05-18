package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.kernel.User;

@Path("/users")
public class UserResource {

  @PUT
  @Path("/{username}")
  public Response createUser(@BeanParam CreateUserRequest request) {

    request.validate();
    
    User user = ChirpApplication.USER_STORE.createUser(
        request.getUsername(), 
        request.getFullName());

    //URI uri = uriInfo.getAbsolutePathBuilder();
    URI uri = request.getUriInfo().getBaseUriBuilder()
        .path("/users")
        .path(request.getUsername())
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













