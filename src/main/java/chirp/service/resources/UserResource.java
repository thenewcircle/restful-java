package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	private static final String USERNAME_REGEX = "^[a-z0-9_-]{3,16}$";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private UserRepository repository = UserRepository.getInstance();
	
	@GET
	@Path("/{username}")
	public Response getUser(@PathParam("username") String username) {
		if (!validate(username)) {
			throw new BadRequestException("Invalid username: " + username);
		}
		User user = repository.getUser(username);
		return Response.ok().entity(user.toString()).build();
	}
	
	@PUT
	@Path("/{username}")
	public Response createUser(@PathParam("username") String username, @FormParam("realname") String realname, @Context UriInfo uriInfo) {
		if (!validate(username)) {
			throw new BadRequestException("Invalid username: " + username);
		}
		repository.createUser(username, realname);
		URI location = uriInfo.getAbsolutePathBuilder().build();
		return Response.created(location).build();
	}
	
	@DELETE
	@Path("/{username}")
	public Response removeUser(@PathParam("username") String username) {
		if (!validate(username)) {
			throw new BadRequestException("Invalid username: " + username);
		}
		repository.deleteUser(username);
		return Response.noContent().build();
	}
	
	private boolean validate(String username) {
		return username != null && username.matches(USERNAME_REGEX);
	}

}
