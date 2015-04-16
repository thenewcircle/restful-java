package chirp.service.resources;

import java.net.URI;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.UserRepository;
import chirp.service.representations.ChirpRepresentation;

@Path("/users/{username}/chirps")
public class ChirpsResource {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createChirp(
			@PathParam("username") String username,
			@NotNull(message = "A chirp must have content, what is on your mind?") @FormParam("content") String content) {
		Chirp chirp = UserRepository.getInstance().getUser(username)
				.createChirp(content);
		logger.info("created chirp for username {} with id {}", username,
				chirp.getId());

		URI location = UriBuilder.fromResource(this.getClass())
				.path(chirp.getId().toString()).build(username);

		return Response.created(location).build();

	}

	private Chirp getChirpFromUriParams(String username, String chirpIdAsString) {
		return UserRepository.getInstance().getUser(username)
				.getChirp(new ChirpId(chirpIdAsString));
	}

	@HEAD
	@Path("{chirpId}")
	public Response headChirp(@PathParam("username") String username,
			@PathParam("chirpId") String chirpIdAsString) {

		getChirpFromUriParams(username, chirpIdAsString);
		return Response.ok().build();

	}

	@GET
	@Path("{chirpId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ChirpRepresentation getChirp(@PathParam("username") String username,
			@PathParam("chirpId") String chirpIdAsString) {

		logger.info("getting chirp {} for username {}", username);
		return new ChirpRepresentation(getChirpFromUriParams(username, chirpIdAsString));

	}
}
