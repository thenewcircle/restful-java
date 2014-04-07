package com.example.chirp.resources;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.example.chirp.model.User;
import com.example.chirp.providers.PATCH;
import com.example.chirp.representations.UserRepresentation;
import com.example.chirp.services.ChirpRepository;
import com.example.chirp.services.ConfigurationService;

@Path("/users")
public class UserResource {

	/** GET /users/yoda, requesting plain text */
	@GET
	@Path("{username}")
	@Produces(TEXT_PLAIN)
	public String getUserAsText(@PathParam("username") String username) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		User user = repo.getUser(username);
		return user.getRealname();
	}

	/** GET /users/yoda, requesting xml */
	@GET
	@Path("{username}")
	@Produces({APPLICATION_XML, TEXT_XML, APPLICATION_JSON})
	public UserRepresentation getUser(@PathParam("username") String username) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		User user = repo.getUser(username);
		UserRepresentation body = new UserRepresentation(user, false);
		return body;
	}

	/** POST /users, with form encoded data */
	@POST
	public Response createUser(@FormParam("username") String username,
		@FormParam("realname") String realname) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		User user = repo.createUser(username, realname);
		URI location = UriBuilder.fromPath("/users/{username}").build(user.getUsername());
		return Response.created(location).build();
	}
	
	/** PUT /users/dbateman, with real name body.
	 * This will only create users... we aren't adding the feature for updating users.  Not today anyway. */
	@Path("{username}")
	@PUT
	@Consumes("text/plain")
	public Response saveUser(@PathParam("username") String username, String realname) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		if (!repo.isExistingUser(username)) {
			User user = repo.createUser(username, realname);
			URI location = UriBuilder.fromPath("/users/{username}").build(user.getUsername());
			return Response.created(location).build();
		} else {
			User user = repo.getUser(username);
			user.setRealname(realname);
			return Response.noContent().build();
		}
	}
	
	/** PUT /users/yoda, with real name body. Works for create or update. */
	@Path("{username}")
	@PUT
	@Consumes({"text/xml", "application/xml", "appication/json"})
	@Produces({"text/xml", "application/xml", "appication/json"})
	public Response saveUser(@PathParam("username") String username, UserRepresentation user) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		if (!repo.isExistingUser(username)) {
			User dbUser = repo.createUser(username, user.getRealname());
			URI location = UriBuilder.fromPath("/users/{username}").build(username);
			user = new UserRepresentation(dbUser, false);
			return Response.created(location).entity(user).build();
		} else {
			User dbUser = repo.getUser(username);
			user.applyChanges(dbUser);
			user = new UserRepresentation(dbUser, false);
			return Response.ok(user).build();
		}
	}

	/** PATCH /users/yoda, with real name body. */
	@Path("{username}")
	@PATCH
	@Consumes({"text/xml", "application/xml", "appication/json"})
	@Produces({"text/xml", "application/xml", "appication/json"})
	public Response saveUserPatch(@PathParam("username") String username, UserRepresentation body) {
		ChirpRepository repo = ConfigurationService.getChirpRepository();
		User user = repo.getUser(username);
		body.applyChanges(user);
		body = new UserRepresentation(user, false);
		return Response.ok(body).build();
	}

}
