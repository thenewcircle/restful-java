package chirp.service.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.DuplicateEntityException;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {

	private final UserRepository repo = UserRepository.getInstance();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUser(@FormParam("username") String username,
			@FormParam("realname") String realname) {

			logger.info("Creating a user with realname={} and username={}",
					realname, username);

			repo.createUser(username, realname);

			return Response.created(
					UriBuilder.fromPath("/users").path(username).build())
					.build();
	}

}
