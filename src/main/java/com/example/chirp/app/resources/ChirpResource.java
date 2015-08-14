package com.example.chirp.app.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUtils;
import com.example.chirp.app.stores.UserStore;

@Component
@Path("/")
public class ChirpResource {

	private final UserStore userStore;

	@Autowired
	public ChirpResource(UserStore userStore) {
		this.userStore = userStore;
	}
	
	@GET
	@Path("/chirps/{chirpId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getChirp(@PathParam("chirpId") String chirpId,
			                 @Context UriInfo uriInfo) {
		
		Chirp chirp = userStore.getChirp(chirpId);
		PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
		
		return Response.ok(pubChirp)
				.link(pubChirp.getSelf(), "self")
				.link(pubChirp.getChirpsLink(), "chirpsLink")
				.link(pubChirp.getUserLink(), "userLink")
				.build();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/users/{username}/chirps")
	public Response getChirps(@PathParam("username") String username,
			                  @Context UriInfo uriInfo,
			                  @DefaultValue("4") @QueryParam("limit") String limitString,
			                  @DefaultValue("0") @QueryParam("offset") String offsetString) {
		
		User user = userStore.getUser(username);
		PubChirps pubChirps = PubUtils.toPubChirps(uriInfo, user, limitString, offsetString);
		
		return Response.ok(pubChirps).build();
	}
}












