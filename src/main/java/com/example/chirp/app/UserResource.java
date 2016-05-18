package com.example.chirp.app;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.example.chirp.app.kernel.User;

@Path("/users")
public class UserResource {

  @PUT
  @Path("/{username}")
  public User createUser(@PathParam("username") String username, 
                         @FormParam("realName") String fullName) {

    
    User user = ChirpApplication.USER_STORE.createUser(username, fullName);
    return null;
  }
}
