package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	private UserRepository repository = UserRepository.getInstance();
	
	@GET
	@Path("/{username}")
	public Response getUser(@PathParam("username") String username) {
		User user = repository.getUser(username);
		return Response.ok().entity(user.toString()).build();
	}

}
