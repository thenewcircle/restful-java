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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UsersResource {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Use this to create a new user than does not already exist in the system.
	 * 
	 * DuplicationEntityExceptions handled by DuplicatedEntityExceptionMapper class
	 * 
	 * @param username
	 * @param realname
	 * @return
	 */
	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {
		logger.info("Creating user {} with realname {}", username, realname);

		UserRepository.getInstance().createUser(username, realname);
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();
		return Response.created(location).build();

	}
	
	// http://localhost:8080/users/student
	@Path("{username}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserRepresentation getUser(@PathParam("username") String username) {
		
		logger.info("Getting info for user {}", username);
		return new UserRepresentation(UserRepository.getInstance().getUser(username));
	}

}