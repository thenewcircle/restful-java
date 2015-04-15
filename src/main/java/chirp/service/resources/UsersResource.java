package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.NoSuchEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUserFromRequest(
			@FormParam("username") String username,
			@FormParam("realname") String realname) {
		UserRepository.getInstance().createUser(username, realname);
		logger.info("created user {} with username {}", realname, username);

		// /users/<username>
		// /users -- comes from the call to fromResource(this.getClass())
		// /<username> -- comes from the path(username) call
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		return Response.created(location).build();

	}

	@HEAD
	@Path("{username}")
	public Response headUser(@PathParam("username") String username) {
		Response response;

		try {
			UserRepository.getInstance().getUser(username);
			response = Response.ok().build();
		} catch (NoSuchEntityException nsee) {
			response = Response.status(404).build();

		}
		return response;
	}

}
