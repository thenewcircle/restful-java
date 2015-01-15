package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.UserRepository;
import chirp.service.resprentations.ChirpCollectionRepresentation;
import chirp.service.resprentations.ChirpRepresentation;

@Path("/chirps/{username}")
public class ChirpResource {

	@GET
	@Path("{chirpId}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ChirpRepresentation getChirp(@PathParam("username") String username,
			@PathParam("chirpId") String chirpId) {
		Chirp chirp = UserRepository.getInstance().getUser(username)
				.getChirp(new ChirpId(chirpId));
		return new ChirpRepresentation(chirp);
	}

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		Chirp chirp = UserRepository.getInstance().getUser(username)
				.createChirp(content);

		URI location = UriBuilder.fromResource(this.getClass())
				.path(chirp.getId().toString()).build(username);

		return Response.created(location).build();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public ChirpCollectionRepresentation getChirps(
			@PathParam("username") String username) {

		return new ChirpCollectionRepresentation(UserRepository.getInstance()
				.getUser(username).getChirps());

	}

}
