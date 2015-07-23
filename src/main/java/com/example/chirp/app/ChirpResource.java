package com.example.chirp.app;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.PubUtils;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.stores.UserStore;

@Component
@Path("/")
public class ChirpResource {

	@Context
	private UriInfo uriInfo;

	private UserStore userStore = ChirpApplication.USER_STORE;

	@GET
	@Path("/users/{username}/chirps")
	public Response getChirps(@PathParam("username") String username, 
			                  @QueryParam("variant") String variantString,
			                  @QueryParam("limit") String limitString,
			                  @QueryParam("offset") String offsetString) {

		User user = userStore.getUser(username);

		PubChirps chirps = PubUtils.toPubChirps(user, uriInfo, variantString, offsetString, limitString, false);

		ResponseBuilder builder = Response.ok(chirps);
		builder.link(chirps.getSelf(), "self");
		builder.link(chirps.getFirst(), "first");
		if (chirps.getNext() != null)		builder.link(chirps.getNext(), "next");
		if (chirps.getPrevious() != null)	builder.link(chirps.getPrevious(), "previous");
				
		return builder.build();
	}

	@POST
	@Path("/users/{username}/chirps")
	public Response createChirp(@Context UriInfo uriInfo, @PathParam("username") String username, String content) {

		User user = userStore.getUser(username);
		Chirp chirp = user.createChirp(content);
		String chirpId = chirp.getId().getId();
		userStore.updateUser(user);

		URI location = uriInfo.getBaseUriBuilder().path("chirps").path(chirpId).build();
		return Response.created(location).build();
	}

	@GET
	@Path("/chirps/{chirpId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChirp(@Context UriInfo uriInfo, @PathParam("chirpId") String chirpId) {

		for (User user : userStore.getUsers()) {
			for (Chirp chirp : user.getChirps()) {
				if (chirpId.equals(chirp.getId().getId())) {
					PubChirp pubChirp = PubUtils.toPubChirp(chirp, uriInfo);
					return Response.ok(pubChirp).link(pubChirp.getUserLink(), "user").link(pubChirp.getChirpsLink(), "chirps").build();
				}
			}
		}

		String msg = String.format("The chrip %s was not found.", chirpId);
		throw new NotFoundException(msg);
	}
}
