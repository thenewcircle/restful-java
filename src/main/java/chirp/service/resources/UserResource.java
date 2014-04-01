package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	/** GET /users/yoda, requesting plain text */
	@GET
	@Path("{username}")
	public String getUser(@PathParam("username") String username) {
		UserRepository repo = UserRepository.getInstance();
		User user = repo.getUser(username);
		return user.getRealname();
	}

	/** POST /users, with form encoded data */
	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {
		UserRepository repo = UserRepository.getInstance();
		User user = repo.createUser(username, realname);
		URI location = UriBuilder.fromPath("/users/{username}").build(user.getUsername());
		return Response.created(location).build();
	}
	
	/** PUT /users/dbateman, with real name body.
	 * This will only create users... we aren't adding the feature for updating users.  Not today anyway. */
	@Path("{username}")
	@PUT
	public Response saveUser(@PathParam("username") String username, String realname) {
		UserRepository repo = UserRepository.getInstance();
		User user = repo.createUser(username, realname);
		URI location = UriBuilder.fromPath("/users/{username}").build(user.getUsername());
		return Response.created(location).build();
	}
	
	
}
