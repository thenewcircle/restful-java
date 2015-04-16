package chirp.service.resources;

import java.net.URI;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.User;
import chirp.model.UserRepository;

@Path("/users")
public class UsersResource {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@POST
	public Response createUserFromRequest(
			@NotNull(message="{usersresource.createuserfromrequest.username.notnull}") @FormParam("username") String username,
			@NotNull(message="everyone has realname, please provide yours.") @FormParam("realname") String realname) {
		UserRepository.getInstance().createUser(username, realname);
		logger.info("created user {} with username {}", realname, username);

		// /users/<username>
		// /users -- comes from the call to fromResource(this.getClass())
		// /<username> -- comes from the path(username) call
		URI location = UriBuilder.fromResource(this.getClass()).path(username)
				.build();

		return Response.created(location).build();

	}

	@HEAD
	@Path("{username}")
	public Response headUser(@PathParam("username") String username) {

		UserRepository.getInstance().getUser(username);
		return Response.ok().build();

	}

	@GET
	@Path("{username}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User getUser(@PathParam("username") String username) {

		logger.info("getting user {}", username);
		return UserRepository.getInstance().getUser(username);
		// return Response.ok().build();

	}

}
