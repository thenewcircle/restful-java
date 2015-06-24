package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.DuplicateEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {

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

		UserRepository.getInstance().createUser(username, realname);
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();
		return Response.created(location).build();

	}

}
