package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	private final UserRepository userRepository = UserRepository.getInstance();

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		userRepository.createUser(username, realname);

		return Response
				.created(
						UriBuilder.fromResource(this.getClass()).path(username)
								.build()).build();

	}

}
