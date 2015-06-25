package chirp.service.resources;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.ChirpRepresentation;

@Path("/chirps/{username}")
public class ChirpsResource {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createChirp(@PathParam("username") String username,
			@FormParam("content") String content) {

		User user = UserRepository.getInstance().getUser(username);
		Chirp chirp = user.createChirp(content); // update to use chirp
													// representation

		URI location = UriBuilder.fromResource(this.getClass())
				.path(chirp.getId().toString()).build(username);

		return Response.created(location).build();

	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ChirpRepresentation getChirp(@PathParam("username") String username,
			@PathParam("id") String id) {
		return new ChirpRepresentation(UserRepository.getInstance()
				.getUser(username).getChirp(new ChirpId(id)));
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<ChirpRepresentation> getAllChirps(@PathParam("{username}") String username) {
		Collection<ChirpRepresentation> chirps = new LinkedList<>(); 
		
		logger.info("Getting all chirps for user {}", username);
				
		for (Chirp chirp : UserRepository.getInstance().getUser(username).getChirps()) {
			chirps.add(new ChirpRepresentation(chirp));
		}
		
		return chirps;
		
	}

}
