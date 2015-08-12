package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStore;

@Path("/")
public class UserResource {
 
  private final UserStore userStore = ChirpApplication.USER_STORE;

  @PUT
  @Path("users/{username}")
  public Response createUser(@BeanParam CreateUserRequest request) throws Exception {

	  request.validate();
	  
    userStore.createUser(request.getUsername(), request.getRealName());
    URI location = request.getUriInfo().getBaseUriBuilder().path("users").path(request.getUsername()).build();
    
     return Response.created(location).build();
  }
  
  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
  @Path("users/{username}")
  public Response getUserJson(@PathParam("username") String username,
		                      @Context UriInfo uriInfo,
		                      @DefaultValue("4") @QueryParam("limit") String limitString,
		                      @DefaultValue("0") @QueryParam("offset") String offsetString,
		                      @DefaultValue("true") @QueryParam("sparse") String sparseString) {

	  URI self = uriInfo.getBaseUriBuilder()
			  .path("users")
			  .path(username)
			  .queryParam("limit", limitString)
			  .queryParam("offset", offsetString)
			  .queryParam("sparse", sparseString)
			  .build();
	  
	  PubUser pubUser = userStore.getUser(username)
			  .toPubUser(self, uriInfo, limitString, offsetString, sparseString);
	  
	  return Response.ok(pubUser)
			  	.link(self, "self")
			  	.build();
  }
}







