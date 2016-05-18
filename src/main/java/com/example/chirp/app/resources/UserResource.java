package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.pub.PubUtils;

@Path("/users")
public class UserResource {

  @Context UriInfo uriInfo;
  
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
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/{username}")
  public String getUserPlain(@PathParam("username") String username) {
    User user = ChirpApplication.USER_STORE.getUser(username);
    return user.getRealName();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{username}")
  public Response getUserJson(@PathParam("username") String username) {
    User user = ChirpApplication.USER_STORE.getUser(username);
    PubUser pubUser = PubUtils.toPubUser(uriInfo, user);

    return Response.ok(pubUser)
        .link(pubUser.getLinks().get("self"), "self")
        .link(pubUser.getLinks().get("chirps"), "chirps")
        .build();
  }
}













