package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.stores.UserStore;

@Path("/users")
public class UserResource {
 
  private final UserStore userStore = ChirpApplication.USER_STORE;

  @PUT
  @Path("/{username}")
  public Response createUser(@BeanParam CreateUserRequest request) throws Exception {

	  request.validate();
	  
    userStore.createUser(request.getUsername(), request.getRealName());
    URI location = request.getUriInfo().getBaseUriBuilder().path("users").path(request.getUsername()).build();
    
     return Response.created(location).build();
  }
  

  @GET
  @Path("/{username}")
  public String getUser(@PathParam("username") String username) {
    return userStore.getUser(username).getRealName();
  }

}







