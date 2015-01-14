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

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.resprentations.UserRepresentation;

@Path("/users")
public class UserResource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// http://localhost:8080/users/username
	@GET
	@Path("{username}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public UserRepresentation getUser(@PathParam("username") String username) {
		User user = UserRepository.getInstance().getUser(username);
		logger.info("Retrived username={}",username);
		return new UserRepresentation(user); 
	}

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		// remember to validate input parameters in product code.

		logger.info("Creating a user with realname={} and username={}",
				realname, username);

		UserRepository.getInstance().createUser(username, realname);

		// The URL for the resource created is
		// http://localhost:8080/users/<username> or
		// http://localhost:8080/users/student
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		return Response.created(location).build();

	}

}
