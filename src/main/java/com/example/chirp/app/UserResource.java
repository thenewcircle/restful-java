package com.example.chirp.app;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.stores.UserStore;
import com.sun.research.ws.wadl.Application;

@Path("/users")
public class UserResource {

	private final UserStore userStore = ChirpApplication.USER_STORE;

	@Context
	private Application application;

	@Context
	private Request requst;

	@Context
	private HttpHeaders headers;

	public UserResource() {
	}

	// mapps to http://localhost:8080/users/student
	@PUT
	@Path("/{username}")
	public Response createUser(@Context UriInfo uriInfo, @PathParam("username") String username, @FormParam("realname") String realname) {

		userStore.createUser(username, realname);

		// base == http://localhost:8080/
		// result == http://localhost:8080/users/studen
		URI uri = uriInfo.getBaseUriBuilder().path("users").path(username).build();

		// base == http://localhost:8080/users/student
		// result == http://localhost:8080/users/studen
		// uri = uriInfo.getAbsolutePathBuilder().build();

		return Response.created(uri).build();
	}
}
