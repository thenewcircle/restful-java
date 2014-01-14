package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import chirp.model.NoSuchEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(final @FormParam("username") String username,
			final @FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// http://localhost:8080/users</username>
		final URI location = UriBuilder.fromResource(this.getClass())
				.path(username).build();

		// should return a 201 status code with the URL to the user created
		// in the location header.
		return Response.created(location).build();
	}

	@GET
	@Path("/{username}")
	public Response getUser(final @PathParam("username") String username) {

		Status status = null;

		try {
			userRepository.getUser(username);
			status = Status.OK;
		} catch (NoSuchEntityException nsee) {
			status = Status.NOT_FOUND;
		}

		return Response.status(status).build();
	}

}
