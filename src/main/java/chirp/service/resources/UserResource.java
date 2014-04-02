package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.DuplicateEntityException;
import chirp.model.UserRepository;

@Path("/user")
public class UserResource {

	private UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		// using a java.net.URI for creating a location
		// URI location = URI.create("/user/" + username);
		// return Response.created(location).build();

		return Response.created(
				UriBuilder.fromPath("user").path(username).build()).build();

	}

}
