package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
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

@Path("chirps/{username}")
public class ChirpResource {
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createChirp(@PathParam("username") String username,
			String message) {
	Chirp chirp = UserRepository.getInstance().getUser(username).createChirp(message);
		String id = chirp.getId().toString();
		URI location = UriBuilder.fromPath("/chirps/{username}/{id}").build(username, id);
		return Response.created(location).build();
	}

	/**
	 * GET http://localhost:8080/chirps/{username}/{id}
	 */
	@Path("{id}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getChirp(@PathParam("username") String username,
			@PathParam("id") String id) {
		ChirpId chirpId = new ChirpId(id);
		Chirp chirp = UserRepository.getInstance().getUser(username).getChirp(chirpId);
		ChirpRepresentation body = new ChirpRepresentation(chirp);
		return Response.ok(body).build();
		
	}


	/**
	 * GET http://localhost:8080/chirps/{username}
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAllChirps(@PathParam("username") String username) {
		Collection<Chirp> chirpList = UserRepository.getInstance().getUser(username).getChirps();
		ChirpCollectionRepresentation body = new ChirpCollectionRepresentation(chirpList);
		return Response.ok(body).build();
		
	}
}
