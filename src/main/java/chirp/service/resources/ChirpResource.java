package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.UserRepository;
import chirp.service.representations.ChirpRepresentation;

@Path("/users/{username}/chirps")
public class ChirpResource {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String message) {

		logger.debug("Creating chirp for username:{}", username);

		Chirp chirp = UserRepository.getInstance().getUser(username)
				.createChirp(message);

		URI location = UriBuilder.fromResource(this.getClass())
				.path(chirp.getId().toString()).build(username);

		logger.debug("Created chirp with id:{} for username:{}", chirp.getId()
				.toString(), username);

		return Response.created(location).build();

	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public ChirpRepresentation getChirp(@PathParam("username") String username,
			@PathParam("id") String id, @Context UriInfo uriInfo) {

		logger.debug("Reading chirp with id:{} for username:{}", id, username);

		return new ChirpRepresentation(UserRepository.getInstance()
				.getUser(username).getChirp(new ChirpId(id)),
				uriInfo.getAbsolutePath());

	}
}
