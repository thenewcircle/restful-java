package chirp.service.resources;

import java.net.URI;

import javax.validation.constraints.NotNull;
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
import chirp.service.validation.Username;

@Path("/users")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private UserRepository repository = UserRepository.getInstance();
	
	@GET
	@Path("/{username}")
	public Response getUser(@Username @PathParam("username") String username) {
		User user = repository.getUser(username);
		return Response.ok().entity(user.toString()).build();
	}
	
	@PUT
	@Path("/{username}")
	public Response createUser(
			@Username @PathParam("username") String username,
			@NotNull @FormParam("realname") String realname,
			@Context UriInfo uriInfo) {
		repository.createUser(username, realname);
		URI location = uriInfo.getAbsolutePathBuilder().build();
		return Response.created(location).build();
	}
	
	@DELETE
	@Path("/{username}")
	public Response removeUser(@NotNull @PathParam("username") String username) {
		repository.deleteUser(username);
		return Response.noContent().build();
	}

}
