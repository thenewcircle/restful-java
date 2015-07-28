package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import chirp.model.UserRepository;

@Path("/users")
public class UserResource {

	@POST
	public Response addUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

		Response response = null;

		UserRepository.getInstance().createUser(username, realname);
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		response = Response.created(location).build();

		return response;

	}
}
