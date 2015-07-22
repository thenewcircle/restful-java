package com.example.chirp.app;

import java.net.URI;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStore;
import com.sun.research.ws.wadl.Application;

@Path("/users")
public class UserResource {

	private static final Logger log = LoggerFactory.getLogger(ChirpAppGrizzlyMain.class);

	private final UserStore userStore = ChirpApplication.USER_STORE;

	@Context
	private Application application;

	@Context
	private Request requst;

	@Context
	private HttpHeaders headers;

	@Context
	private UriInfo uriInfo;

	public UserResource() {
	}

	// mapps to http://localhost:8080/users/student
	@PUT
	@Path("/{username}")
	public Response createUser(@BeanParam CreateUserRequest createUserRequest) {

		createUserRequest.validate();

		log.warn("URI: {}", uriInfo.getAbsolutePath());

		userStore.createUser(createUserRequest.getUsername(), createUserRequest.getRealname());

		// base == http://localhost:8080/
		// result == http://localhost:8080/users/studen
		URI uri = uriInfo.getBaseUriBuilder().path("users").path(createUserRequest.getUsername()).build();

		// base == http://localhost:8080/users/student
		// result == http://localhost:8080/users/studen
		// uri = uriInfo.getAbsolutePathBuilder().build();

		// This provides a relative URL
		// uri =
		// UriBuilder.fromResource(UserResource.class).path(username).build();

		URI chirpsLink = uriInfo.getBaseUriBuilder().path("users").path(createUserRequest.getUsername()).path("chirps").build();
		chirpsLink = uriInfo.getAbsolutePathBuilder().path("chirps").build();

		return Response.created(uri).link(chirpsLink, "chirps").build();
	}

	// @GET
	// @Produces({ MediaType.TEXT_PLAIN })
	// @Path("/{username}")
	// public Response getPubUserName(@PathParam("username") String username) {
	// User user = userStore.getUser(username);
	// PubUser pubUser = user.toPubUser(uriInfo);
	// return Response.ok(pubUser.getRealname())
	// .link(pubUser.getChirpsLink(), "chirps")
	// .build();
	// }

	// private static final String[] PRODUCES = new String[]{};

	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// @Produces({ "application/vnd.vsp;version=1+json",
	// "application/vnd.vsp;version=1+json" })
	@Path("/{username}")
	public Response getPubUser(@PathParam("username") String username) {
		User user = userStore.getUser(username);
		PubUser pubUser = user.toPubUser(uriInfo);
		return Response.ok(pubUser).link(pubUser.getChirpsLink(), "chirps").build();
	}
}
