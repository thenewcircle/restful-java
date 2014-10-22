package chirp.service.resources;

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

import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		logger.info("Creating a user with username={} and realname={}",
				username, realname);

		userRepository.createUser(username, realname);
		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();

	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_XML)
	public UserRepresentation getUserAsXML(@PathParam("username") String username) {
		return new UserRepresentation(userRepository.getUser(username));
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserRepresentation getUserAsJSON(@PathParam("username") String username) {
		return new UserRepresentation(userRepository.getUser(username));
	}



}
