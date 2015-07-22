package com.example.chirp.app;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.stores.UserStore;

@Path("/chirps")
public class ChirpResource {

	// private static final Logger log =
	// LoggerFactory.getLogger(ChirpAppGrizzlyMain.class);

	private UserStore userStore = ChirpApplication.USER_STORE;

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/{chirpId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChirp(@PathParam("chirpId") String chirpId) {

		for (User user : userStore.getUsers()) {
			for (Chirp chirp : user.getChirps()) {
				if (chirpId.equals(chirp.getId().getId())) {
					PubChirp pubChirp = chirp.toPubChirp(uriInfo);
					return Response.ok(pubChirp).link(pubChirp.getUserLink(), "user").link(pubChirp.getChirpsLink(), "chirps").build();
				}
			}
		}

		String msg = String.format("The chrip %s was not found.", chirpId);
		throw new NotFoundException(msg);
	}
}
