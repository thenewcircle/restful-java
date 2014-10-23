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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.Chirp;
import chirp.model.ChirpId;
import chirp.model.UserRepository;
import chirp.service.representations.ChirpCollectionRepresentation;
import chirp.service.representations.ChirpRepresentation;

@Path("/chirps/{username}")
public class ChirpResource {

	private final UserRepository userRepository = UserRepository.getInstance();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		logger.info("Creating a chirp with for username={} and content={}",
				username, content);

		Chirp chirp = userRepository.getUser(username).createChirp(content);

		URI location = UriBuilder.fromResource(this.getClass()) // fromResource pulls in /chirps/{username}
				.path(chirp.getId().toString()).build(username); // build fills in the username template value

		return Response.created(location).build();
	}
	
	private Response createSingleChirpResponse(boolean isGet, String username, String id, UriInfo uriInfo) {
		Chirp chirp = userRepository.getUser(username).getChirp(new ChirpId(id)); // will validate if the users exists
		URI self = uriInfo.getAbsolutePathBuilder().build();
		ResponseBuilder rb = (isGet) ? Response.ok(new ChirpRepresentation(false, self, chirp)) : Response.ok();
		return rb.build();
	}
	
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getChirp(@PathParam("username") String username,
			@PathParam("id") String id, @Context UriInfo uriInfo) {
		return createSingleChirpResponse(true, username, id, uriInfo);
	}
		
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ChirpCollectionRepresentation getChirp(@PathParam("username") String username, @Context UriInfo uriInfo) {
		
		return new ChirpCollectionRepresentation(userRepository.getUser(username).getChirps(),uriInfo);
	}


}
