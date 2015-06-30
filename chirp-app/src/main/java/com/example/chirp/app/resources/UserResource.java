package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.kernel.stores.UsersStore;

@Path("/users")
public class UserResource {

	private UsersStore usersStore;
	
	public UserResource(@Context Application application) {
		this.usersStore = (UsersStore)application.getProperties().get(UsersStore.class.getName());
	}
	
	@PUT
	public Response createUser(@Context UriInfo uriInfo,
			                   @FormParam("username") String username, 
							   @FormParam("realName") String realName) {
		
		usersStore.createUser(username, realName);
		
        // URI newLocation = uriInfo.getAbsolutePathBuilder().path(username).build();
        // URI newLocation = uriInfo.getBaseUriBuilder().path("users").path(username).build();
		URI newLocation = UriBuilder.fromResource(UserResource.class).path(username).build();
		return Response.created(newLocation).build();
	}
}
